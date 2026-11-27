/// What the root of a scene shows: a top-level destination, or, in compact width, the list of the destinations that
/// do not fit the tab bar. RootNavigation holds it, with the destination open in the overflow list's tab.
enum RootSelection: Hashable, Sendable, RawRepresentable {
    case destination(TopLevelDestination)
    case overflowList

    /// The destinations that have a tab of their own in compact width; the rest open from the overflow list
    /// (ADR-0009 §2: at most five tab bar items).
    static let compactTabBarDestinations: [TopLevelDestination] = [
        .home, .localTimeline, .federatedTimeline, .notifications,
    ]

    static var overflowDestinations: [TopLevelDestination] {
        TopLevelDestination.allCases.filter { !compactTabBarDestinations.contains($0) }
    }

    private static let overflowListRawValue = "overflowList"

    init?(rawValue: String) {
        if rawValue == Self.overflowListRawValue {
            self = .overflowList
        } else if let destination = TopLevelDestination(rawValue: rawValue) {
            self = .destination(destination)
        } else {
            return nil
        }
    }

    var rawValue: String {
        switch self {
        case .destination(let destination): destination.rawValue
        case .overflowList: Self.overflowListRawValue
        }
    }

    /// The destination this selection shows in the overflow list's stack, if it is one of the overflow destinations.
    var overflowDestination: TopLevelDestination? {
        guard case .destination(let destination) = self, Self.overflowDestinations.contains(destination) else {
            return nil
        }
        return destination
    }
}
