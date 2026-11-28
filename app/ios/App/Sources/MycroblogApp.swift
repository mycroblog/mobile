import AppData
import Features
import SwiftUI

/// The app's one place of assembly (ADR-0009 §6).
@main
struct MycroblogApp: App {
    private let session: ReaderSession

    init() {
        // The store is a cache of what the servers hold (ADR-0008 §1); without it, no timeline is read.
        let sync = (try? TimelineStore.app()).map { TimelineSync(store: $0, source: RemoteTimelineSource()) }
        session = ReaderSession(instanceLookup: RemoteInstanceLookup(), sync: sync, serverStore: ChosenServerStore())
        Task { [session] in await session.prune() }
    }

    var body: some Scene {
        WindowGroup {
            RootView(session: session)
        }
    }
}
