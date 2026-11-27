import Domain
import Foundation
import Testing

/// What a person types as a server address becomes the origin the client fetches from, as both apps read it
/// (shared/fixtures/server-address/cases.json).
struct ServerAddressTests {
    /// A case of the fixture: an input, and the origin and host it gives, or nil for both when it is invalid.
    struct Case: Decodable, CustomTestStringConvertible, Sendable {
        let input: String
        let origin: String?
        let host: String?

        var testDescription: String { "\"\(input)\"" }
    }

    static let cases: [Case] = (try? SharedFixture.decode([Case].self, from: "server-address/cases.json")) ?? []

    /// The parts of an origin that make it one: its scheme, host and port, and nothing after them.
    struct Origin: Equatable, CustomStringConvertible {
        let scheme: String?
        let host: String?
        let port: Int?
        let rest: String

        init(_ url: URL) {
            let components = URLComponents(url: url, resolvingAgainstBaseURL: false)
            scheme = components?.scheme
            host = components?.host
            port = components?.port
            // Only an empty path or "/" is no path; a user, a query or a fragment is something after the origin.
            let path = components?.path ?? ""
            rest =
                (path == "/" ? "" : path)
                + (components?.user.map { " user \($0)" } ?? "")
                + (components?.query.map { "?\($0)" } ?? "")
                + (components?.fragment.map { "#\($0)" } ?? "")
        }

        var description: String { "\(scheme ?? "")://\(host ?? "")\(port.map { ":\($0)" } ?? "")\(rest)" }
    }

    @Test func theFixtureHasCases() {
        #expect(!Self.cases.isEmpty, "shared/fixtures/server-address/cases.json did not decode")
    }

    @Test(arguments: cases.filter { $0.origin != nil })
    func aValidAddressGivesItsOriginAndHost(_ testCase: Case) throws {
        let address = try #require(ServerAddress(parsing: testCase.input), "\"\(testCase.input)\" was refused")
        let expected = try #require(testCase.origin.flatMap(URL.init(string:)))

        #expect(Origin(address.origin) == Origin(expected), "\"\(testCase.input)\" gave \(address.origin)")
        #expect(address.host == testCase.host)
    }

    @Test(arguments: cases.filter { $0.origin == nil })
    func anInvalidAddressIsRefused(_ testCase: Case) {
        let address = ServerAddress(parsing: testCase.input)
        #expect(address == nil, "\"\(testCase.input)\" was read as \(address.map { "\($0.origin)" } ?? "")")
    }

    @Test func plainHTTPIsForTheLocalMyOnly() {
        #expect(ServerAddress.localHosts == ["localhost", "127.0.0.1", "10.0.2.2"])
    }
}
