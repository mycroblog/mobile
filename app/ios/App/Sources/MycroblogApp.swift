import AppData
import Features
import SwiftUI

/// The app's one place of assembly (ADR-0009 §6).
@main
struct MycroblogApp: App {
    private let instanceLookup = RemoteInstanceLookup()

    var body: some Scene {
        WindowGroup {
            RootView(instanceLookup: instanceLookup)
        }
    }
}
