import SnapshotTesting
import SwiftUI
import Testing

@testable import Features

/// The placeholder of a destination, in its navigation stack, as it renders on an iPhone in portrait.
///
/// The reference images are in __Snapshots__/ next to this file; `task ios:test` names them, and on a failure the new
/// image and the difference. `task ios:record-screenshots` records them again. The tests run without a host app, where
/// Liquid Glass, such as the tab bar's, does not render; shoot screens below the tab container.
@MainActor
struct DestinationPlaceholderSnapshotTests {
    @Test func settingsOnAniPhone() {
        let screen = NavigationStack {
            DestinationPlaceholder(destination: .settings)
        }
        assertSnapshot(
            of: screen, as: .image(perceptualPrecision: 0.98, layout: .device(config: .iPhone13)), named: "iPhone")
    }
}
