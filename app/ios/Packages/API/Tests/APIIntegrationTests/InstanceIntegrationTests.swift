import API
import Foundation
import GeneratedAPI
import XCTest

/// The client against a local my (ADR-0006 §1), which `task ios:test` starts and names in MY_URL.
final class InstanceIntegrationTests: XCTestCase {
    func testTheInstanceDecodesWithAName() async throws {
        let client = try APIClient(origin: LocalMy.origin())

        let instance = try await client.generated.getInstance().ok.body.json

        XCTAssertFalse(instance.name.isEmpty, "my answered GET /api/v1/instance with an empty name")
    }
}

/// The local my of `task my:up`.
enum LocalMy {
    /// Its origin, from the environment variable MY_URL, which `task ios:test` sets to what `scripts/my.sh url` prints.
    static func origin() throws -> URL {
        guard let value = ProcessInfo.processInfo.environment["MY_URL"], let url = URL(string: value) else {
            throw Missing()
        }
        return url
    }

    struct Missing: LocalizedError {
        var errorDescription: String? {
            "MY_URL is not set: run these tests with task ios:test, which starts a local my and passes its URL"
        }
    }
}
