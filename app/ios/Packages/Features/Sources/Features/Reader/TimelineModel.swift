import AppData
import Domain
import Foundation
import Observation

/// The state of the screen of a public timeline: what the store holds of it (ADR-0008 §1), and whether it is being
/// read from the server or failed to be.
@Observable
public final class TimelineModel {
    /// What the screen shows, apart from the posts.
    public enum Status: Hashable, Sendable {
        /// Nothing is being read.
        case idle
        /// The newest posts are being read.
        case refreshing
        /// An older page is being read.
        case loadingOlder
        /// Reading failed for `error`; trying again is allowed from `retryAt` on, or at once when it is nil.
        case failed(TimelineLoadError, retryAt: Date?)
    }

    public let timeline: PublicTimeline
    public let context: ReaderContext
    /// The posts, newest first, as the store holds them.
    public private(set) var items: [TimelineItem] = []
    /// Whether an older page may follow the last post.
    public private(set) var hasOlder = false
    /// Whether the store has ever held the timeline.
    public private(set) var wasLoaded = false
    public private(set) var status: Status = .idle
    /// Where the reader was when the screen was last left, read once from the store.
    public private(set) var savedPosition: ReadingPosition?
    /// Whether the store has been read, so that the saved position can be restored.
    public private(set) var hasReadStore = false
    /// The posts whose content warning the reader opened.
    public var openedContentWarnings: Set<String> = []
    /// The posts whose hidden media the reader showed.
    public var shownMedia: Set<String> = []

    @ObservationIgnored private let sync: TimelineSync
    @ObservationIgnored private let onUnavailable: () -> Void
    @ObservationIgnored private var didRefreshOnOpen = false
    @ObservationIgnored private var isWorking = false

    /// The model of `timeline` of `context`, kept in step by `sync`; `onUnavailable` is called when the server
    /// answers that it does not offer the timeline.
    public init(
        timeline: PublicTimeline,
        context: ReaderContext,
        sync: TimelineSync,
        onUnavailable: @escaping () -> Void = {}
    ) {
        self.timeline = timeline
        self.context = context
        self.sync = sync
        self.onUnavailable = onUnavailable
    }

    /// Shows the store's timeline and follows its changes until the task is cancelled; the first time, it also
    /// refreshes the timeline from the server (ADR-0008 §3). The screen runs it while it is shown.
    public func run() async {
        if !hasReadStore {
            savedPosition = try? await sync.store.readingPosition(of: timeline, in: context)
        }
        await withTaskGroup(of: Void.self) { group in
            group.addTask { await self.observeStore() }
            if !didRefreshOnOpen {
                didRefreshOnOpen = true
                group.addTask { await self.refresh() }
            }
        }
    }

    private func observeStore() async {
        do {
            for try await snapshot in sync.store.observe(timeline, in: context) {
                items = snapshot.items
                hasOlder = snapshot.hasOlder
                wasLoaded = snapshot.wasLoaded
                hasReadStore = true
            }
        } catch {
            hasReadStore = true
        }
    }

    /// Whether the newest posts can be read now: nothing is being read, and the rate limit allows it.
    public var canRefresh: Bool {
        if isWorking { return false }
        if case .failed(_, let retryAt?) = status, retryAt > .now { return false }
        return true
    }

    /// Reads the newest posts; used when the timeline is opened, pulled to refresh, and tried again.
    public func refresh() async {
        guard canRefresh else { return }
        await perform(.refreshing) { [sync, timeline, context] in
            try await sync.refresh(timeline, in: context)
        }
    }

    /// Reads the page older than the last post; used when the reader scrolls to the end.
    public func loadOlder() async {
        guard hasOlder, canRefresh else { return }
        if case .failed = status { return }
        await perform(.loadingOlder) { [sync, timeline, context] in
            try await sync.loadOlder(timeline, in: context)
        }
    }

    /// Tries again what failed, once the rate limit allows it.
    public func retry() async {
        guard case .failed = status, canRefresh else { return }
        let older = wasLoaded && !items.isEmpty && hasOlder && isOlderFailure
        status = .idle
        if older { await loadOlder() } else { await refresh() }
    }

    @ObservationIgnored private var isOlderFailure = false

    private func perform(_ working: Status, _ operation: @escaping @Sendable () async throws -> Void) async {
        isWorking = true
        status = working
        defer { isWorking = false }
        do {
            try await operation()
            status = .idle
        } catch is CancellationError {
            status = .idle
        } catch let error as TimelineLoadError {
            isOlderFailure = working == .loadingOlder
            status = .failed(error, retryAt: Self.retryDate(for: error))
            if error == .notFound { onUnavailable() }
        } catch {
            isOlderFailure = working == .loadingOlder
            status = .failed(.invalidAnswer, retryAt: nil)
        }
    }

    private static func retryDate(for error: TimelineLoadError) -> Date? {
        guard case .rateLimited(let seconds?) = error, seconds > 0 else { return nil }
        return Date.now.addingTimeInterval(TimeInterval(seconds))
    }

    /// Saves `position` as where the reader is, or forgets it with nil.
    public func save(position: ReadingPosition?) {
        guard position != savedPosition else { return }
        savedPosition = position
        Task { [sync, timeline, context] in
            try? await sync.saveReadingPosition(position, of: timeline, in: context)
        }
    }
}
