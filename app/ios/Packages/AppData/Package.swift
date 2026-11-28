// swift-tools-version: 6.2
// The data of the app: the store and the sync (ADR-0008). Only the app uses it, never an extension (ADR-0008 §5,
// ADR-0009 §7). It is not UI, so the default isolation is nonisolated. The store is SQLite through GRDB (ADR-0008 §7).
import PackageDescription

let isolation: [SwiftSetting] = [
    .defaultIsolation(nil),
    .enableUpcomingFeature("NonisolatedNonsendingByDefault"),
    .enableUpcomingFeature("InferIsolatedConformances"),
]

let package = Package(
    name: "AppData",
    platforms: [.iOS(.v26)],
    products: [.library(name: "AppData", targets: ["AppData"])],
    dependencies: [
        .package(path: "../Domain"),
        .package(path: "../API"),
        .package(url: "https://github.com/groue/GRDB.swift", exact: "7.12.0"),
    ],
    targets: [
        .target(
            name: "AppData",
            dependencies: ["Domain", "API", .product(name: "GRDB", package: "GRDB.swift")],
            swiftSettings: isolation
        ),
        // The unit tests (Swift Testing): the store and the sync against a stub source of pages.
        .testTarget(
            name: "AppDataTests",
            dependencies: ["AppData", "Domain"],
            swiftSettings: isolation
        ),
        // The integration tests (XCTest) against a local my, whose origin `task ios:test` passes in MY_URL.
        .testTarget(
            name: "AppDataIntegrationTests",
            dependencies: ["AppData", "Domain", "API"],
            swiftSettings: isolation
        ),
    ]
)
