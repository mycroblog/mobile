// Throwaway spike for mycroblog/mobile#15. Never merges.
// A UI test that never ends: it listens on TCP 127.0.0.1:$SPIKE_DRIVER_PORT (default 22087) for one JSON
// command per line and answers one JSON line per command. Run it with
// `xcodebuild test-without-building ... -only-testing:Spike3UITests/DriverServer/testServe`.
import Network
import XCTest

final class DriverServer: XCTestCase {
    private var app = XCUIApplication(bundleIdentifier: "blog.mycro.spike3")
    private let springboard = XCUIApplication(bundleIdentifier: "com.apple.springboard")
    private var done = false

    func testServe() throws {
        continueAfterFailure = true
        let port = NWEndpoint.Port(rawValue: UInt16(ProcessInfo.processInfo.environment["SPIKE_DRIVER_PORT"] ?? "") ?? 22087)!
        let params = NWParameters.tcp
        params.requiredLocalEndpoint = .hostPort(host: "127.0.0.1", port: port)
        params.allowLocalEndpointReuse = true
        let listener = try NWListener(using: params)
        listener.newConnectionHandler = { [weak self] c in
            c.start(queue: .main)
            self?.receive(on: c, buffer: Data())
        }
        listener.start(queue: .main)
        NSLog("SPIKE_DRIVER ready on \(port)")
        while !done { RunLoop.main.run(until: Date().addingTimeInterval(0.05)) }
        listener.cancel()
    }

    private func receive(on c: NWConnection, buffer: Data) {
        c.receive(minimumIncompleteLength: 1, maximumLength: 1 << 20) { [weak self] data, _, end, _ in
            guard let self else { return }
            var buf = buffer + (data ?? Data())
            while let nl = buf.firstIndex(of: 0x0A) {
                let line = buf[buf.startIndex..<nl]
                buf = Data(buf[buf.index(after: nl)...])
                let reply = MainActor.assumeIsolated { self.handle(line) }
                var out = (try? JSONSerialization.data(withJSONObject: reply)) ?? Data("{}".utf8)
                out.append(0x0A)
                c.send(content: out, completion: .idempotent)
            }
            if end { c.cancel() } else { self.receive(on: c, buffer: buf) }
        }
    }

    @MainActor private func handle(_ line: Data) -> [String: Any] {
        let t = Date()
        guard let cmd = try? JSONSerialization.jsonObject(with: line) as? [String: Any], let op = cmd["op"] as? String else {
            return ["ok": false, "error": "bad json"]
        }
        var out: [String: Any] = ["ok": true, "op": op]
        do {
            try run(op, cmd, &out)
        } catch {
            out["ok"] = false; out["error"] = "\(error)"
        }
        // Every command that changes something can ask for the tree and the screenshot in the same reply.
        if cmd["tree"] as? Bool == true { out["tree"] = tree() }
        if let path = cmd["screenshot"] as? String {
            try? XCUIScreen.main.screenshot().pngRepresentation.write(to: URL(fileURLWithPath: path))
            out["screenshot"] = path
        }
        out["ms"] = Int(Date().timeIntervalSince(t) * 1000)
        return out
    }

    struct Failure: Error, CustomStringConvertible { let description: String }

    @MainActor private func element(_ cmd: [String: Any]) throws -> XCUIElement {
        let target = (cmd["springboard"] as? Bool == true) ? springboard : app
        let q = target.descendants(matching: .any)
        let e: XCUIElement
        if let id = cmd["id"] as? String { e = q[id].firstMatch }
        else if let label = cmd["label"] as? String { e = q.matching(NSPredicate(format: "label == %@", label)).firstMatch }
        else { throw Failure(description: "need id or label") }
        guard e.waitForExistence(timeout: (cmd["timeout"] as? Double) ?? 2) else { throw Failure(description: "no element") }
        return e
    }

    @MainActor private func run(_ op: String, _ cmd: [String: Any], _ out: inout [String: Any]) throws {
        switch op {
        case "ping": break
        case "tap":
            if let x = cmd["x"] as? Double, let y = cmd["y"] as? Double {
                let base = (cmd["springboard"] as? Bool == true) ? springboard : app
                base.coordinate(withNormalizedOffset: .zero).withOffset(CGVector(dx: x, dy: y)).tap()
            } else { try element(cmd).tap() }
        case "type":
            if cmd["id"] != nil || cmd["label"] != nil { let e = try element(cmd); e.tap(); e.typeText(cmd["text"] as? String ?? "") }
            else { app.typeText(cmd["text"] as? String ?? "") }
        case "swipe":
            let e = (cmd["id"] != nil || cmd["label"] != nil) ? try element(cmd) : app
            switch cmd["dir"] as? String ?? "up" {
            case "up": e.swipeUp(); case "down": e.swipeDown(); case "left": e.swipeLeft(); default: e.swipeRight()
            }
        case "back":
            let b = app.navigationBars.buttons.element(boundBy: 0)
            guard b.waitForExistence(timeout: 2) else { throw Failure(description: "no back button") }
            b.tap()
        case "home": XCUIDevice.shared.press(.home)
        case "orientation":
            let map: [String: UIDeviceOrientation] = ["portrait": .portrait, "landscapeLeft": .landscapeLeft,
                                                      "landscapeRight": .landscapeRight, "portraitUpsideDown": .portraitUpsideDown]
            XCUIDevice.shared.orientation = map[cmd["value"] as? String ?? ""] ?? .portrait
        case "appearance":
            XCUIDevice.shared.appearance = (cmd["value"] as? String) == "dark" ? .dark : .light
        case "openURL": XCUIDevice.shared.system.open(URL(string: cmd["url"] as! String)!)
        case "launch":
            app = XCUIApplication(bundleIdentifier: "blog.mycro.spike3")
            app.launchArguments = cmd["args"] as? [String] ?? []
            app.launch()
        case "activate": app.activate()
        case "terminate": app.terminate()
        case "state": out["state"] = app.state.rawValue
        case "tree": out["tree"] = tree()
        case "quit": done = true
        default: throw Failure(description: "unknown op \(op)")
        }
    }

    /// One line per element: indentation, type, "label", [identifier], =value, @x,y,wxh. Groups without a label are folded.
    @MainActor private func tree() -> String {
        let root: XCUIElement = app.state == .runningForeground ? app : springboard
        guard let snap = try? root.snapshot() else { return "<no snapshot>" }
        var lines: [String] = []
        func walk(_ s: XCUIElementSnapshot, _ depth: Int) {
            var d = depth
            let keep = !(s.elementType == .other && s.label.isEmpty && s.identifier.isEmpty)
            if keep {
                var bits = [Self.name(s.elementType)]
                if !s.label.isEmpty { bits.append("\"\(s.label)\"") }
                if !s.identifier.isEmpty { bits.append("[\(s.identifier)]") }
                if let v = s.value as? String, !v.isEmpty { bits.append("=\"\(v)\"") } else if let v = s.value { bits.append("=\(v)") }
                if s.isSelected { bits.append("selected") }
                if !s.isEnabled { bits.append("disabled") }
                let f = s.frame
                bits.append("@\(Int(f.minX)),\(Int(f.minY)),\(Int(f.width))x\(Int(f.height))")
                lines.append(String(repeating: "  ", count: depth) + bits.joined(separator: " "))
                d += 1
            }
            for c in s.children { walk(c, d) }
        }
        walk(snap, 0)
        return lines.joined(separator: "\n")
    }

    private static func name(_ t: XCUIElement.ElementType) -> String {
        let names: [XCUIElement.ElementType: String] = [
            .application: "Application", .window: "Window", .button: "Button", .staticText: "StaticText",
            .navigationBar: "NavigationBar", .tabBar: "TabBar", .table: "Table", .collectionView: "CollectionView",
            .cell: "Cell", .textField: "TextField", .textView: "TextView", .switch: "Switch", .toggle: "Toggle",
            .image: "Image", .scrollView: "ScrollView", .sheet: "Sheet", .alert: "Alert", .toolbar: "Toolbar",
            .other: "Other", .keyboard: "Keyboard", .key: "Key", .searchField: "SearchField", .link: "Link",
            .slider: "Slider", .icon: "Icon", .statusBar: "StatusBar", .menu: "Menu", .menuItem: "MenuItem",
            .outline: "Outline", .outlineRow: "OutlineRow", .group: "Group", .segmentedControl: "SegmentedControl",
        ]
        return names[t] ?? "type\(t.rawValue)"
    }
}
