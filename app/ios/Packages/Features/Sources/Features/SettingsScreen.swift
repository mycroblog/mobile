import AppData
import SwiftUI

/// The Settings destination. "Add an account" opens the screen that looks up a server, in a sheet.
struct SettingsScreen: View {
    let instanceLookup: any InstanceLookup
    @State private var serverLookup: ServerLookupModel?

    var body: some View {
        List {
            Section {
                Button("Add an account") {
                    serverLookup = ServerLookupModel(lookup: instanceLookup)
                }
            }
        }
        .navigationTitle(TopLevelDestination.settings.title)
        .sheet(item: $serverLookup) { model in
            NavigationStack {
                ServerLookupScreen(model: model)
            }
        }
    }
}

extension ServerLookupModel: Identifiable {}
