import SwiftUI

/// The Settings destination. "Add an account" opens the screen that looks up a server; the root of the scene
/// presents it, so that it survives the destinations being rebuilt when the width changes.
struct SettingsScreen: View {
    let addAccount: () -> Void

    var body: some View {
        List {
            Section {
                Button("Add an account", action: addAccount)
            }
        }
        .navigationTitle(TopLevelDestination.settings.title)
    }
}
