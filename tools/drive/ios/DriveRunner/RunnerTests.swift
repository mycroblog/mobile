// The iOS runner of the app-driving tool (ADR-0011 §3): a UI test that never ends. It listens on TCP
// 127.0.0.1:$DRIVE_PORT (default 22087), reads one JSON request per line and writes one JSON reply per line, until it
// gets {"op":"quit"}. xcodebuild passes TEST_RUNNER_DRIVE_PORT to the test as DRIVE_PORT.
import Network
import XCTest

final class RunnerTests: XCTestCase {
    private var done = false
    /// The XCTest issues recorded while a request runs: a failed XCUITest action records one instead of throwing.
    private var issues: [String] = []
    private lazy var driver = Driver(issues: { [unowned self] in self.issues })

    func testServe() throws {
        continueAfterFailure = true
        let raw = ProcessInfo.processInfo.environment["DRIVE_PORT"] ?? ""
        guard let port = NWEndpoint.Port(rawValue: UInt16(raw) ?? 22087) else { throw Driver.Failure("bad DRIVE_PORT") }
        let params = NWParameters.tcp
        params.requiredLocalEndpoint = .hostPort(host: "127.0.0.1", port: port)
        params.allowLocalEndpointReuse = true
        let listener = try NWListener(using: params)
        listener.newConnectionHandler = { [weak self] connection in
            connection.start(queue: .main)
            self?.receive(on: connection, buffer: Data())
        }
        listener.start(queue: .main)
        NSLog("DRIVE_RUNNER ready on 127.0.0.1:\(port)")
        while !done { RunLoop.main.run(until: Date().addingTimeInterval(0.05)) }
        listener.cancel()
    }

    override func record(_ issue: XCTIssue) {
        // Kept as the reply's error instead of failing the test, which must keep serving.
        issues.append(issue.compactDescription)
        NSLog("DRIVE_RUNNER issue: \(issue.compactDescription)")
    }

    private func receive(on connection: NWConnection, buffer: Data) {
        connection.receive(minimumIncompleteLength: 1, maximumLength: 1 << 20) { [weak self] data, _, end, error in
            guard let self else { return }
            var buffer = buffer + (data ?? Data())
            while let newline = buffer.firstIndex(of: 0x0A) {
                let line = Data(buffer[buffer.startIndex..<newline])
                buffer = Data(buffer[buffer.index(after: newline)...])
                if line.allSatisfy({ $0 == 0x20 || $0 == 0x0D }) { continue }
                self.issues = []
                let (reply, quit) = self.driver.handle(line)
                var out = (try? JSONSerialization.data(withJSONObject: reply)) ?? Data(#"{"ok":false}"#.utf8)
                out.append(0x0A)
                connection.send(content: out, completion: .contentProcessed { _ in if quit { self.done = true } })
            }
            if end || error != nil { connection.cancel() } else { self.receive(on: connection, buffer: buffer) }
        }
    }
}
