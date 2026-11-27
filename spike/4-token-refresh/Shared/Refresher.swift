// Spike #4: cross-process serialized token refresh, compiled into the app and the Share Extension.
import Foundation
import Security
#if !EXTENSION
import UIKit
#endif

enum Cfg {
    static let group = "group.org.mycroblog.spike4"
    static let server = URL(string: "http://127.0.0.1:8765")!
    static var container: URL {
        FileManager.default.containerURL(forSecurityApplicationGroupIdentifier: group)!
    }
    static var logURL: URL { container.appendingPathComponent("spike4.log") }
    static var configURL: URL { container.appendingPathComponent("config.json") }
    static var lockURL: URL { container.appendingPathComponent("refresh.lock") }
    static var leaseURL: URL { container.appendingPathComponent("refresh.lease") }
    static let leaseSeconds: Double = 60
}

enum Mode: String, Codable { case none, flock, coord, lease }

struct RunConfig: Codable {
    var mode: Mode
    var iterations: Int
    var pauseMs: Int
    /// Wall-clock start shared by app and extension, so both loops begin at the same moment.
    var startAt: Double = 0
    static func load() -> RunConfig {
        (try? JSONDecoder().decode(RunConfig.self, from: Data(contentsOf: Cfg.configURL)))
            ?? RunConfig(mode: .none, iterations: 10, pauseMs: 200)
    }
    func save() { try? JSONEncoder().encode(self).write(to: Cfg.configURL, options: .atomic) }
}

enum Log {
    static var who = "app"
    private static let fmt: DateFormatter = {
        let f = DateFormatter(); f.dateFormat = "HH:mm:ss.SSS"; f.timeZone = TimeZone(identifier: "UTC"); return f
    }()
    static func t(_ s: String) {
        let line = "\(fmt.string(from: Date())) [\(who) \(getpid())] \(s)\n"
        NSLog("SPIKE4 %@", line)
        let fd = open(Cfg.logURL.path, O_WRONLY | O_APPEND | O_CREAT, 0o644)
        if fd >= 0 { _ = line.withCString { write(fd, $0, strlen($0)) }; close(fd) }
    }
}

struct Tokens: Codable, Equatable { var access: String; var refresh: String; var gen: Int }

enum Keychain {
    static let service = "spike4.tokens"
    static let account = "acct1"
    static var base: [String: Any] {
        [kSecClass as String: kSecClassGenericPassword,
         kSecAttrService as String: service,
         kSecAttrAccount as String: account,
         kSecAttrAccessGroup as String: Cfg.group,
         kSecUseDataProtectionKeychain as String: true]
    }
    static func read() -> Tokens? {
        var q = base
        q[kSecReturnData as String] = true
        q[kSecMatchLimit as String] = kSecMatchLimitOne
        var out: CFTypeRef?
        let st = SecItemCopyMatching(q as CFDictionary, &out)
        guard st == errSecSuccess, let d = out as? Data else {
            if st != errSecItemNotFound { Log.t("keychain read status=\(st)") }
            return nil
        }
        return try? JSONDecoder().decode(Tokens.self, from: d)
    }
    static func write(_ t: Tokens) {
        let d = try! JSONEncoder().encode(t)
        let attrs: [String: Any] = [kSecValueData as String: d,
                                    kSecAttrAccessible as String: kSecAttrAccessibleAfterFirstUnlockThisDeviceOnly]
        var st = SecItemUpdate(base as CFDictionary, attrs as CFDictionary)
        if st == errSecItemNotFound {
            var add = base; attrs.forEach { add[$0] = $1 }
            add[kSecAttrSynchronizable as String] = false
            st = SecItemAdd(add as CFDictionary, nil)
        }
        if st != errSecSuccess { Log.t("keychain write status=\(st)") }
    }
}

enum Server {
    /// Synchronous on purpose: the lock variants hold a lock across this call.
    static func call(_ path: String, body: String? = nil) -> (Int, [String: Any]) {
        var req = URLRequest(url: Cfg.server.appendingPathComponent(path), timeoutInterval: 120)
        req.httpMethod = "POST"
        req.setValue(Log.who, forHTTPHeaderField: "x-who")
        req.httpBody = body?.data(using: .utf8)
        let sem = DispatchSemaphore(value: 0)
        var result: (Int, [String: Any]) = (0, [:])
        URLSession.shared.dataTask(with: req) { d, r, e in
            let code = (r as? HTTPURLResponse)?.statusCode ?? -1
            let obj = d.flatMap { try? JSONSerialization.jsonObject(with: $0) as? [String: Any] } ?? ["error": "\(e?.localizedDescription ?? "?")"]
            result = (code, obj); sem.signal()
        }.resume()
        sem.wait()
        return result
    }
    static func tokens(_ o: [String: Any]) -> Tokens? {
        guard let a = o["access_token"] as? String, let r = o["refresh_token"] as? String, let g = o["gen"] as? Int else { return nil }
        return Tokens(access: a, refresh: r, gen: g)
    }
}

/// Keeps the process from being suspended while `body` runs: a background task in the app,
/// performExpiringActivity in an extension (no UIApplication there).
enum Activity {
    static func run(_ name: String, _ body: @escaping () -> Void, expired: @escaping () -> Void) {
        #if EXTENSION
        let done = DispatchSemaphore(value: 0)
        var finished = false
        ProcessInfo.processInfo.performExpiringActivity(withReason: name) { isExpired in
            if isExpired { if !finished { Log.t("activity EXPIRED: \(name)"); expired() }; return }
            body(); finished = true; done.signal()
        }
        done.wait()
        #else
        var id = UIBackgroundTaskIdentifier.invalid
        id = DispatchQueue.main.sync {
            UIApplication.shared.beginBackgroundTask(withName: name) {
                Log.t("background task EXPIRED: \(name) remaining=\(UIApplication.shared.backgroundTimeRemaining)")
                expired()
                UIApplication.shared.endBackgroundTask(id)
            }
        }
        body()
        DispatchQueue.main.async { UIApplication.shared.endBackgroundTask(id) }
        #endif
    }
}

enum Refresher {
    /// The network part every variant shares: re-read, skip if someone else already rotated, else rotate and save at once.
    private static func refreshIfStill(_ stale: Tokens) -> String {
        guard let cur = Keychain.read() else { return "no token" }
        if cur.refresh != stale.refresh { return "already refreshed by another process (gen \(cur.gen)), using it" }
        let (code, o) = Server.call("token", body: "grant_type=refresh_token&refresh_token=\(cur.refresh)")
        if code == 200, let n = Server.tokens(o) { Keychain.write(n); return "refreshed gen \(cur.gen) -> \(n.gen)" }
        return "FAILED \(code) \(o["reason"] ?? o["error"] ?? "")"
    }

    static func refresh(mode: Mode) {
        guard let stale = Keychain.read() else { Log.t("no token in keychain"); return }
        let t0 = Date()
        switch mode {
        case .none:
            Log.t("none: \(refreshIfStill(stale))")
        case .flock:
            let fd = open(Cfg.lockURL.path, O_RDWR | O_CREAT, 0o644)
            flock(fd, LOCK_EX)
            Log.t("flock acquired after \(ms(t0)) ms")
            Log.t("flock: \(refreshIfStill(stale))")
            flock(fd, LOCK_UN); close(fd)
        case .coord:
            var err: NSError?
            NSFileCoordinator(filePresenter: nil).coordinate(writingItemAt: Cfg.lockURL, options: [], error: &err) { _ in
                Log.t("coordinator acquired after \(ms(t0)) ms")
                Log.t("coord: \(refreshIfStill(stale))")
            }
            if let err { Log.t("coord error \(err)") }
        case .lease:
            // No OS lock is held across the network call. A lease record is taken under a flock held
            // for microseconds; the refresh runs inside a background task / expiring activity.
            while !Lease.acquire() {
                if let cur = Keychain.read(), cur.refresh != stale.refresh {
                    Log.t("lease: refreshed by another process while waiting (gen \(cur.gen)), using it"); return
                }
                usleep(100_000)
            }
            Log.t("lease acquired after \(ms(t0)) ms")
            Activity.run("token-refresh", {
                Log.t("lease: \(refreshIfStill(stale))")
                Lease.release()
            }, expired: {
                // The request may already have rotated the token on the server. Releasing would let another
                // process present the old one, so the lease is left to run out. The outcome is unknown.
                Log.t("lease: expired mid-refresh, outcome unknown, lease left to time out")
            })
        }
    }

    static func ms(_ t0: Date) -> Int { Int(Date().timeIntervalSince(t0) * 1000) }

    static func loop(_ cfg: RunConfig) {
        let wait = cfg.startAt - Date().timeIntervalSince1970
        if wait > 0 { Log.t("waiting \(Int(wait)) s for the shared start"); usleep(useconds_t(wait * 1_000_000)) }
        Log.t("loop start mode=\(cfg.mode.rawValue) n=\(cfg.iterations)")
        for _ in 0..<cfg.iterations {
            refresh(mode: cfg.mode)
            usleep(useconds_t(cfg.pauseMs * 1000 + Int.random(in: 0...100) * 1000))
        }
        Log.t("loop end")
    }
}

enum Lease {
    struct Record: Codable { var pid: Int32; var who: String; var expires: Double }
    private static func withFileLock<T>(_ f: () -> T) -> T {
        let fd = open(Cfg.lockURL.path, O_RDWR | O_CREAT, 0o644)
        flock(fd, LOCK_EX); defer { flock(fd, LOCK_UN); close(fd) }
        return f()
    }
    static func acquire() -> Bool {
        withFileLock {
            let now = Date().timeIntervalSince1970
            if let d = try? Data(contentsOf: Cfg.leaseURL), let r = try? JSONDecoder().decode(Record.self, from: d), r.expires > now {
                return false
            }
            let r = Record(pid: getpid(), who: Log.who, expires: now + Cfg.leaseSeconds)
            try? JSONEncoder().encode(r).write(to: Cfg.leaseURL, options: .atomic)
            return true
        }
    }
    static func release() {
        withFileLock {
            if let d = try? Data(contentsOf: Cfg.leaseURL), let r = try? JSONDecoder().decode(Record.self, from: d), r.pid == getpid() {
                try? FileManager.default.removeItem(at: Cfg.leaseURL)
            }
        }
    }
}
