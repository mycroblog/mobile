// The host app of the DriveRunner UI tests. XCUITest needs a target app to start its runner; the runner drives other
// apps by bundle id, so this one shows an empty screen.
import SwiftUI

@main
struct DriveHostApp: App {
    var body: some Scene {
        WindowGroup {
            Color.clear
        }
    }
}
