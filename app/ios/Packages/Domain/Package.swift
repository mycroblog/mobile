// swift-tools-version: 6.2
// The domain types. The app extensions use them (ADR-0007 §1), so the default isolation is nonisolated and values
// cross actors as Sendable (ADR-0009 §7). It depends on no other package.
import PackageDescription

let package = Package(
    name: "Domain",
    platforms: [.iOS(.v26)],
    products: [.library(name: "Domain", targets: ["Domain"])],
    targets: [
        .target(
            name: "Domain",
            swiftSettings: [
                .defaultIsolation(nil),
                .enableUpcomingFeature("NonisolatedNonsendingByDefault"),
                .enableUpcomingFeature("InferIsolatedConformances"),
            ]
        ),
    ]
)
