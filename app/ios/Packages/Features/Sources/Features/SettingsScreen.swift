import AppData
import SwiftUI

/// The Settings destination. The server section shows the server being read and changes it with the screen that looks
/// up a server, which the root of the scene presents, so that it survives the destinations being rebuilt when the
/// width changes.
struct SettingsScreen: View {
    let server: ChosenServer?
    let chooseServer: () -> Void

    var body: some View {
        List {
            Section {
                if let server {
                    LabeledContent("Reading", value: server.context.host)
                }
                Button(server == nil ? LocalizedStringKey("Choose a Server") : "Change Server", action: chooseServer)
            } header: {
                Text("Server")
            }
        }
        .navigationTitle(TopLevelDestination.settings.title)
    }
}
