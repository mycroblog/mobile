// swift-tools-version: 6.2
// The pinned swift-openapi-generator that writes the committed client of the API package (ADR-0003 §2):
// `task ios:api-generate` runs it with `swift run`. It is a tool of this repository, not a part of the app.
import PackageDescription

let package = Package(
    name: "APIGenerator",
    platforms: [.macOS(.v15)],
    dependencies: [
        .package(url: "https://github.com/apple/swift-openapi-generator", exact: "1.14.4")
    ]
)
