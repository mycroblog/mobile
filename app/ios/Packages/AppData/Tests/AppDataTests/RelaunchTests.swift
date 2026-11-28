import AppData
import Domain
import Foundation
import Testing

/// After a relaunch the app shows the same server, the same timeline and the same position, from what it kept before
/// the network answers (#42): the chosen server in the user defaults, the timeline and the position in the store's file.
struct RelaunchTests {
    /// A suite of user defaults of its own, removed when the test ends.
    final class Suite {
        let name = "dev.mycroblog.tests.\(UUID().uuidString)"

        deinit { UserDefaults.standard.removePersistentDomain(forName: name) }
    }

    /// A directory of its own for a store file, removed when the test ends.
    final class Directory {
        let url = FileManager.default.temporaryDirectory.appending(path: "AppDataTests-\(UUID().uuidString)")

        init() throws {
            try FileManager.default.createDirectory(at: url, withIntermediateDirectories: true)
        }

        var store: URL { url.appending(path: "store.sqlite") }

        deinit { try? FileManager.default.removeItem(at: url) }
    }

    static let server = ChosenServer(context: .test, publicTimelines: [.local, .federated])

    @Test func noServerIsChosenAtFirst() {
        let suite = Suite()
        #expect(ChosenServerStore(suiteName: suite.name).load() == nil)
    }

    @Test func theChosenServerSurvivesANewStoreOnTheSameSuite() {
        let suite = Suite()
        ChosenServerStore(suiteName: suite.name).save(Self.server)

        #expect(ChosenServerStore(suiteName: suite.name).load() == Self.server)
    }

    @Test func choosingAnotherServerReplacesIt() {
        let suite = Suite()
        let other = ChosenServer(context: .other, publicTimelines: [])
        ChosenServerStore(suiteName: suite.name).save(Self.server)
        ChosenServerStore(suiteName: suite.name).save(other)

        #expect(ChosenServerStore(suiteName: suite.name).load() == other)
    }

    @Test func aForgottenServerStaysForgotten() {
        let suite = Suite()
        ChosenServerStore(suiteName: suite.name).save(Self.server)
        ChosenServerStore(suiteName: suite.name).save(nil)

        #expect(ChosenServerStore(suiteName: suite.name).load() == nil)
    }

    @Test func anotherKeyOrSuiteKeepsItsOwnServer() {
        let suite = Suite()
        let another = Suite()
        ChosenServerStore(suiteName: suite.name).save(Self.server)

        #expect(ChosenServerStore(suiteName: suite.name, key: "Another").load() == nil)
        #expect(ChosenServerStore(suiteName: another.name).load() == nil)
    }

    /// The store of a new launch, opened on the same file with no network, reads the timeline, its paging and the
    /// position that the last launch wrote.
    @Test func theStoreOnDiskIsReadWithNoNetwork() async throws {
        let directory = try Directory()
        let online = StubServer(pageSize: 10)
        online.set(ids("p", 100))
        let position = ReadingPosition(anchorID: "p0024", offset: 40)
        do {
            let store = try TimelineStore.onDisk(at: directory.store)
            let sync = TimelineSync(store: store, source: online)
            try await sync.refresh(.local, in: .test)
            try await sync.loadOlder(.local, in: .test)
            try await sync.saveReadingPosition(position, of: .local, in: .test)
        }

        let offline = StubServer(pageSize: 10)
        let store = try TimelineStore.onDisk(at: directory.store)
        let snapshot = try await store.snapshot(of: .local, in: .test)

        #expect(snapshot.items.map(\.id) == Array(ids("p", 100).prefix(60)))
        #expect(snapshot.items.first?.post.content.plainText == "p0100")
        #expect(snapshot.items.first?.author == StubServer.author)
        #expect(snapshot.wasLoaded)
        #expect(snapshot.hasOlder)
        #expect(try await store.readingPosition(of: .local, in: .test) == position)
        #expect(offline.asked.isEmpty)
    }

    /// With no network, a refresh fails and the kept timeline stays; the older page still follows the kept cursor.
    @Test func withNoNetworkTheKeptTimelineStays() async throws {
        let directory = try Directory()
        let online = StubServer(pageSize: 10)
        online.set(ids("p", 100))
        do {
            let sync = TimelineSync(store: try TimelineStore.onDisk(at: directory.store), source: online)
            try await sync.refresh(.local, in: .test)
        }

        let offline = StubServer(pageSize: 10)
        offline.fail(with: TimelineLoadError.unreachable)
        let store = try TimelineStore.onDisk(at: directory.store)
        let sync = TimelineSync(store: store, source: offline)
        await #expect(throws: TimelineLoadError.unreachable) { try await sync.refresh(.local, in: .test) }

        #expect(try await store.entryIDs(of: .local, in: .test) == Array(ids("p", 100).prefix(50)))
        offline.set(ids("p", 100))
        try await sync.loadOlder(.local, in: .test)
        #expect(offline.cursors.last == .some(StubServer.cursor(after: "p0051")))
    }
}
