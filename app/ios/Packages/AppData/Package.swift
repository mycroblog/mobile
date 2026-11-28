// swift-tools-version: 6.2
// The data of the app: the store and the sync (ADR-0008). Only the app uses it, never an extension (ADR-0008 §5,
// ADR-0009 §7). It is not UI, so the default isolation is nonisolated. The store is SQLite through GRDB (ADR-0008 §7).
import PackageDescription

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
            swiftSettings: [
                .defaultIsolation(nil),
                .enableUpcomingFeature("NonisolatedNonsendingByDefault"),
                .enableUpcomingFeature("InferIsolatedConformances"),
            ]
        )
    ]
)
