import Foundation

/// A file of the shared fixtures in `shared/fixtures/` of the repository (ADR-0002 §2), read where it is committed, so
/// that both apps test against the same bytes.
enum SharedFixture {
    /// The directory `shared/fixtures/` of the repository this file is in.
    static let directory = URL(filePath: #filePath)
        .deletingLastPathComponent() // SharedFixture.swift
        .deletingLastPathComponent() // APITests
        .deletingLastPathComponent() // Tests
        .deletingLastPathComponent() // API
        .deletingLastPathComponent() // Packages
        .deletingLastPathComponent() // ios
        .deletingLastPathComponent() // app
        .appending(path: "shared/fixtures")

    /// The bytes of the fixture at `path`, relative to `shared/fixtures/`, such as `instance/response.json`.
    static func data(_ path: String) throws -> Data {
        try Data(contentsOf: directory.appending(path: path))
    }

    /// The fixture at `path` decoded as `T`.
    static func decode<T: Decodable>(_ type: T.Type, from path: String) throws -> T {
        try JSONDecoder().decode(type, from: data(path))
    }
}
