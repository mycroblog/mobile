import Testing

@testable import Features

/// The overflow list's tab keeps the destination last opened from it while another tab is selected; going back in it
/// returns to the list.
@MainActor
struct RootNavigationTests {
    @Test func theOverflowTabKeepsItsDestinationAcrossTabSwitches() {
        var navigation = RootNavigation()
        navigation.select(tab: .overflowList)
        navigation.setOverflowPath([.profile])

        navigation.select(tab: .destination(.home))
        #expect(navigation.tab(isCompact: true) == .destination(.home))
        #expect(navigation.overflowPath == [.profile])

        navigation.select(tab: .overflowList)
        #expect(navigation.tab(isCompact: true) == .overflowList)
        #expect(navigation.selection == .destination(.profile))
        #expect(navigation.overflowPath == [.profile])
    }

    @Test func goingBackInTheOverflowTabReturnsToTheList() {
        var navigation = RootNavigation()
        navigation.select(tab: .overflowList)
        navigation.setOverflowPath([.settings])

        navigation.setOverflowPath([])
        navigation.select(tab: .destination(.notifications))
        navigation.select(tab: .overflowList)

        #expect(navigation.selection == .overflowList)
        #expect(navigation.overflowPath.isEmpty)
    }

    @Test func anOverflowDestinationSelectedInTheSidebarOpensInTheOverflowTab() {
        var navigation = RootNavigation()
        navigation.select(tab: .destination(.bookmarks))

        #expect(navigation.tab(isCompact: false) == .destination(.bookmarks))
        #expect(navigation.tab(isCompact: true) == .overflowList)
        #expect(navigation.overflowPath == [.bookmarks])
    }

    @Test func theSceneRestoresBothTheSelectionAndTheOverflowDestination() {
        var navigation = RootNavigation()
        navigation.select(tab: .overflowList)
        navigation.setOverflowPath([.profile])
        navigation.select(tab: .destination(.home))

        let restored = RootNavigation(rawValue: navigation.rawValue)

        #expect(restored == navigation)
        #expect(RootNavigation(rawValue: "home ") == RootNavigation())
        #expect(RootNavigation(rawValue: "nowhere ") == nil)
    }
}
