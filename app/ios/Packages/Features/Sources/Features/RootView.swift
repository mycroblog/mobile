import AppData
import Domain
import Foundation
import SwiftUI

/// The root of a scene: the top-level destinations in the OS tab container, which shows a tab bar in compact width
/// and a sidebar in regular width (ADR-0009 §2).
///
/// In regular width every destination is a tab of the sidebar. In compact width the first four are tabs and the rest
/// open from a list in a fifth tab, so that the system's More tab never appears: a NavigationStack inside it shows a
/// second navigation bar under the system's own.
public struct RootView: View {
    @SceneStorage("RootView.navigation") private var navigation = RootNavigation()
    @Environment(\.horizontalSizeClass) private var horizontalSizeClass
    // The server screen belongs to the scene, above the tab container, so that rebuilding the destinations when the
    // width changes does not close it; whether it is open and the address typed in it are restored with the scene
    // (ADR-0009 §3). A result is not restored: it is looked up again with "Look up".
    @State private var serverLookup: ServerLookupModel?
    @SceneStorage("RootView.isLookingUpAServer") private var isLookingUpAServer = false
    @SceneStorage("RootView.serverAddress") private var serverAddress = ""
    /// When the set of tabs last changed: at the scene's start and when the width changes. Rebuilding the tabs makes the
    /// tab container select its first tab, which is not the reader's choice.
    @State private var tabsChangedAt = Date.now

    private let session: ReaderSession

    /// The root of a scene, which reads the server of `session` and looks up servers with its lookup.
    public init(session: ReaderSession) {
        self.session = session
    }

    private var instanceLookup: any InstanceLookup { session.instanceLookup }

    private var isCompact: Bool { horizontalSizeClass == .compact }

    /// The destinations shown: all but the server-provided timelines the chosen server does not offer (ADR-0002 §6).
    private var availableDestinations: [TopLevelDestination] {
        TopLevelDestination.allCases.filter { destination in
            destination.publicTimeline.map { session.publicTimelines.contains($0) } ?? true
        }
    }

    private var tabDestinations: [TopLevelDestination] {
        let available = availableDestinations
        return isCompact ? RootSelection.compactTabBarDestinations.filter { available.contains($0) } : available
    }

    public var body: some View {
        TabView(selection: tabSelection) {
            ForEach(tabDestinations, id: \.self) { destination in
                Tab(value: RootSelection.destination(destination)) {
                    NavigationStack {
                        DestinationScreen(
                            destination: destination, session: session, chooseServer: lookUpAServer, open: show)
                    }
                } label: {
                    TabLabel(title: destination.title, systemImage: destination.systemImage)
                }
            }
            if isCompact {
                Tab(value: RootSelection.overflowList) {
                    NavigationStack(path: overflowPath) {
                        OverflowList()
                            .navigationDestination(for: TopLevelDestination.self) { destination in
                                DestinationScreen(
                                    destination: destination, session: session, chooseServer: lookUpAServer,
                                    open: show)
                            }
                    }
                } label: {
                    TabLabel(title: "More", systemImage: "ellipsis")
                }
            }
        }
        .tabViewStyle(.sidebarAdaptable)
        .sidebarOnTheInnerDisplay()
        .opensLinksInAppBrowser()
        .sheet(item: $serverLookup) { model in
            NavigationStack {
                ServerLookupScreen(model: model) { address, instance in
                    session.choose(address, instance: instance)
                    serverLookup = nil
                }
            }
        }
        .task { await session.refreshInstance() }
        .onChange(of: serverLookup?.address) { _, address in
            serverAddress = address ?? ""
        }
        .onChange(of: serverLookup == nil) { _, isClosed in
            isLookingUpAServer = !isClosed
        }
        .onAppear(perform: restoreServerLookup)
        .onChange(of: tabDestinations) { tabsChangedAt = .now }
    }

    /// Shows the top-level destination `destination`.
    private func show(_ destination: TopLevelDestination) {
        navigation.select(tab: .destination(destination))
    }

    private func lookUpAServer() {
        serverLookup = ServerLookupModel(lookup: instanceLookup)
    }

    /// Opens the server screen again, with the address that was typed, when the scene had it open.
    private func restoreServerLookup() {
        guard isLookingUpAServer, serverLookup == nil else { return }
        serverLookup = ServerLookupModel(lookup: instanceLookup, address: serverAddress)
    }

    /// The selected tab, which RootNavigation maps to and from what the root shows.
    private var tabSelection: Binding<RootSelection> {
        Binding {
            let tab = navigation.tab(isCompact: isCompact)
            // A destination the server stopped offering shows Home instead.
            if case .destination(let destination) = tab, !availableDestinations.contains(destination) {
                return .destination(.home)
            }
            return tab
        } set: { tab in
            // Keep the restored or selected destination when the container falls back to Home as it rebuilds its tabs.
            let isRebuilding = Date.now.timeIntervalSince(tabsChangedAt) < 1
            if isRebuilding, tab == .destination(.home), navigation.tab(isCompact: isCompact) != tab,
                case .destination(let selected) = navigation.tab(isCompact: isCompact),
                availableDestinations.contains(selected)
            {
                return
            }
            navigation.select(tab: tab)
        }
    }

    /// The stack of the overflow list's tab: the overflow destination open in it, if any.
    private var overflowPath: Binding<[TopLevelDestination]> {
        Binding {
            navigation.overflowPath
        } set: { path in
            navigation.setOverflowPath(path)
        }
    }
}

/// The label of a tab. Its symbol's accessible name is the tab's title, not the symbol's name.
private struct TabLabel: View {
    let title: LocalizedStringKey
    let systemImage: String

    var body: some View {
        Label {
            Text(title)
        } icon: {
            Image(systemName: systemImage)
                .accessibilityLabel(Text(title))
        }
    }
}

/// The destinations that do not fit the compact tab bar, in the order of ADR-0002 §6.
private struct OverflowList: View {
    var body: some View {
        List(RootSelection.overflowDestinations, id: \.self) { destination in
            NavigationLink(value: destination) {
                Label(destination.title, systemImage: destination.systemImage)
            }
        }
        .navigationTitle("More")
    }
}

/// The screen of a top-level destination: its feature, or its placeholder until the feature lands.
private struct DestinationScreen: View {
    let destination: TopLevelDestination
    let session: ReaderSession
    let chooseServer: () -> Void
    let open: (TopLevelDestination) -> Void

    var body: some View {
        switch destination {
        case .home:
            HomeScreen(
                server: session.server,
                firstTimeline: session.publicTimelines.first,
                chooseServer: chooseServer,
                open: open
            )
        case .localTimeline, .federatedTimeline:
            if let timeline = destination.publicTimeline, let model = session.model(of: timeline) {
                TimelineScreen(model: model, title: destination.title)
                    .id(model.context)
            } else {
                DestinationPlaceholder(destination: destination)
            }
        case .settings:
            SettingsScreen(server: session.server, chooseServer: chooseServer)
        default:
            DestinationPlaceholder(destination: destination)
        }
    }
}

/// Home. Before a server is chosen, it offers to choose one; its timeline needs signing in.
private struct HomeScreen: View {
    let server: ChosenServer?
    /// The first public timeline the server offers, which Home offers to open until the reader can sign in.
    let firstTimeline: PublicTimeline?
    let chooseServer: () -> Void
    let open: (TopLevelDestination) -> Void

    var body: some View {
        if server == nil {
            ContentUnavailableView {
                Label {
                    Text(TopLevelDestination.home.title)
                } icon: {
                    Image(systemName: TopLevelDestination.home.systemImage)
                        .accessibilityHidden(true)
                }
            } description: {
                Text("Choose a server to read its public timelines.")
            } actions: {
                Button("Choose a Server", action: chooseServer)
                    .buttonStyle(.borderedProminent)
            }
            .navigationTitle(TopLevelDestination.home.title)
        } else {
            ContentUnavailableView {
                Label {
                    Text(TopLevelDestination.home.title)
                } icon: {
                    Image(systemName: TopLevelDestination.home.systemImage)
                        .accessibilityHidden(true)
                }
            } description: {
                Text("Once you sign in, Home shows the posts of the accounts you follow. Signing in comes later.")
            } actions: {
                if let destination = firstTimeline.map(TopLevelDestination.init(publicTimeline:)) {
                    Button {
                        open(destination)
                    } label: {
                        Text("Open \(Text(destination.title))")
                    }
                    .buttonStyle(.borderedProminent)
                }
            }
            .navigationTitle(TopLevelDestination.home.title)
        }
    }
}

/// The empty screen of a destination until its feature lands. Its symbol is decorative, so VoiceOver skips it.
struct DestinationPlaceholder: View {
    let destination: TopLevelDestination

    var body: some View {
        ContentUnavailableView {
            Label {
                Text(destination.title)
            } icon: {
                Image(systemName: destination.systemImage)
                    .accessibilityHidden(true)
            }
        }
        .navigationTitle(destination.title)
    }
}

extension View {
    /// The iPhone Duo's inner display defaults to a vertical tab bar; ADR-0009 §2 asks for the sidebar there.
    @ViewBuilder
    fileprivate func sidebarOnTheInnerDisplay() -> some View {
        if #available(iOS 27.0, *) {
            defaultTabBarPlacement(.sidebar)
        } else {
            self
        }
    }
}
