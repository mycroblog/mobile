import AppData
import Domain
import SnapshotTesting
import SwiftUI
import Testing

@testable import Features

/// The server screen in each of its states, in its navigation stack, as it renders on an iPhone in portrait. The model
/// starts in the state it is given, so each image shows that state without a lookup.
@MainActor
struct ServerLookupSnapshotTests {
    /// A lookup the screen never reaches: the state is given, and nothing is looked up.
    struct NoLookup: InstanceLookup {
        func instance(at address: ServerAddress) async throws -> Instance {
            throw InstanceLookupError.unreachable
        }
    }

    static func assertScreen(
        address: String,
        state: ServerLookupModel.State,
        fileID: StaticString = #fileID,
        file: StaticString = #filePath,
        testName: String = #function,
        line: UInt = #line,
        column: UInt = #column
    ) {
        let model = ServerLookupModel(lookup: NoLookup(), address: address, state: state)
        let screen = NavigationStack {
            ServerLookupScreen(model: model)
        }
        assertSnapshot(
            of: screen,
            as: .image(layout: .device(config: .iPhone13)),
            named: "iPhone",
            fileID: fileID,
            file: file,
            testName: testName,
            line: line,
            column: column
        )
    }

    @Test func idleWithAnEmptyField() {
        Self.assertScreen(address: "", state: .idle)
    }

    @Test func invalidAddress() {
        Self.assertScreen(address: "ftp://example.social", state: .invalidAddress)
    }

    @Test func loading() {
        Self.assertScreen(address: "example.social", state: .loading(host: "example.social"))
    }

    @Test func loadedWithAShortDescription() {
        let instance = Instance(
            name: "Example Social",
            shortDescription: "A place on the fediverse to test the mobile apps with."
        )
        Self.assertScreen(address: "example.social", state: .loaded(host: "example.social", instance: instance))
    }

    @Test func loadedWithoutAShortDescription() {
        let instance = Instance(name: "Example Social", shortDescription: nil)
        Self.assertScreen(address: "example.social", state: .loaded(host: "example.social", instance: instance))
    }

    @Test func failedUnreachable() {
        Self.assertScreen(address: "example.social", state: .failed(host: "example.social", error: .unreachable))
    }

    @Test func failedNotMycroblog() {
        Self.assertScreen(address: "example.social", state: .failed(host: "example.social", error: .notMycroblog))
    }

    @Test func failedWithAProblem() {
        Self.assertScreen(
            address: "example.social",
            state: .failed(
                host: "example.social",
                error: .problem(title: "The server is down for maintenance.", status: 503)
            )
        )
    }
}
