import SwiftUI

/// The root of a scene: every top-level destination in the OS tab container, which shows a tab bar in compact width
/// and a sidebar in regular width (ADR-0009 §2).
public struct RootView: View {
    @State private var selection: TopLevelDestination = .home

    public init() {}

    public var body: some View {
        TabView(selection: $selection) {
            // Search is a plain tab: the search role would pin it before the system's More tab and push Notifications,
            // an item with a badge, out of the tab bar (ADR-0009 §5).
            ForEach(TopLevelDestination.allCases, id: \.self) { destination in
                Tab(destination.title, systemImage: destination.systemImage, value: destination) {
                    DestinationPlaceholder(destination: destination)
                }
            }
        }
        .tabViewStyle(.sidebarAdaptable)
        .sidebarOnTheInnerDisplay()
    }
}

/// The empty screen of a destination until its feature lands.
private struct DestinationPlaceholder: View {
    let destination: TopLevelDestination

    var body: some View {
        NavigationStack {
            ContentUnavailableView(destination.title, systemImage: destination.systemImage)
                .navigationTitle(destination.title)
        }
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
