// swift-tools-version: 6.2
// The client of the API, generated from the specification (ADR-0003). The app extensions use it (ADR-0007 §1), so the
// default isolation is nonisolated (ADR-0009 §7). It depends on the domain types only.
import PackageDescription

let package = Package(
    name: "API",
    platforms: [.iOS(.v26)],
    products: [.library(name: "API", targets: ["API"])],
    dependencies: [
        .package(path: "../Domain"),
    ],
    targets: [
        .target(
            name: "API",
            dependencies: ["Domain"],
            swiftSettings: [
                .defaultIsolation(nil),
                .enableUpcomingFeature("NonisolatedNonsendingByDefault"),
                .enableUpcomingFeature("InferIsolatedConformances"),
            ]
        ),
    ]
)
