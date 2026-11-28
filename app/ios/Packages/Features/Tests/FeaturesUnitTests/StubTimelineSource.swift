import AppData
import Domain
import Foundation

/// A server's timeline as a list of post IDs, newest first, read in pages of `pageSize`: the cursor of a page names its
/// last post. It fails with the errors queued in `failures`, one per request, and records the cursor of each request.
/// While it is held, a request waits until `release()`, so that a test sees what the screen shows before the network
/// answers.
@MainActor final class StubTimelineSource: TimelineSource {
    var list: [String]
    let pageSize: Int
    private(set) var cursors: [String?] = []
    private var failures: [TimelineLoadError]
    private var waiting: [CheckedContinuation<Void, Never>] = []
    var isHeld = false

    init(list: [String] = [], pageSize: Int = 10, failures: [TimelineLoadError] = []) {
        self.list = list
        self.pageSize = pageSize
        self.failures = failures
    }

    @MainActor func page(of timeline: PublicTimeline, in context: ReaderContext, cursor: String?) async throws
        -> PostPage
    {
        cursors.append(cursor)
        if isHeld {
            await withCheckedContinuation { waiting.append($0) }
        }
        if !failures.isEmpty { throw failures.removeFirst() }
        var start = 0
        if let cursor {
            guard let index = list.firstIndex(of: String(cursor.dropFirst("after:".count))) else {
                return PostPage(posts: [], accounts: [], entryIDs: [], nextCursor: nil)
            }
            start = index + 1
        }
        let ids = Array(list[start..<min(start + pageSize, list.count)])
        let next = start + pageSize < list.count ? ids.last.map { "after:\($0)" } : nil
        return PostPage(posts: ids.map(Self.post), accounts: [Self.author], entryIDs: ids, nextCursor: next)
    }

    /// Lets the requests that wait go on.
    func release() {
        isHeld = false
        let continuations = waiting
        waiting = []
        for continuation in continuations { continuation.resume() }
    }

    static let author = Account(
        id: "author",
        displayName: [ContentRun(content: .text("Author"))],
        handle: "@author",
        avatar: nil
    )

    static func post(_ id: String) -> Post {
        Post(
            id: id,
            accountID: author.id,
            createdAt: Date(timeIntervalSince1970: 1_800_000_000),
            content: [ContentBlock(kind: .paragraph, runs: [ContentRun(content: .text(id))])]
        )
    }
}

/// `count` post IDs, newest first: `postIDs(3)` is p3, p2, p1.
func postIDs(_ count: Int) -> [String] {
    (0..<count).map { "p\(count - $0)" }
}

/// Waits until `condition` holds, or gives up after `seconds`; says whether it held.
@MainActor func eventually(within seconds: Double = 5, _ condition: () async throws -> Bool) async rethrows -> Bool {
    let deadline = Date().addingTimeInterval(seconds)
    while try await !condition() {
        if Date() > deadline { return false }
        try? await Task.sleep(for: .milliseconds(10))
    }
    return true
}

/// A suite of user defaults of its own, removed when the test ends.
final class DefaultsSuite {
    let name = "dev.mycroblog.tests.\(UUID().uuidString)"

    deinit { UserDefaults.standard.removePersistentDomain(forName: name) }
}
