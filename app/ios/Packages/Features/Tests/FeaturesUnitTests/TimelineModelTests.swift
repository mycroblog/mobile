import AppData
import Domain
import Foundation
import Testing

@testable import Features

/// The screen of a public timeline: it shows the store's posts, refreshes and pages older ones through the sync, and
/// shows a failure with retry, holding the retry back until the `Retry-After` of a 429 has passed.
@MainActor
struct TimelineModelTests {
    static let context = ReaderContext(origin: URL(string: "https://my.example")!)

    static func model(_ source: StubTimelineSource, onUnavailable: @escaping () -> Void = {}) throws -> TimelineModel {
        TimelineModel(
            timeline: .local,
            context: context,
            sync: TimelineSync(store: try TimelineStore.inMemory(), source: source),
            onUnavailable: onUnavailable
        )
    }

    @Test func runningShowsTheNewestPosts() async throws {
        let model = try Self.model(StubTimelineSource(list: postIDs(100), pageSize: 2))
        let running = Task { await model.run() }
        defer { running.cancel() }

        #expect(await eventually { !model.items.isEmpty && model.status == .idle })
        #expect(model.items.map(\.id) == Array(postIDs(100).prefix(model.items.count)))
        #expect(model.hasOlder)
        #expect(model.wasLoaded)
    }

    @Test func anOlderPageFollowsTheLastPost() async throws {
        let source = StubTimelineSource(list: postIDs(100), pageSize: 2)
        let model = try Self.model(source)
        let running = Task { await model.run() }
        defer { running.cancel() }
        #expect(await eventually { !model.items.isEmpty && model.status == .idle })
        let shown = model.items.map(\.id)
        let last = try #require(shown.last)

        await model.loadOlder()

        #expect(source.cursors.last == .some("after:\(last)"))
        #expect(await eventually { model.items.map(\.id) == Array(postIDs(100).prefix(shown.count + 2)) })
    }

    @Test func aTooManyRequestsFailureHoldsTheRetryUntilRetryAfter() async throws {
        let source = StubTimelineSource(list: ["p1"], failures: [.rateLimited(retryAfter: 30)])
        let model = try Self.model(source)
        let before = Date()

        await model.refresh()

        guard case .failed(let error, let retryAt) = model.status else {
            Issue.record("the status is \(model.status), not failed")
            return
        }
        #expect(error == .rateLimited(retryAfter: 30))
        let at = try #require(retryAt, "a 429 with Retry-After gave no time to retry at")
        #expect(at >= before.addingTimeInterval(29) && at <= Date().addingTimeInterval(31), "retry at \(at)")
        #expect(!model.canRefresh)

        await model.retry()
        await model.refresh()
        #expect(source.cursors.count == 1, "the timeline was asked again before Retry-After passed")
    }

    @Test func aTooManyRequestsFailureWithoutRetryAfterCanBeRetriedAtOnce() async throws {
        let source = StubTimelineSource(list: ["p1"], failures: [.rateLimited(retryAfter: nil)])
        let model = try Self.model(source)
        let running = Task { await model.run() }
        defer { running.cancel() }

        #expect(await eventually { model.status == .failed(.rateLimited(retryAfter: nil), retryAt: nil) })
        #expect(model.canRefresh)

        await model.retry()

        #expect(source.cursors.count == 2)
        #expect(model.status == .idle)
        #expect(await eventually { model.items.map(\.id) == ["p1"] })
    }

    @Test func anUnreachableServerFailsAndRetriesAtOnce() async throws {
        let source = StubTimelineSource(list: ["p1"], failures: [.unreachable])
        let model = try Self.model(source)
        let running = Task { await model.run() }
        defer { running.cancel() }

        #expect(await eventually { model.status == .failed(.unreachable, retryAt: nil) })

        await model.retry()
        #expect(model.status == .idle)
        #expect(await eventually { model.items.map(\.id) == ["p1"] })
    }

    @Test func notFoundSaysTheTimelineIsUnavailable() async throws {
        var unavailable = 0
        let model = try Self.model(StubTimelineSource(failures: [.notFound])) { unavailable += 1 }

        await model.refresh()

        #expect(unavailable == 1)
    }

    @Test func anEmptyTimelineIsLoadedWithNoPosts() async throws {
        let model = try Self.model(StubTimelineSource())
        let running = Task { await model.run() }
        defer { running.cancel() }

        #expect(await eventually { model.wasLoaded })
        #expect(model.items.isEmpty)
        #expect(!model.hasOlder)
    }
}
