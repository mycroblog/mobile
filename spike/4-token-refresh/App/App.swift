import SwiftUI

// Launch arguments: -mode none|flock|coord|lease  -n <iterations>  -pause <ms>  -reset 1  -autorun 1  -startIn <s>
@main
struct Spike4App: App {
    init() {
        Log.who = "app"
        let d = UserDefaults.standard
        let cfg = RunConfig(mode: Mode(rawValue: d.string(forKey: "mode") ?? "none") ?? .none,
                            iterations: Int(d.string(forKey: "n") ?? "") ?? 10,
                            pauseMs: Int(d.string(forKey: "pause") ?? "") ?? 200,
                            startAt: Date().timeIntervalSince1970 + Double(d.integer(forKey: "startIn")))
        cfg.save()  // the extension reads the same run configuration
        if d.bool(forKey: "reset") {
            let (_, o) = Server.call("issue")
            if let t = Server.tokens(o) { Keychain.write(t); Log.t("reset: stored gen 0") }
        }
        Log.t("launched; keychain token gen=\(Keychain.read()?.gen ?? -1) mode=\(cfg.mode.rawValue)")
        Timer.scheduledTimer(withTimeInterval: 1, repeats: true) { _ in Log.t("heartbeat") }
        if d.bool(forKey: "autorun") { DispatchQueue.global().async { Refresher.loop(cfg) } }
    }
    var body: some Scene {
        WindowGroup {
            VStack(spacing: 24) {
                Text("Spike 4").font(.title)
                Button("Run loop") { DispatchQueue.global().async { Refresher.loop(RunConfig.load()) } }
                ShareLink(item: "spike4") { Text("Share") }
            }
        }
    }
}
