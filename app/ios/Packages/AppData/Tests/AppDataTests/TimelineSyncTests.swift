import AppData
import Domain
import Foundation
import Testing

/// The sync keeps the store's timelines in step with the server by the rule of ADR-0008 §3: a refresh reads the newest
/// page and older ones until they meet the stored list, and replaces the list when they do not meet within
/// `maxPagesToMeet` pages; an older page follows the cursor of the stored list's last page; a launch prunes each
/// timeline to `maxEntriesPerTimeline`.
struct TimelineSyncTests {
    let server = StubServer(pageSize: 10)
    let store: TimelineStore
    let sync: TimelineSync

    init() throws {
        store = try TimelineStore.inMemory()
        sync = TimelineSync(store: store, source: server)
    }

    func entries(_ timeline: PublicTimeline = .local, in context: ReaderContext = .test) async throws -> [String] {
        try await store.entryIDs(of: timeline, in: context)
    }

    func snapshot(_ timeline: PublicTimeline = .local, in context: ReaderContext = .test) async throws
        -> TimelineSnapshot
    {
        try await store.snapshot(of: timeline, in: context)
    }

    @Test func theLimitsAreThoseOfTheDesign() {
        #expect(TimelineSync.maxPagesToMeet == 5)
        #expect(TimelineStore.maxEntriesPerTimeline == 1000)
    }

    @Test func aTimelineNeverReadIsNotLoaded() async throws {
        let snapshot = try await snapshot()
        #expect(snapshot.items.isEmpty)
        #expect(!snapshot.wasLoaded)
        #expect(try await store.readingPosition(of: .local, in: .test) == nil)
    }

    /// With nothing stored, a refresh reads from the newest page on, at most `maxPagesToMeet` pages.
    @Test func theFirstRefreshStoresTheNewestPosts() async throws {
        server.set(ids("p", 100))

        try await sync.refresh(.local, in: .test)

        let stored = try await entries()
        #expect(server.cursors.first == .some(nil), "the refresh did not start at the newest page")
        #expect(server.asked.count <= TimelineSync.maxPagesToMeet)
        #expect(!stored.isEmpty)
        #expect(stored == Array(ids("p", 100).prefix(stored.count)), "the stored list is not the newest posts in order")
        let snapshot = try await snapshot()
        #expect(snapshot.items.map(\.id) == stored)
        #expect(snapshot.items.first?.post.content.plainText == stored.first)
        #expect(snapshot.items.first?.author.handle == "@author")
        #expect(snapshot.wasLoaded)
        #expect(snapshot.hasOlder)
    }

    @Test func anEmptyTimelineIsLoadedWithNothingOlder() async throws {
        try await sync.refresh(.local, in: .test)

        let snapshot = try await snapshot()
        #expect(snapshot.items.isEmpty)
        #expect(snapshot.wasLoaded)
        #expect(!snapshot.hasOlder)
    }

    @Test func aRefreshWithNothingNewReadsOnePageAndKeepsTheList() async throws {
        server.set(ids("p", 30))
        try await sync.refresh(.local, in: .test)
        let before = try await entries()
        server.forgetRequests()

        try await sync.refresh(.local, in: .test)

        #expect(server.cursors == [nil])
        #expect(try await entries() == before)
    }

    @Test func newPostsThatMeetOnTheFirstPageGoAboveTheList() async throws {
        server.set(ids("p", 30))
        try await sync.refresh(.local, in: .test)
        let before = try await entries()
        server.post(ids("n", 3))
        server.forgetRequests()

        try await sync.refresh(.local, in: .test)

        #expect(server.cursors == [nil])
        #expect(try await entries() == ids("n", 3) + before)
    }

    /// 25 new posts take three pages of 10 to meet the stored list: the new ones go above it, in order, once each.
    @Test func newPostsThatMeetWithinThePagesKeepTheOrder() async throws {
        server.set(ids("p", 30))
        try await sync.refresh(.local, in: .test)
        let before = try await entries()
        server.post(ids("n", 25))
        server.forgetRequests()

        try await sync.refresh(.local, in: .test)

        #expect(server.cursors == [nil, StubServer.cursor(after: "n0016"), StubServer.cursor(after: "n0006")])
        #expect(try await entries() == ids("n", 25) + before)
    }

    /// 45 new posts meet the stored list on the fifth page, the last one a refresh reads: the list is kept.
    @Test func newPostsThatMeetOnTheLastPageKeepTheList() async throws {
        server.set(ids("p", 30))
        try await sync.refresh(.local, in: .test)
        let before = try await entries()
        server.post(ids("n", 45))
        server.forgetRequests()

        try await sync.refresh(.local, in: .test)

        #expect(server.asked.count == TimelineSync.maxPagesToMeet)
        #expect(try await entries() == ids("n", 45) + before)
    }

    /// 60 new posts do not meet the stored list within five pages of 10: the list becomes the 50 posts read, with no
    /// gap left in it.
    @Test func newPostsThatNeverMeetInFivePagesReplaceTheList() async throws {
        server.set(ids("p", 30))
        try await sync.refresh(.local, in: .test)
        server.post(ids("n", 60))
        server.forgetRequests()

        try await sync.refresh(.local, in: .test)

        #expect(server.asked.count == TimelineSync.maxPagesToMeet)
        #expect(try await entries() == Array(ids("n", 60).prefix(50)))
        #expect(try await snapshot().hasOlder)
    }

    /// After a replace, the older page is the one after the last page the refresh read.
    @Test func afterAReplaceAnOlderPageFollowsTheLastPageRead() async throws {
        server.set(ids("p", 30))
        try await sync.refresh(.local, in: .test)
        server.post(ids("n", 60))
        try await sync.refresh(.local, in: .test)
        server.forgetRequests()

        try await sync.loadOlder(.local, in: .test)

        #expect(server.cursors == [StubServer.cursor(after: "n0011")])
        #expect(try await entries() == ids("n", 60))
    }

    @Test func olderPagesFollowTheCursorToTheEnd() async throws {
        server.set(ids("p", 100))
        try await sync.refresh(.local, in: .test)
        let first = try await entries()
        let last = try #require(first.last)
        server.forgetRequests()

        try await sync.loadOlder(.local, in: .test)
        try await sync.loadOlder(.local, in: .test)
        while try await snapshot().hasOlder, server.asked.count < 10 {
            try await sync.loadOlder(.local, in: .test)
        }

        #expect(server.cursors.first == .some(StubServer.cursor(after: last)))
        #expect(try await entries() == ids("p", 100))
        #expect(try await !snapshot().hasOlder)
        let asked = server.asked.count
        try await sync.loadOlder(.local, in: .test)
        #expect(server.asked.count == asked, "an older page was asked after the end")
    }

    /// When a refresh meets the stored list, the older page still follows the stored list's last page, not the last
    /// page the refresh read.
    @Test func afterAMeetingRefreshAnOlderPageFollowsTheStoredList() async throws {
        server.set(ids("p", 100))
        try await sync.refresh(.local, in: .test)
        try await sync.loadOlder(.local, in: .test)
        let before = try await entries()
        let last = try #require(before.last)
        server.post(ids("n", 25))
        try await sync.refresh(.local, in: .test)
        server.forgetRequests()

        try await sync.loadOlder(.local, in: .test)

        #expect(server.cursors == [StubServer.cursor(after: last)])
        #expect(try await entries() == ids("n", 25) + ids("p", 100).prefix(before.count + 10))
    }

    /// A refresh that fails on a later page leaves no gap: the list is the stored one, or the newest page alone.
    @Test func aRefreshThatFailsHalfWayLeavesNoGap() async throws {
        server.set(ids("p", 30))
        try await sync.refresh(.local, in: .test)
        let before = try await entries()
        server.post(ids("n", 25))
        server.fail(with: nil, TimelineLoadError.unreachable)

        await #expect(throws: TimelineLoadError.unreachable) { try await sync.refresh(.local, in: .test) }

        let after = try await entries()
        #expect(after == before || after == Array(ids("n", 25).prefix(10)), "the list became \(after)")
    }

    @Test(arguments: [TimelineLoadError.notFound, .rateLimited(retryAfter: 30), .rateLimited(retryAfter: nil)])
    func aFailedRefreshThrowsItsErrorAndKeepsTheList(_ error: TimelineLoadError) async throws {
        server.set(ids("p", 30))
        try await sync.refresh(.local, in: .test)
        let before = try await entries()
        server.post(ids("n", 3))
        server.fail(with: error)

        await #expect(throws: error) { try await sync.refresh(.local, in: .test) }

        #expect(try await entries() == before)
        #expect(try await snapshot().wasLoaded)
    }

    @Test(arguments: [TimelineLoadError.notFound, .rateLimited(retryAfter: 30)])
    func aFailedOlderPageThrowsItsErrorAndCanBeAskedAgain(_ error: TimelineLoadError) async throws {
        server.set(ids("p", 100))
        try await sync.refresh(.local, in: .test)
        let before = try await entries()
        let last = try #require(before.last)
        server.fail(with: error)

        await #expect(throws: error) { try await sync.loadOlder(.local, in: .test) }
        #expect(try await entries() == before)
        server.forgetRequests()
        try await sync.loadOlder(.local, in: .test)

        #expect(server.cursors == [StubServer.cursor(after: last)])
        #expect(try await entries() == Array(ids("p", 100).prefix(before.count + 10)))
    }

    /// A launch cuts a timeline to its newest 1000 entries; the cut one pages no further back, so the reader never
    /// meets the gap where the cut entries were.
    @Test func pruningKeepsTheNewestThousand() async throws {
        let big = StubServer(pageSize: 400)
        let sync = TimelineSync(store: store, source: big)
        big.set(ids("p", 3000))
        try await sync.refresh(.local, in: .test)
        try await sync.loadOlder(.local, in: .test)
        try await sync.loadOlder(.local, in: .test)
        let last = try #require(try await entries().last)
        #expect(try await entries().count > 1000)
        #expect(try await snapshot().hasOlder)

        try await sync.prune()

        #expect(try await entries() == Array(ids("p", 3000).prefix(1000)))
        let snapshot = try await snapshot()
        #expect(snapshot.items.count == 1000)
        #expect(!snapshot.hasOlder)
        big.forgetRequests()
        try await sync.loadOlder(.local, in: .test)
        #expect(!big.cursors.contains(StubServer.cursor(after: last)), "an older page followed the cut entries")
    }

    @Test func pruningLeavesAShortTimelineAsItIs() async throws {
        server.set(ids("p", 30))
        try await sync.refresh(.local, in: .test)
        let before = try await snapshot()

        try await sync.prune()

        #expect(try await snapshot() == before)
    }

    @Test func pruningCutsEachTimelineOfEachServer() async throws {
        server.set(ids("l", 30), of: .local, in: .test)
        server.set(ids("f", 30), of: .federated, in: .test)
        server.set(ids("o", 30), of: .local, in: .other)
        for (timeline, context) in [(PublicTimeline.local, ReaderContext.test), (.federated, .test), (.local, .other)] {
            try await sync.refresh(timeline, in: context)
            try await sync.loadOlder(timeline, in: context)
        }

        try await sync.prune(maxEntries: 3)

        #expect(try await entries(.local, in: .test) == Array(ids("l", 30).prefix(3)))
        #expect(try await entries(.federated, in: .test) == Array(ids("f", 30).prefix(3)))
        #expect(try await entries(.local, in: .other) == Array(ids("o", 30).prefix(3)))
        #expect(try await snapshot(.local, in: .other).items.map(\.id) == Array(ids("o", 30).prefix(3)))
    }

    /// The reader context is keyed by the server (ADR-0007 §3): one server's timeline never shows another's posts.
    @Test func eachServerAndTimelineHasItsOwnList() async throws {
        server.set(ids("l", 5), of: .local, in: .test)
        server.set(ids("f", 5), of: .federated, in: .test)
        server.set(ids("o", 5), of: .local, in: .other)

        try await sync.refresh(.local, in: .test)
        try await sync.refresh(.federated, in: .test)
        try await sync.refresh(.local, in: .other)

        #expect(try await entries(.local, in: .test) == ids("l", 5))
        #expect(try await entries(.federated, in: .test) == ids("f", 5))
        #expect(try await entries(.local, in: .other) == ids("o", 5))
        #expect(try await snapshot(.federated, in: .other).items.isEmpty)
        #expect(server.asked.map(\.context) == [.test, .test, .other])
    }

    /// One record per post (ADR-0008 §1): a post on two timelines shows what the server said of it last on both.
    @Test func aPostOnTwoTimelinesIsOneRecord() async throws {
        server.set(["shared", "l0001"], of: .local)
        server.set(["shared", "f0001"], of: .federated)
        server.setCounts(PostCounts(replies: 1, boosts: 1, favourites: 1), of: "shared")
        try await sync.refresh(.local, in: .test)

        server.setCounts(PostCounts(replies: 5, boosts: 6, favourites: 7), of: "shared")
        try await sync.refresh(.federated, in: .test)

        let local = try await snapshot(.local).items.first { $0.id == "shared" }
        #expect(local?.post.counts == PostCounts(replies: 5, boosts: 6, favourites: 7))
    }

    @Test func theReadingPositionIsSavedAndForgotten() async throws {
        server.set(ids("p", 30))
        try await sync.refresh(.local, in: .test)
        let position = ReadingPosition(anchorID: "p0025", offset: 12.5)

        try await sync.saveReadingPosition(position, of: .local, in: .test)

        #expect(try await store.readingPosition(of: .local, in: .test) == position)
        #expect(try await store.readingPosition(of: .federated, in: .test) == nil)
        #expect(try await store.readingPosition(of: .local, in: .other) == nil)
        try await sync.saveReadingPosition(nil, of: .local, in: .test)
        #expect(try await store.readingPosition(of: .local, in: .test) == nil)
    }

    @Test func anObserverSeesTheTimelineAndItsChanges() async throws {
        server.set(ids("p", 5))
        var updates = store.observe(.local, in: .test).makeAsyncIterator()
        let first = try await updates.next()
        #expect(first?.items.isEmpty == true)

        try await sync.refresh(.local, in: .test)

        var seen = first
        for _ in 0..<5 where seen?.items.map(\.id) != ids("p", 5) {
            seen = try await updates.next()
        }
        #expect(seen?.items.map(\.id) == ids("p", 5))
    }
}
