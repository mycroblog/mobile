// swift-tools-version: 6.2
// The domain types. The app extensions use them (ADR-0007 §1), so the default isolation is nonisolated and values
// cross actors as Sendable (ADR-0009 §7). It depends on no other package.
import PackageDescription

let isolation: [SwiftSetting] = [
    .defaultIsolation(nil),
    .enableUpcomingFeature("NonisolatedNonsendingByDefault"),
    .enableUpcomingFeature("InferIsolatedConformances"),
]

let package = Package(
    name: "Domain",
    platforms: [.iOS(.v26)],
    products: [.library(name: "Domain", targets: ["Domain"])],
    targets: [
        .target(
            name: "Domain",
            swiftSettings: isolation
        ),
        // The unit tests (Swift Testing), against the shared fixtures where both apps must agree (ADR-0002 §2).
        .testTarget(
            name: "DomainTests",
            dependencies: ["Domain"],
            swiftSettings: isolation
        ),
    ]
)
