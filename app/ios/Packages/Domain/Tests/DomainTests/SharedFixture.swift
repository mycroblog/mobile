import Foundation

/// A file of the shared fixtures in `shared/fixtures/` of the repository (ADR-0002 §2), read where it is committed, so
/// that both apps test against the same bytes.
enum SharedFixture {
    /// The directory `shared/fixtures/` of the repository this file is in.
    static let directory = URL(filePath: #filePath)
        .deletingLastPathComponent()  // SharedFixture.swift
        .deletingLastPathComponent()  // DomainTests
        .deletingLastPathComponent()  // Tests
        .deletingLastPathComponent()  // Domain
        .deletingLastPathComponent()  // Packages
        .deletingLastPathComponent()  // ios
        .deletingLastPathComponent()  // app
        .appending(path: "shared/fixtures")

    /// The fixture at `path`, relative to `shared/fixtures/`, decoded as `T`.
    static func decode<T: Decodable>(_ type: T.Type, from path: String) throws -> T {
        try JSONDecoder().decode(type, from: Data(contentsOf: directory.appending(path: path)))
    }
}
