import AppData
import Domain
import Foundation

/// A server's public timelines as lists of post IDs, newest first, read in pages of `pageSize` as my pages them: the
/// cursor of a page names its last post, and the page older than it starts after that post. It records every page it
/// was asked, and fails with the errors queued in `failures`, one per request.
final class StubServer: TimelineSource, @unchecked Sendable {
    /// A page the sync asked for.
    struct Request: Hashable, CustomStringConvertible {
        let timeline: PublicTimeline
        let context: ReaderContext
        let cursor: String?

        var description: String { "\(timeline) \(context.host) \(cursor ?? "newest")" }
    }

    let pageSize: Int
    private let lock = NSLock()
    private var lists: [String: [String]] = [:]
    private var countsByID: [String: PostCounts] = [:]
    private var queuedFailures: [(any Error)?] = []
    private var requests: [Request] = []

    init(pageSize: Int) {
        self.pageSize = pageSize
    }

    private static func key(_ timeline: PublicTimeline, _ context: ReaderContext) -> String {
        "\(context.key) \(timeline.rawValue)"
    }

    /// The posts of `timeline` of `context`, newest first.
    func list(of timeline: PublicTimeline = .local, in context: ReaderContext = .test) -> [String] {
        lock.withLock { lists[Self.key(timeline, context)] ?? [] }
    }

    /// Makes `ids`, newest first, the posts of `timeline` of `context`.
    func set(_ ids: [String], of timeline: PublicTimeline = .local, in context: ReaderContext = .test) {
        lock.withLock { lists[Self.key(timeline, context)] = ids }
    }

    /// Posts `ids`, newest first, above the posts of `timeline` of `context`.
    func post(_ ids: [String], to timeline: PublicTimeline = .local, in context: ReaderContext = .test) {
        lock.withLock { lists[Self.key(timeline, context), default: []].insert(contentsOf: ids, at: 0) }
    }

    /// Makes the counts of the post `id` `counts` from now on.
    func setCounts(_ counts: PostCounts, of id: String) {
        lock.withLock { countsByID[id] = counts }
    }

    /// Makes the next requests fail with `errors` in turn, nil for one that answers.
    func fail(with errors: (any Error)?...) {
        lock.withLock { queuedFailures += errors }
    }

    /// The pages asked so far, in order.
    var asked: [Request] {
        lock.withLock { requests }
    }

    /// The cursors of the pages asked so far, nil for the newest page.
    var cursors: [String?] { asked.map(\.cursor) }

    /// Forgets the pages asked so far.
    func forgetRequests() {
        lock.withLock { requests = [] }
    }

    /// The cursor of a page whose last post is `id`.
    static func cursor(after id: String) -> String { "after:\(id)" }

    func page(of timeline: PublicTimeline, in context: ReaderContext, cursor: String?) async throws -> PostPage {
        let (failure, list, counts): ((any Error)?, [String], [String: PostCounts]) = lock.withLock {
            requests.append(Request(timeline: timeline, context: context, cursor: cursor))
            let failure = queuedFailures.isEmpty ? nil : queuedFailures.removeFirst()
            return (failure, lists[Self.key(timeline, context)] ?? [], countsByID)
        }
        if let failure { throw failure }
        var start = 0
        if let cursor {
            let last = String(cursor.dropFirst("after:".count))
            guard let index = list.firstIndex(of: last) else {
                return PostPage(posts: [], accounts: [], entryIDs: [], nextCursor: nil)
            }
            start = index + 1
        }
        let ids = Array(list[start..<min(start + pageSize, list.count)])
        let next = start + pageSize < list.count ? ids.last.map(Self.cursor(after:)) : nil
        return PostPage(
            posts: ids.map { StubServer.post($0, counts: counts[$0]) },
            accounts: [StubServer.author],
            entryIDs: ids,
            nextCursor: next
        )
    }

    static let author = Account(
        id: "author",
        displayName: [ContentRun(content: .text("Author"))],
        handle: "@author",
        avatar: nil
    )

    /// A post whose content is its ID.
    static func post(_ id: String, counts: PostCounts? = nil) -> Post {
        Post(
            id: id,
            accountID: author.id,
            createdAt: Date(timeIntervalSince1970: 1_800_000_000),
            content: [ContentBlock(kind: .paragraph, runs: [ContentRun(content: .text(id))])],
            counts: counts ?? PostCounts(replies: 0, boosts: 0, favourites: 0)
        )
    }
}

/// `count` post IDs named `prefix` and a number, newest first: `ids("p", 3)` is p0003, p0002, p0001.
func ids(_ prefix: String, _ count: Int) -> [String] {
    (1...max(count, 1)).reversed().prefix(count).map { prefix + String(format: "%04d", $0) }
}

extension ReaderContext {
    /// The server of the tests.
    static let test = ReaderContext(origin: URL(string: "https://my.example")!)
    /// Another server.
    static let other = ReaderContext(origin: URL(string: "https://other.example")!)
}
