import SwiftUI

/// A top-level destination of the app, in the order of ADR-0002 §6.
///
/// The server-provided timelines are the local and the federated one; until there is a server to ask, both are shown.
public enum TopLevelDestination: String, CaseIterable, Hashable, Sendable {
    case home
    case localTimeline
    case federatedTimeline
    case notifications
    case search
    case directMessages
    case profile
    case bookmarks
    case settings

    var title: LocalizedStringKey {
        switch self {
        case .home: "Home"
        case .localTimeline: "Local"
        case .federatedTimeline: "Federated"
        case .notifications: "Notifications"
        case .search: "Search"
        case .directMessages: "Messages"
        case .profile: "Profile"
        case .bookmarks: "Bookmarks"
        case .settings: "Settings"
        }
    }

    var systemImage: String {
        switch self {
        case .home: "house"
        case .localTimeline: "person.2"
        case .federatedTimeline: "globe"
        case .notifications: "bell"
        case .search: "magnifyingglass"
        case .directMessages: "envelope"
        case .profile: "person.crop.circle"
        case .bookmarks: "bookmark"
        case .settings: "gearshape"
        }
    }
}
