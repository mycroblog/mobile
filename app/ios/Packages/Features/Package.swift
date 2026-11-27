// swift-tools-version: 6.2
// The screens of the app, from the root container down. It is UI, so the default isolation is MainActor
// (ADR-0009 §7). It reads the domain types, the data of the app and the design system; it never calls the API
// directly, which only the data of the app does. Its screenshot tests (ADR-0006 §1) are in Tests/ (`task ios:test`).
import PackageDescription

let isolation: [SwiftSetting] = [
    .defaultIsolation(MainActor.self),
    .enableUpcomingFeature("NonisolatedNonsendingByDefault"),
    .enableUpcomingFeature("InferIsolatedConformances"),
]

let package = Package(
    name: "Features",
    platforms: [.iOS(.v26)],
    products: [.library(name: "Features", targets: ["Features"])],
    dependencies: [
        .package(path: "../Domain"),
        .package(path: "../AppData"),
        .package(path: "../DesignSystem"),
        .package(url: "https://github.com/pointfreeco/swift-snapshot-testing", exact: "1.19.7"),
    ],
    targets: [
        .target(
            name: "Features",
            dependencies: ["Domain", "AppData", "DesignSystem"],
            swiftSettings: isolation
        ),
        .testTarget(
            name: "FeaturesTests",
            dependencies: [
                "Features",
                .product(name: "SnapshotTesting", package: "swift-snapshot-testing"),
            ],
            swiftSettings: isolation
        ),
    ]
)
