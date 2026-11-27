// The ops of the runner protocol: each request acts on the app under test (`app`, a bundle id) or the system, then
// answers with the settled tree and a screenshot.
import XCTest

final class Driver {
    struct Failure: Error {
        let message: String
        init(_ message: String) { self.message = message }
    }

    static let springboardID = "com.apple.springboard"
    static let safariID = "com.apple.mobilesafari"

    let springboard = XCUIApplication(bundleIdentifier: springboardID)
    private let issues: () -> [String]
    private var apps: [String: XCUIApplication] = [:]
    /// Apps other than the one under test that may be in front: Safari for `share`, and every app a request named.
    private var known: [String] = [safariID]
    lazy var screen = Screen(driver: self)

    init(issues: @escaping () -> [String]) {
        self.issues = issues
    }

    func application(_ id: String) -> XCUIApplication {
        if id == Self.springboardID { return springboard }
        if let app = apps[id] { return app }
        let app = XCUIApplication(bundleIdentifier: id)
        apps[id] = app
        if !known.contains(id) { known.append(id) }
        return app
    }

    /// The bundle id this runner made the app with.
    func id(of app: XCUIApplication) -> String? {
        app === springboard ? Self.springboardID : apps.first { $0.value === app }?.key
    }

    /// The apps the tree shows besides the app under test, and the ones an element is searched in after it.
    func others(than id: String?) -> [XCUIApplication] {
        known.filter { $0 != id }.map(application)
    }

    /// Handles one request line; the second value is true when the runner must stop after sending the reply.
    func handle(_ line: Data) -> ([String: Any], Bool) {
        guard let request = (try? JSONSerialization.jsonObject(with: line)) as? [String: Any],
              let op = request["op"] as? String else {
            return (["ok": false, "error": "the request is not a JSON object with an op"], false)
        }
        if op == "ping" || op == "quit" { return (["ok": true], op == "quit") }
        let appID = request["app"] as? String
        var reply: [String: Any] = ["ok": true]
        do {
            try run(op, request, appID)
            if let issue = issues().first { throw Failure(Self.sentence(issue)) }
        } catch let failure as Failure {
            reply["ok"] = false
            reply["error"] = failure.message
        } catch {
            reply["ok"] = false
            reply["error"] = "\(error)"
        }
        let (tree, screenshot) = screen.settled(appID)
        reply["tree"] = tree
        reply["screenshot"] = screenshot
        return (reply, false)
    }

    private func run(_ op: String, _ request: [String: Any], _ appID: String?) throws {
        func app() throws -> XCUIApplication {
            guard let appID else { throw Failure("the request has no app") }
            return application(appID)
        }
        func running() throws -> XCUIApplication {
            let app = try app()
            if app.state == .notRunning { throw Failure("\(appID!) is not running") }
            return app
        }
        switch op {
        case "look":
            break
        case "tap":
            let app = try running()
            if let x = number(request["x"]), let y = number(request["y"]) {
                screen.point(x: x, y: y, appID).tap()
            } else {
                try element(request, in: app).tap()
            }
        case "type":
            let app = try running()
            guard let text = request["text"] as? String else { throw Failure("type needs text") }
            if request["label"] != nil || request["id"] != nil {
                let field = try element(request, in: app, preferring: [.textField, .secureTextField, .searchField, .textView])
                if !field.hasFocus { field.tap() }
                field.typeText(text)
            } else {
                app.typeText(text)
            }
        case "scroll":
            let app = try running()
            let container = request["label"] != nil || request["id"] != nil ? try element(request, in: app) : app
            switch request["direction"] as? String {
            case "down": container.swipeUp()
            case "up": container.swipeDown()
            case "right": container.swipeLeft()
            case "left": container.swipeRight()
            default: throw Failure("scroll needs a direction: up, down, left or right")
            }
        case "back":
            try back(running())
        case "home":
            let before = appID.map(application)?.state
            XCUIDevice.shared.press(.home)
            if let appID { awaitChange(of: application(appID), from: before) }
        case "launch":
            let app = try app()
            app.launchArguments = (request["args"] as? [String]) ?? []
            app.launch()
        case "activate":
            try app().activate()
        case "orientation":
            let orientations: [String: UIDeviceOrientation] = [
                "portrait": .portrait, "landscape-left": .landscapeLeft,
                "landscape-right": .landscapeRight, "upside-down": .portraitUpsideDown,
            ]
            guard let value = request["value"] as? String, let orientation = orientations[value] else {
                throw Failure("orientation needs a value: portrait, landscape-left, landscape-right or upside-down")
            }
            XCUIDevice.shared.orientation = orientation
        case "open":
            guard let text = request["url"] as? String, let url = URL(string: text) else { throw Failure("open needs a url") }
            let before = appID.map(application)?.state
            XCUIDevice.shared.system.open(url)
            if let appID { awaitChange(of: application(appID), from: before) }
        case "share":
            try share(request)
        case "resize":
            guard let width = number(request["width"]), let height = number(request["height"]) else {
                throw Failure("resize needs width and height")
            }
            _ = try running()
            try Windows(driver: self).resize(appID!, to: CGSize(width: width, height: height))
        default:
            throw Failure("unknown op \"\(op)\"")
        }
    }

    /// Waits up to 2 s for the app's state to change: XCUITest learns it asynchronously, and the tree is read by it.
    private func awaitChange(of app: XCUIApplication, from state: XCUIApplication.State?) {
        let deadline = Date().addingTimeInterval(2)
        while app.state == state, Date() < deadline { RunLoop.current.run(until: Date().addingTimeInterval(0.1)) }
    }

    /// The first sentence of an XCTest issue, which goes on with a dump of the app's elements.
    private static func sentence(_ issue: String) -> String {
        let line = issue.prefix { $0 != "\n" }
        guard let end = line.range(of: ". ") else { return String(line) }
        return String(line[..<end.lowerBound]) + "."
    }

    private func number(_ value: Any?) -> Double? {
        (value as? NSNumber)?.doubleValue
    }

    /// The element of `label` or `id`, searched in `app` first, then in springboard and the other apps in front. In
    /// each app an element of the `preferring` types wins over any other.
    func element(
        _ request: [String: Any], in app: XCUIApplication, preferring types: [XCUIElement.ElementType] = [],
        timeout: TimeInterval = 2
    ) throws -> XCUIElement {
        let predicate: NSPredicate
        let what: String
        if let id = request["id"] as? String {
            predicate = NSPredicate(format: "identifier == %@", id)
            what = "id \"\(id)\""
        } else if let label = request["label"] as? String {
            predicate = NSPredicate(format: "label == %@", label)
            what = "label \"\(label)\""
        } else {
            throw Failure("the request needs a label or an id")
        }
        let places = [app, springboard] + others(than: id(of: app)).filter { $0 !== springboard }
        let deadline = Date().addingTimeInterval(timeout)
        repeat {
            for place in places where place === app || place === springboard || place.state == .runningForeground {
                for type in types + [.any] {
                    let match = place.descendants(matching: type).matching(predicate).firstMatch
                    // Springboard keeps the home screen under the app in front: only what can be hit there counts.
                    if match.exists, place === app || match.isHittable { return match }
                }
            }
            RunLoop.current.run(until: Date().addingTimeInterval(0.1))
        } while Date() < deadline
        throw Failure("no element with \(what)")
    }

    /// The back button of UIKit and SwiftUI navigation (and Safari's), else the leading button of the navigation bar,
    /// else a swipe from the left edge.
    private func back(_ app: XCUIApplication) throws {
        let named = app.buttons["BackButton"]
        if named.exists, named.isHittable, named.isEnabled {
            named.tap()
            return
        }
        let bar = app.navigationBars.firstMatch
        if bar.exists {
            let first = bar.buttons.element(boundBy: 0)
            if first.exists, first.frame.minX < bar.frame.minX + bar.frame.width / 4 {
                first.tap()
                return
            }
        }
        let start = app.coordinate(withNormalizedOffset: CGVector(dx: 0, dy: 0.5)).withOffset(CGVector(dx: 2, dy: 0))
        let end = app.coordinate(withNormalizedOffset: CGVector(dx: 0.9, dy: 0.5))
        start.press(forDuration: 0.1, thenDragTo: end, withVelocity: .fast, thenHoldForDuration: 0)
    }

    private func share(_ request: [String: Any]) throws {
        guard let text = request["url"] as? String, let url = URL(string: text) else { throw Failure("share needs a url") }
        guard let target = request["target"] as? String else { throw Failure("share needs a target") }
        let safari = application(Self.safariID)
        XCUIDevice.shared.system.open(url)
        guard safari.wait(for: .runningForeground, timeout: 10) else { throw Failure("Safari did not open \(text)") }
        try Share(driver: self, safari: safari).pick(target)
    }
}
