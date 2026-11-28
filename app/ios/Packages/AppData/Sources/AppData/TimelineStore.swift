// The store of ADR-0008: one record per post and per account, keyed by (context, ID), and the timelines as ordered
// lists of post IDs per (context, timeline). The screens read it; only TimelineSync writes it (ADR-0008 §1).
import Domain
import Foundation
import GRDB

/// Where the reader was in a timeline: the entry at the top of the screen, and how far it was scrolled past its top.
public struct ReadingPosition: Sendable, Hashable, Codable {
    /// The ID of the timeline's entry at the top.
    public var anchorID: String
    /// How many points the top of the screen was below the top of the anchor.
    public var offset: Double

    public init(anchorID: String, offset: Double = 0) {
        self.anchorID = anchorID
        self.offset = offset
    }
}

/// What the store holds of one timeline.
public struct TimelineSnapshot: Sendable, Hashable {
    /// The items, newest first.
    public var items: [TimelineItem]
    /// Whether an older page may follow the last item.
    public var hasOlder: Bool
    /// Whether the timeline was ever loaded; an empty timeline that never was is still loading.
    public var wasLoaded: Bool

    public init(items: [TimelineItem], hasOlder: Bool, wasLoaded: Bool) {
        self.items = items
        self.hasOlder = hasOlder
        self.wasLoaded = wasLoaded
    }

    public static let empty = TimelineSnapshot(items: [], hasOlder: false, wasLoaded: false)
}

/// The SQLite store of the app (ADR-0008 §7), which only the app's own process opens (ADR-0008 §5).
public final class TimelineStore: Sendable {
    /// The most entries a timeline keeps after a launch (ADR-0008 §3).
    public static let maxEntriesPerTimeline = 1000

    let writer: any DatabaseWriter

    private init(writer: any DatabaseWriter) throws {
        self.writer = writer
        try Self.migrator.migrate(writer)
    }

    /// A store in a file at `url`.
    public static func onDisk(at url: URL) throws -> TimelineStore {
        try FileManager.default.createDirectory(
            at: url.deletingLastPathComponent(), withIntermediateDirectories: true)
        return try TimelineStore(writer: DatabasePool(path: url.path(percentEncoded: false)))
    }

    /// A store in memory, which goes away with it: for tests and previews.
    public static func inMemory() throws -> TimelineStore {
        try TimelineStore(writer: DatabaseQueue())
    }

    /// The app's store, in its Caches directory: everything in it can be read again from the servers (ADR-0008 §1),
    /// so the system may purge it and it is not backed up.
    public static func app() throws -> TimelineStore {
        let caches = try FileManager.default.url(
            for: .cachesDirectory, in: .userDomainMask, appropriateFor: nil, create: true)
        return try onDisk(at: caches.appending(path: "Store/store.sqlite"))
    }

    private static var migrator: DatabaseMigrator {
        var migrator = DatabaseMigrator()
        migrator.registerMigration("v1") { db in
            try db.execute(
                sql: """
                    CREATE TABLE account (
                        context TEXT NOT NULL, id TEXT NOT NULL, record BLOB NOT NULL,
                        PRIMARY KEY (context, id));
                    CREATE TABLE post (
                        context TEXT NOT NULL, id TEXT NOT NULL, accountID TEXT NOT NULL, boostOf TEXT,
                        record BLOB NOT NULL,
                        PRIMARY KEY (context, id));
                    CREATE TABLE timelineEntry (
                        context TEXT NOT NULL, timeline TEXT NOT NULL, position INTEGER NOT NULL, postID TEXT NOT NULL,
                        PRIMARY KEY (context, timeline, position));
                    CREATE TABLE timelineState (
                        context TEXT NOT NULL, timeline TEXT NOT NULL, olderCursor TEXT,
                        reachedEnd INTEGER NOT NULL DEFAULT 0,
                        PRIMARY KEY (context, timeline));
                    CREATE TABLE readingPosition (
                        context TEXT NOT NULL, timeline TEXT NOT NULL, anchorID TEXT NOT NULL, offset REAL NOT NULL,
                        PRIMARY KEY (context, timeline));
                    """)
        }
        return migrator
    }

    // MARK: Reading

    /// The timeline `timeline` of `context` now.
    public func snapshot(of timeline: PublicTimeline, in context: ReaderContext) async throws -> TimelineSnapshot {
        try await writer.read { db in try Self.snapshot(db, timeline: timeline, context: context) }
    }

    /// The timeline `timeline` of `context`, now and after every change.
    public func observe(
        _ timeline: PublicTimeline,
        in context: ReaderContext
    ) -> AsyncThrowingStream<TimelineSnapshot, any Error> {
        let writer = writer
        return AsyncThrowingStream { continuation in
            let task = Task {
                let observation = ValueObservation.tracking { db in
                    try Self.snapshot(db, timeline: timeline, context: context)
                }.removeDuplicates()
                let values = observation.values(in: writer)
                do {
                    for try await snapshot in values { continuation.yield(snapshot) }
                    continuation.finish()
                } catch {
                    continuation.finish(throwing: error)
                }
            }
            continuation.onTermination = { _ in task.cancel() }
        }
    }

    /// The IDs of the entries of `timeline`, newest first.
    public func entryIDs(of timeline: PublicTimeline, in context: ReaderContext) async throws -> [String] {
        try await writer.read { db in try Self.entryIDs(db, timeline: timeline, context: context) }
    }

    /// Where the reader was in `timeline`, or nil when nothing was saved.
    public func readingPosition(of timeline: PublicTimeline, in context: ReaderContext) async throws -> ReadingPosition?
    {
        try await writer.read { db in
            try Row.fetchOne(
                db,
                sql: "SELECT anchorID, offset FROM readingPosition WHERE context = ? AND timeline = ?",
                arguments: [context.key, timeline.rawValue]
            ).map { ReadingPosition(anchorID: $0["anchorID"], offset: $0["offset"]) }
        }
    }

    private static func entryIDs(_ db: Database, timeline: PublicTimeline, context: ReaderContext) throws -> [String] {
        try String.fetchAll(
            db,
            sql: "SELECT postID FROM timelineEntry WHERE context = ? AND timeline = ? ORDER BY position",
            arguments: [context.key, timeline.rawValue])
    }

    private static func snapshot(_ db: Database, timeline: PublicTimeline, context: ReaderContext) throws
        -> TimelineSnapshot
    {
        let ids = try entryIDs(db, timeline: timeline, context: context)
        let state = try Row.fetchOne(
            db,
            sql: "SELECT olderCursor, reachedEnd FROM timelineState WHERE context = ? AND timeline = ?",
            arguments: [context.key, timeline.rawValue])
        let decoder = JSONDecoder()
        var posts: [String: Post] = [:]
        for post in try fetchPosts(db, ids: ids, context: context, decoder: decoder) { posts[post.id] = post }
        let boosted = posts.values.compactMap(\.boostOf).filter { posts[$0] == nil }
        for post in try fetchPosts(db, ids: boosted, context: context, decoder: decoder) { posts[post.id] = post }
        let accountIDs = Set(posts.values.map(\.accountID))
        var accounts: [String: Account] = [:]
        for account in try fetchAccounts(db, ids: Array(accountIDs), context: context, decoder: decoder) {
            accounts[account.id] = account
        }
        let cursor: String? = state.flatMap { $0["olderCursor"] as String? }
        let reachedEnd: Bool = state.flatMap { $0["reachedEnd"] as Bool? } ?? false
        return TimelineSnapshot(
            items: ids.compactMap { TimelineItem(id: $0, posts: posts, accounts: accounts) },
            hasOlder: cursor != nil && !reachedEnd,
            wasLoaded: state != nil
        )
    }

    private static func fetchPosts(_ db: Database, ids: [String], context: ReaderContext, decoder: JSONDecoder) throws
        -> [Post]
    {
        guard !ids.isEmpty else { return [] }
        let rows = try Row.fetchAll(
            db,
            sql: "SELECT record FROM post WHERE context = ? AND id IN (\(placeholders(ids.count)))",
            arguments: StatementArguments([context.key] + ids))
        return try rows.map { try decoder.decode(Post.self, from: $0["record"] as Data) }
    }

    private static func fetchAccounts(_ db: Database, ids: [String], context: ReaderContext, decoder: JSONDecoder)
        throws -> [Account]
    {
        guard !ids.isEmpty else { return [] }
        let rows = try Row.fetchAll(
            db,
            sql: "SELECT record FROM account WHERE context = ? AND id IN (\(placeholders(ids.count)))",
            arguments: StatementArguments([context.key] + ids))
        return try rows.map { try decoder.decode(Account.self, from: $0["record"] as Data) }
    }

    private static func placeholders(_ count: Int) -> String {
        Array(repeating: "?", count: count).joined(separator: ",")
    }

    // MARK: Writing (TimelineSync only)

    /// The state of paging of `timeline`: the cursor of the page older than its last entry, and whether the server
    /// has none.
    struct PagingState: Sendable, Equatable {
        var olderCursor: String?
        var reachedEnd: Bool
    }

    func pagingState(of timeline: PublicTimeline, in context: ReaderContext) async throws -> PagingState? {
        try await writer.read { db in
            try Row.fetchOne(
                db,
                sql: "SELECT olderCursor, reachedEnd FROM timelineState WHERE context = ? AND timeline = ?",
                arguments: [context.key, timeline.rawValue]
            ).map { PagingState(olderCursor: $0["olderCursor"], reachedEnd: $0["reachedEnd"]) }
        }
    }

    /// Writes the records of `pages`, then sets the entries of `timeline` to `entryIDs` and its paging to `paging`.
    func write(
        pages: [PostPage],
        entryIDs: [String],
        paging: PagingState,
        of timeline: PublicTimeline,
        in context: ReaderContext
    ) async throws {
        let encoder = JSONEncoder()
        encoder.outputFormatting = .sortedKeys
        let posts = try pages.flatMap(\.posts).map { ($0, try encoder.encode($0)) }
        let accounts = try pages.flatMap(\.accounts).map { ($0, try encoder.encode($0)) }
        try await writer.write { db in
            for (post, record) in posts {
                try db.execute(
                    sql: "INSERT OR REPLACE INTO post (context, id, accountID, boostOf, record) VALUES (?, ?, ?, ?, ?)",
                    arguments: [context.key, post.id, post.accountID, post.boostOf, record])
            }
            for (account, record) in accounts {
                try db.execute(
                    sql: "INSERT OR REPLACE INTO account (context, id, record) VALUES (?, ?, ?)",
                    arguments: [context.key, account.id, record])
            }
            try db.execute(
                sql: "DELETE FROM timelineEntry WHERE context = ? AND timeline = ?",
                arguments: [context.key, timeline.rawValue])
            for (position, id) in entryIDs.enumerated() {
                try db.execute(
                    sql: "INSERT INTO timelineEntry (context, timeline, position, postID) VALUES (?, ?, ?, ?)",
                    arguments: [context.key, timeline.rawValue, position, id])
            }
            try db.execute(
                sql: """
                    INSERT OR REPLACE INTO timelineState (context, timeline, olderCursor, reachedEnd)
                    VALUES (?, ?, ?, ?)
                    """,
                arguments: [context.key, timeline.rawValue, paging.olderCursor, paging.reachedEnd])
        }
    }

    func saveReadingPosition(
        _ position: ReadingPosition?,
        of timeline: PublicTimeline,
        in context: ReaderContext
    ) async throws {
        try await writer.write { db in
            if let position {
                try db.execute(
                    sql: """
                        INSERT OR REPLACE INTO readingPosition (context, timeline, anchorID, offset)
                        VALUES (?, ?, ?, ?)
                        """,
                    arguments: [context.key, timeline.rawValue, position.anchorID, position.offset])
            } else {
                try db.execute(
                    sql: "DELETE FROM readingPosition WHERE context = ? AND timeline = ?",
                    arguments: [context.key, timeline.rawValue])
            }
        }
    }

    /// Keeps at most `maxEntries` entries of each timeline, the newest, and deletes the posts and accounts no
    /// timeline uses. A timeline cut short pages no further back until it is replaced (ADR-0008 §3).
    func prune(maxEntries: Int) async throws {
        try await writer.write { db in
            try db.execute(
                sql: """
                    UPDATE timelineState SET olderCursor = NULL, reachedEnd = 1
                    WHERE EXISTS (
                        SELECT 1 FROM timelineEntry e
                        WHERE e.context = timelineState.context AND e.timeline = timelineState.timeline
                        AND e.position >= ?)
                    """,
                arguments: [maxEntries])
            try db.execute(sql: "DELETE FROM timelineEntry WHERE position >= ?", arguments: [maxEntries])
            try db.execute(
                sql: """
                    DELETE FROM post WHERE NOT EXISTS (
                        SELECT 1 FROM timelineEntry e WHERE e.context = post.context AND e.postID = post.id)
                    AND NOT EXISTS (
                        SELECT 1 FROM timelineEntry e JOIN post b ON b.context = e.context AND b.id = e.postID
                        WHERE b.context = post.context AND b.boostOf = post.id)
                    """)
            try db.execute(
                sql: """
                    DELETE FROM account WHERE NOT EXISTS (
                        SELECT 1 FROM post p WHERE p.context = account.context AND p.accountID = account.id)
                    """)
        }
    }
}
