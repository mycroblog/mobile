// swift-tools-version: 6.2
// The client of the API. GeneratedAPI is generated from shared/api/openapi.yaml with swift-openapi-generator and
// committed (`task ios:api-generate`, ADR-0003 §2); its types are `package`, so only the hand-written target API, the
// product, uses them. The app extensions use it (ADR-0007 §1), so the default isolation is nonisolated (ADR-0009 §7).
import PackageDescription

let isolation: [SwiftSetting] = [
    .defaultIsolation(nil),
    .enableUpcomingFeature("NonisolatedNonsendingByDefault"),
    .enableUpcomingFeature("InferIsolatedConformances"),
]

let package = Package(
    name: "API",
    platforms: [.iOS(.v26)],
    products: [.library(name: "API", targets: ["API"])],
    dependencies: [
        .package(path: "../Domain"),
        .package(url: "https://github.com/apple/swift-openapi-runtime", exact: "1.13.4"),
        .package(url: "https://github.com/apple/swift-openapi-urlsession", exact: "1.4.0"),
        .package(url: "https://github.com/apple/swift-http-types", exact: "1.8.0"),
    ],
    targets: [
        .target(
            name: "GeneratedAPI",
            dependencies: [
                .product(name: "OpenAPIRuntime", package: "swift-openapi-runtime"),
                .product(name: "HTTPTypes", package: "swift-http-types"),
            ],
            swiftSettings: isolation
        ),
        .target(
            name: "API",
            dependencies: [
                "Domain",
                "GeneratedAPI",
                .product(name: "OpenAPIRuntime", package: "swift-openapi-runtime"),
                .product(name: "OpenAPIURLSession", package: "swift-openapi-urlsession"),
                .product(name: "HTTPTypes", package: "swift-http-types"),
            ],
            swiftSettings: isolation
        ),
        // The unit tests (Swift Testing): the generated types against the shared fixtures (ADR-0002 §2), and the
        // client against a stub transport.
        .testTarget(
            name: "APITests",
            dependencies: [
                "API",
                "Domain",
                "GeneratedAPI",
                .product(name: "OpenAPIRuntime", package: "swift-openapi-runtime"),
                .product(name: "HTTPTypes", package: "swift-http-types"),
            ],
            swiftSettings: isolation
        ),
        // The integration tests (XCTest) against a local my, whose origin `task ios:test` passes in MY_URL.
        .testTarget(
            name: "APIIntegrationTests",
            dependencies: ["API", "Domain", "GeneratedAPI"],
            swiftSettings: isolation
        ),
    ]
)
