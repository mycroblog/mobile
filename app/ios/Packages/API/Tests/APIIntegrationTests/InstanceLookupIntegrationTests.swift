import API
import Domain
import Foundation
import XCTest

/// The lookup of the server screen against a local my, and against an address where nothing answers.
final class InstanceLookupIntegrationTests: XCTestCase {
    func testTheLocalMyGivesItsInstance() async throws {
        let client = try APIClient(origin: LocalMy.origin())

        let instance = try await client.instance()

        XCTAssertFalse(instance.name.isEmpty, "the instance of the local my has an empty name")
    }

    /// Port 1 of this Mac (tcpmux) has no server, so the connection is refused at once.
    func testAClosedPortIsUnreachable() async throws {
        let client = APIClient(origin: try XCTUnwrap(URL(string: "http://localhost:1")))

        do {
            let instance = try await client.instance()
            XCTFail("a closed port gave the instance \(instance.name)")
        } catch let error as InstanceLookupError {
            XCTAssertEqual(error, .unreachable)
        }
    }
}
