import API
import AppData
import Domain
import Foundation
import XCTest

/// The sync and the store against a local my (ADR-0006 §1), which `task ios:test` starts and names in MY_URL. Its seed
/// has 15 local posts, offers the local timeline to everyone and the federated one to signed-in readers only.
final class PublicTimelineIntegrationTests: XCTestCase {
    var context: ReaderContext!
    var store: TimelineStore!
    var sync: TimelineSync!

    override func setUp() async throws {
        context = ReaderContext(origin: try LocalMy.origin())
        store = try TimelineStore.inMemory()
        sync = TimelineSync(store: store, source: RemoteTimelineSource())
    }

    /// The posts of the local timeline, newest first, as the API gives them in one page.
    func serverList() async throws -> [String] {
        try await APIClient(origin: LocalMy.origin()).page(of: .local, limit: 40).entryIDs
    }

    func testARefreshStoresTheLocalTimeline() async throws {
        let expected = try await serverList()

        try await sync.refresh(.local, in: context)
        var pages = 1
        while try await store.snapshot(of: .local, in: context).hasOlder, pages < 10 {
            try await sync.loadOlder(.local, in: context)
            pages += 1
        }

        let snapshot = try await store.snapshot(of: .local, in: context)
        XCTAssertTrue(snapshot.wasLoaded)
        XCTAssertEqual(snapshot.items.map(\.id), expected)
        for item in snapshot.items {
            XCTAssertFalse(item.author.displayName.isEmpty, "the author of \(item.id) has no name")
        }
    }

    /// Opening the timeline again meets the stored list on the newest page and keeps it as it was.
    func testARefreshAgainKeepsTheList() async throws {
        try await sync.refresh(.local, in: context)
        let before = try await store.entryIDs(of: .local, in: context)

        try await sync.refresh(.local, in: context)

        let after = try await store.entryIDs(of: .local, in: context)
        XCTAssertEqual(after, before)
    }

    /// A store on disk keeps the timeline and the position for the next launch.
    func testTheTimelineAndThePositionSurviveANewStoreOnTheSameFile() async throws {
        let directory = FileManager.default.temporaryDirectory
            .appending(path: "AppDataIntegration-\(UUID().uuidString)")
        try FileManager.default.createDirectory(at: directory, withIntermediateDirectories: true)
        defer { try? FileManager.default.removeItem(at: directory) }
        let file = directory.appending(path: "store.sqlite")
        var position: ReadingPosition?
        var stored: [String] = []
        do {
            let sync = TimelineSync(store: try TimelineStore.onDisk(at: file), source: RemoteTimelineSource())
            try await sync.refresh(.local, in: context)
            stored = try await sync.store.entryIDs(of: .local, in: context)
            position = ReadingPosition(anchorID: try XCTUnwrap(stored.dropFirst(2).first), offset: 8)
            try await sync.saveReadingPosition(position, of: .local, in: context)
        }

        let relaunched = try TimelineStore.onDisk(at: file)

        let snapshot = try await relaunched.snapshot(of: .local, in: context)
        let restored = try await relaunched.readingPosition(of: .local, in: context)
        XCTAssertEqual(snapshot.items.map(\.id), stored)
        XCTAssertEqual(restored, position)
    }

    /// The federated timeline is for signed-in readers: the sync throws and stores nothing.
    func testTheFederatedTimelineIsRefusedAndNothingIsStored() async throws {
        do {
            try await sync.refresh(.federated, in: context)
            XCTFail("the federated timeline was read without signing in")
        } catch is TimelineLoadError {}

        let federated = try await store.entryIDs(of: .federated, in: context)
        XCTAssertTrue(federated.isEmpty)
    }

    /// Port 1 of this Mac (tcpmux) has no server: the refresh is unreachable and the store keeps what it had.
    func testAServerThatDoesNotAnswerIsUnreachable() async throws {
        let closed = ReaderContext(origin: try XCTUnwrap(URL(string: "http://localhost:1")))

        do {
            try await sync.refresh(.local, in: closed)
            XCTFail("a closed port gave a timeline")
        } catch let error as TimelineLoadError {
            XCTAssertEqual(error, .unreachable)
        }
        let snapshot = try await store.snapshot(of: .local, in: closed)
        XCTAssertFalse(snapshot.wasLoaded)
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
