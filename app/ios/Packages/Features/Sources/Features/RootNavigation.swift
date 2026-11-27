/// The navigation of a scene's root: the destination it shows, and the destination open in the overflow list's tab,
/// which stays open while another tab is selected. A scene saves it and restores it when the system relaunches the
/// app (ADR-0009 §3).
struct RootNavigation: Hashable, Sendable, RawRepresentable {
    /// What the root shows.
    private(set) var selection: RootSelection
    /// The overflow destination open in the overflow list's tab, if any.
    private(set) var overflowDestination: TopLevelDestination?

    init(selection: RootSelection = .destination(.home), overflowDestination: TopLevelDestination? = nil) {
        self.selection = selection
        self.overflowDestination = overflowDestination.flatMap { RootSelection.destination($0).overflowDestination }
        if let destination = selection.overflowDestination { self.overflowDestination = destination }
    }

    /// The selected tab. In compact width an overflow destination is shown in the overflow list's tab; in regular
    /// width the overflow list has no tab, so Home stands for it.
    func tab(isCompact: Bool) -> RootSelection {
        if isCompact {
            selection.overflowDestination == nil ? selection : .overflowList
        } else {
            selection == .overflowList ? .destination(.home) : selection
        }
    }

    /// Selects `tab`. Selecting the overflow list's tab shows the destination that was open in it.
    mutating func select(tab: RootSelection) {
        if tab == .overflowList {
            selection = overflowDestination.map(RootSelection.destination) ?? .overflowList
        } else {
            selection = tab
            if let destination = tab.overflowDestination { overflowDestination = destination }
        }
    }

    /// The stack of the overflow list's tab.
    var overflowPath: [TopLevelDestination] {
        overflowDestination.map { [$0] } ?? []
    }

    /// Sets the stack of the overflow list's tab, which the list's tab shows.
    mutating func setOverflowPath(_ path: [TopLevelDestination]) {
        overflowDestination = path.last.flatMap { RootSelection.destination($0).overflowDestination }
        selection = overflowDestination.map(RootSelection.destination) ?? .overflowList
    }

    init?(rawValue: String) {
        let parts = rawValue.split(separator: " ", omittingEmptySubsequences: false).map(String.init)
        guard (1...2).contains(parts.count), let selection = RootSelection(rawValue: parts[0]) else { return nil }
        let overflow = parts.count == 2 && !parts[1].isEmpty ? TopLevelDestination(rawValue: parts[1]) : nil
        self.init(selection: selection, overflowDestination: overflow)
    }

    var rawValue: String {
        "\(selection.rawValue) \(overflowDestination?.rawValue ?? "")"
    }
}
