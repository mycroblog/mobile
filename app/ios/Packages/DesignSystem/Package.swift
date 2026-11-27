// swift-tools-version: 6.2
// The design system. It is UI, so the default isolation is MainActor (ADR-0009 §7). It depends on no other package.
import PackageDescription

let package = Package(
    name: "DesignSystem",
    platforms: [.iOS(.v26)],
    products: [.library(name: "DesignSystem", targets: ["DesignSystem"])],
    targets: [
        .target(
            name: "DesignSystem",
            swiftSettings: [
                .defaultIsolation(MainActor.self),
                .enableUpcomingFeature("NonisolatedNonsendingByDefault"),
                .enableUpcomingFeature("InferIsolatedConformances"),
            ]
        ),
    ]
)
