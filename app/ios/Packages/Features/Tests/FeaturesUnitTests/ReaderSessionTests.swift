import AppData
import Domain
import Foundation
import Testing

@testable import Features

/// The reader who has not signed in: the destinations of the public timelines appear only as the chosen server offers
/// them to anonymous readers, and not after one answers 404 (ADR-0002 §6); the server and the timeline come back after a
/// relaunch from what was kept, before the network answers (#42).
@MainActor
struct ReaderSessionTests {
    static let address = ServerAddress(parsing: "my.example")!

    static func instance(local: InstanceAudience, federated: InstanceAudience, singleUser: Bool = false) -> Instance {
        Instance(
            name: "My Example",
            timelines: InstanceTimelines(local: local, federated: federated),
            isSingleUser: singleUser
        )
    }

    static func session(
        suite: DefaultsSuite,
        source: StubTimelineSource = StubTimelineSource(),
        store: TimelineStore? = nil
    ) throws -> ReaderSession {
        ReaderSession(
            instanceLookup: StubLookup(answers: []),
            sync: TimelineSync(store: try store ?? TimelineStore.inMemory(), source: source),
            serverStore: ChosenServerStore(suiteName: suite.name)
        )
    }

    @Test func noServerIsChosenAtFirst() throws {
        let session = try Self.session(suite: DefaultsSuite())
        #expect(session.server == nil)
        #expect(session.publicTimelines.isEmpty)
        #expect(session.model(of: .local) == nil)
    }

    @Test(arguments: [
        (InstanceAudience.everyone, InstanceAudience.everyone, false, [PublicTimeline.local, .federated]),
        (.everyone, .signedIn, false, [.local]),
        (.off, .everyone, false, [.federated]),
        (.signedIn, .off, false, []),
        (.everyone, .everyone, true, []),
    ])
    func theDestinationsAreTheTimelinesOffered(
        _ local: InstanceAudience,
        _ federated: InstanceAudience,
        _ singleUser: Bool,
        _ offered: [PublicTimeline]
    ) throws {
        let session = try Self.session(suite: DefaultsSuite())

        let instance = Self.instance(local: local, federated: federated, singleUser: singleUser)

        session.choose(Self.address, instance: instance)

        #expect(session.server?.context == ReaderContext(server: Self.address))
        #expect(session.publicTimelines == offered)
    }

    @Test func theChosenServerComesBackInANewSessionOnTheSameDefaults() throws {
        let suite = DefaultsSuite()
        try Self.session(suite: suite).choose(Self.address, instance: Self.instance(local: .everyone, federated: .off))

        let relaunched = try Self.session(suite: suite)

        #expect(relaunched.server?.context == ReaderContext(server: Self.address))
        #expect(relaunched.publicTimelines == [.local])
        #expect(relaunched.model(of: .local) != nil)
    }

    @Test func aTimelineThatAnswersNotFoundLosesItsDestination() async throws {
        let source = StubTimelineSource(failures: [.notFound])
        let session = try Self.session(suite: DefaultsSuite(), source: source)
        session.choose(Self.address, instance: Self.instance(local: .everyone, federated: .everyone))
        let model = try #require(session.model(of: .local))

        await model.refresh()

        #expect(session.unavailableTimelines == [.local])
        #expect(session.publicTimelines == [.federated])
    }

    /// The store's timeline and the reading position show before the network answers.
    @Test func aRelaunchShowsTheStoredTimelineBeforeTheNetworkAnswers() async throws {
        let suite = DefaultsSuite()
        let store = try TimelineStore.inMemory()
        let first = StubTimelineSource(list: ["p3", "p2", "p1"])
        let session = try Self.session(suite: suite, source: first, store: store)
        session.choose(Self.address, instance: Self.instance(local: .everyone, federated: .off))
        let model = try #require(session.model(of: .local))
        await model.refresh()
        model.save(position: ReadingPosition(anchorID: "p2", offset: 16))
        let context = ReaderContext(server: Self.address)
        #expect(try await store.entryIDs(of: .local, in: context) == ["p3", "p2", "p1"])
        #expect(
            try await eventually {
                try await store.readingPosition(of: .local, in: context) == ReadingPosition(anchorID: "p2", offset: 16)
            },
            "the position was not saved"
        )

        let offline = StubTimelineSource(list: ["p4", "p3", "p2", "p1"])
        offline.isHeld = true
        let relaunched = try Self.session(suite: suite, source: offline, store: store)
        let restored = try #require(relaunched.model(of: .local), "the relaunched session has no Local timeline")
        let running = Task { await restored.run() }
        defer { running.cancel() }

        #expect(await eventually { restored.items.map(\.id) == ["p3", "p2", "p1"] })
        #expect(restored.savedPosition == ReadingPosition(anchorID: "p2", offset: 16))
        #expect(restored.hasReadStore)
        offline.release()
        #expect(await eventually { restored.items.map(\.id) == ["p4", "p3", "p2", "p1"] })
    }
}
