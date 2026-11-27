import SwiftUI

/// The root of a scene: the top-level destinations in the OS tab container, which shows a tab bar in compact width
/// and a sidebar in regular width (ADR-0009 §2).
///
/// In regular width every destination is a tab of the sidebar. In compact width the first four are tabs and the rest
/// open from a list in a fifth tab, so that the system's More tab never appears: a NavigationStack inside it shows a
/// second navigation bar under the system's own.
public struct RootView: View {
    @SceneStorage("RootView.selection") private var selection: RootSelection = .destination(.home)
    @Environment(\.horizontalSizeClass) private var horizontalSizeClass

    public init() {}

    private var isCompact: Bool { horizontalSizeClass == .compact }

    public var body: some View {
        TabView(selection: tabSelection) {
            ForEach(isCompact ? RootSelection.compactTabBarDestinations : TopLevelDestination.allCases, id: \.self) { destination in
                Tab(value: RootSelection.destination(destination)) {
                    NavigationStack {
                        DestinationPlaceholder(destination: destination)
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
                                DestinationPlaceholder(destination: destination)
                            }
                    }
                } label: {
                    TabLabel(title: "More", systemImage: "ellipsis")
                }
            }
        }
        .tabViewStyle(.sidebarAdaptable)
        .sidebarOnTheInnerDisplay()
    }

    /// The selected tab: in compact width an overflow destination is shown in the overflow list's tab; in regular
    /// width the overflow list has no tab, so Home stands for it.
    private var tabSelection: Binding<RootSelection> {
        Binding {
            if isCompact {
                selection.overflowDestination == nil ? selection : .overflowList
            } else {
                selection == .overflowList ? .destination(.home) : selection
            }
        } set: { newValue in
            // Selecting the overflow list's tab again keeps the destination open in it.
            if newValue == .overflowList, selection.overflowDestination != nil { return }
            selection = newValue
        }
    }

    /// The stack of the overflow list's tab: the overflow destination that is open, if any.
    private var overflowPath: Binding<[TopLevelDestination]> {
        Binding {
            selection.overflowDestination.map { [$0] } ?? []
        } set: { path in
            selection = path.last.map(RootSelection.destination) ?? .overflowList
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

private extension View {
    /// The iPhone Duo's inner display defaults to a vertical tab bar; ADR-0009 §2 asks for the sidebar there.
    @ViewBuilder
    func sidebarOnTheInnerDisplay() -> some View {
        if #available(iOS 27.0, *) {
            defaultTabBarPlacement(.sidebar)
        } else {
            self
        }
    }
}
