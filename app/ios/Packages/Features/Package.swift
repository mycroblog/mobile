// swift-tools-version: 6.2
// The screens of the app, from the root container down. It is UI, so the default isolation is MainActor
// (ADR-0009 §7). It reads the domain types, the data of the app and the design system; it never calls the API
// directly, which only the data of the app does.
import PackageDescription

let package = Package(
    name: "Features",
    platforms: [.iOS(.v26)],
    products: [.library(name: "Features", targets: ["Features"])],
    dependencies: [
        .package(path: "../Domain"),
        .package(path: "../AppData"),
        .package(path: "../DesignSystem"),
    ],
    targets: [
        .target(
            name: "Features",
            dependencies: ["Domain", "AppData", "DesignSystem"],
            swiftSettings: [
                .defaultIsolation(MainActor.self),
                .enableUpcomingFeature("NonisolatedNonsendingByDefault"),
                .enableUpcomingFeature("InferIsolatedConformances"),
            ]
        ),
    ]
)
