// swift-tools-version: 6.2
// The data of the app: the store and the sync (ADR-0008). Only the app uses it, never an extension (ADR-0008 §5,
// ADR-0009 §7). It is not UI, so the default isolation is nonisolated.
import PackageDescription

let package = Package(
    name: "AppData",
    platforms: [.iOS(.v26)],
    products: [.library(name: "AppData", targets: ["AppData"])],
    dependencies: [
        .package(path: "../Domain"),
        .package(path: "../API"),
    ],
    targets: [
        .target(
            name: "AppData",
            dependencies: ["Domain", "API"],
            swiftSettings: [
                .defaultIsolation(nil),
                .enableUpcomingFeature("NonisolatedNonsendingByDefault"),
                .enableUpcomingFeature("InferIsolatedConformances"),
            ]
        )
    ]
)
