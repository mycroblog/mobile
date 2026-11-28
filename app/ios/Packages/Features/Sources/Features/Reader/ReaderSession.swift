import AppData
import Domain
import Foundation
import Observation

/// The reader of the app who has not signed in: the server they chose, the public timelines it offers, and the models
/// of those timelines. The app owns one and passes it to every scene (ADR-0009 §6).
@Observable
public final class ReaderSession {
    /// The chosen server, or nil before one is chosen.
    public private(set) var server: ChosenServer?
    /// The timelines that answered 404 for the chosen server; their destinations are hidden.
    public private(set) var unavailableTimelines: Set<PublicTimeline> = []

    @ObservationIgnored public let instanceLookup: any InstanceLookup
    @ObservationIgnored private let sync: TimelineSync?
    @ObservationIgnored private let serverStore: ChosenServerStore
    @ObservationIgnored private var timelineModels: [TimelineKey: TimelineModel] = [:]

    private struct TimelineKey: Hashable {
        let context: ReaderContext
        let timeline: PublicTimeline
    }

    /// A session that remembers its server in `serverStore`, keeps its timelines in step with `sync` (none, and no
    /// timeline is read, when the store could not be opened), and looks up servers with `instanceLookup`. It restores
    /// the server chosen last.
    public init(instanceLookup: any InstanceLookup, sync: TimelineSync?, serverStore: ChosenServerStore) {
        self.instanceLookup = instanceLookup
        self.sync = sync
        self.serverStore = serverStore
        server = serverStore.load()
    }

    /// The public timelines whose destinations are shown, in the order of ADR-0002 §6.
    public var publicTimelines: [PublicTimeline] {
        (server?.publicTimelines ?? []).filter { !unavailableTimelines.contains($0) }
    }

    /// Reads `address` from now on, whose instance is `instance`.
    public func choose(_ address: ServerAddress, instance: Instance) {
        let chosen = ChosenServer(
            context: ReaderContext(server: address), publicTimelines: instance.publicTimelinesForReaders)
        if chosen.context != server?.context { unavailableTimelines = [] }
        server = chosen
        serverStore.save(chosen)
    }

    /// Asks the chosen server again what it offers, and keeps what it says. A failure keeps what was known.
    public func refreshInstance() async {
        guard let server, let address = ServerAddress(parsing: server.context.origin.absoluteString),
            let instance = try? await instanceLookup.instance(at: address),
            self.server?.context == server.context
        else { return }
        let updated = ChosenServer(context: server.context, publicTimelines: instance.publicTimelinesForReaders)
        guard updated != self.server else { return }
        self.server = updated
        serverStore.save(updated)
    }

    /// Cuts the stored timelines to their limit (ADR-0008 §3); the app calls it at launch.
    public func prune() async {
        try? await sync?.prune()
    }

    /// The model of `timeline` of the chosen server, kept while the session lives so that rebuilding a destination
    /// keeps its state; nil when no server is chosen or no store could be opened.
    public func model(of timeline: PublicTimeline) -> TimelineModel? {
        guard let context = server?.context, let sync else { return nil }
        let key = TimelineKey(context: context, timeline: timeline)
        if let model = timelineModels[key] { return model }
        let model = TimelineModel(timeline: timeline, context: context, sync: sync) { [weak self] in
            self?.timelineIsUnavailable(timeline, in: context)
        }
        timelineModels[key] = model
        return model
    }

    private func timelineIsUnavailable(_ timeline: PublicTimeline, in context: ReaderContext) {
        guard server?.context == context else { return }
        unavailableTimelines.insert(timeline)
    }
}
