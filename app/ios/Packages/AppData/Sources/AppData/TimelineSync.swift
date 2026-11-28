// The sync of the public timelines (ADR-0008 §3): it reads pages from the server and is the one writer of the store.
import API
import Domain
import Foundation

/// Reads pages of a server's public timelines. The sync uses it; a test passes a stub.
public protocol TimelineSource: Sendable {
    /// The newest page of `timeline` of `context`, or the one older than `cursor`.
    ///
    /// - Throws: A `TimelineLoadError`, or `CancellationError` when the task was cancelled.
    func page(of timeline: PublicTimeline, in context: ReaderContext, cursor: String?) async throws -> PostPage
}

/// Reads pages over the network with the client of the API.
public struct RemoteTimelineSource: TimelineSource {
    private let session: URLSession

    public init(session: URLSession = .shared) {
        self.session = session
    }

    public func page(of timeline: PublicTimeline, in context: ReaderContext, cursor: String?) async throws -> PostPage {
        try await APIClient(origin: context.origin, session: session).page(of: timeline, cursor: cursor)
    }
}

/// Keeps the store's timelines in step with the server, the one writer of the store (ADR-0008 §1).
public final class TimelineSync: Sendable {
    /// The most pages a refresh follows to meet the stored list before it replaces it (ADR-0008 §3).
    public static let maxPagesToMeet = 5

    public let store: TimelineStore
    private let source: any TimelineSource

    public init(store: TimelineStore, source: any TimelineSource) {
        self.store = store
        self.source = source
    }

    /// Reads the newest page of `timeline`, and the older ones until they meet the stored list, up to
    /// `maxPagesToMeet` pages. When they meet, the new entries go above the stored ones; when they do not, the
    /// stored list is replaced by what was read. With nothing stored there is nothing to meet, so only the newest page
    /// is read; older ones follow as the reader scrolls. Used when a timeline is opened and when it is pulled to
    /// refresh.
    @concurrent public func refresh(_ timeline: PublicTimeline, in context: ReaderContext) async throws {
        let stored = try await store.entryIDs(of: timeline, in: context)
        let maxPages = stored.isEmpty ? 1 : Self.maxPagesToMeet
        let storedPositions = Dictionary(stored.enumerated().map { ($1, $0) }, uniquingKeysWith: { first, _ in first })
        var pages: [PostPage] = []
        var newer: [String] = []
        var seen = Set<String>()
        var metAt: Int?
        var cursor: String?
        var reachedEnd = false
        reading: for _ in 0..<maxPages {
            let page = try await source.page(of: timeline, in: context, cursor: cursor)
            pages.append(page)
            for id in page.entryIDs {
                if let position = storedPositions[id] {
                    metAt = position
                    break reading
                }
                if seen.insert(id).inserted { newer.append(id) }
            }
            cursor = page.nextCursor
            if cursor == nil {
                reachedEnd = true
                break
            }
        }
        if let metAt {
            let paging = try await store.pagingState(of: timeline, in: context)
            try await store.write(
                pages: pages,
                entryIDs: newer + stored[metAt...],
                paging: paging ?? .init(olderCursor: nil, reachedEnd: true),
                of: timeline,
                in: context)
        } else {
            try await store.write(
                pages: pages,
                entryIDs: newer,
                paging: .init(olderCursor: cursor, reachedEnd: reachedEnd),
                of: timeline,
                in: context)
        }
    }

    /// Appends the page older than the last entry of `timeline`, if there is one.
    @concurrent public func loadOlder(_ timeline: PublicTimeline, in context: ReaderContext) async throws {
        guard let paging = try await store.pagingState(of: timeline, in: context), !paging.reachedEnd,
            let cursor = paging.olderCursor
        else { return }
        let page = try await source.page(of: timeline, in: context, cursor: cursor)
        let stored = try await store.entryIDs(of: timeline, in: context)
        var seen = Set(stored)
        let older = page.entryIDs.filter { seen.insert($0).inserted }
        try await store.write(
            pages: [page],
            entryIDs: stored + older,
            paging: .init(olderCursor: page.nextCursor, reachedEnd: page.nextCursor == nil),
            of: timeline,
            in: context)
    }

    /// Saves what the reader opened of the post `postID`: its content warning and its hidden media.
    public func saveDisplayState(_ state: PostDisplayState, of postID: String, in context: ReaderContext) async throws {
        try await store.saveDisplayState(state, of: postID, in: context)
    }

    /// Saves where the reader is in `timeline`, or forgets it with nil.
    public func saveReadingPosition(
        _ position: ReadingPosition?,
        of timeline: PublicTimeline,
        in context: ReaderContext
    ) async throws {
        try await store.saveReadingPosition(position, of: timeline, in: context)
    }

    /// Cuts each timeline to its newest `maxEntries` and deletes what no timeline uses. The app calls it at launch.
    public func prune(maxEntries: Int = TimelineStore.maxEntriesPerTimeline) async throws {
        try await store.prune(maxEntries: maxEntries)
    }
}
