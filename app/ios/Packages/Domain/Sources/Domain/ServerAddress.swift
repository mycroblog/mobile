import Foundation

/// The address of a server that a person typed, reduced to its origin: the scheme, the host and the port.
///
/// The scheme is https; http is accepted only for the hosts of a local server in development and in the tests.
public struct ServerAddress: Sendable, Hashable {
    /// The hosts that may be reached over plain http: the local `my` of the tests and of development, also as the
    /// Android emulator reaches its host.
    public static let localHosts: Set<String> = ["localhost", "127.0.0.1", "10.0.2.2"]

    /// The origin of the server, such as `https://example.social` or `http://localhost:8790`.
    public let origin: URL
    /// The host of the server, such as `example.social`, without the port.
    public let host: String

    /// The address in `text`, or nil when it is not one: spaces around it are trimmed, a missing scheme is https,
    /// and a path, a query or a fragment is dropped.
    public init?(parsing text: String) {
        let trimmed = text.trimmingCharacters(in: .whitespacesAndNewlines)
        guard !trimmed.isEmpty else { return nil }
        let withScheme = trimmed.contains("://") ? trimmed : "https://" + trimmed
        guard let components = URLComponents(string: withScheme),
            let scheme = components.scheme?.lowercased(),
            let host = components.host?.lowercased(), !host.isEmpty,
            components.user == nil, components.password == nil
        else { return nil }
        guard scheme == "https" || (scheme == "http" && Self.localHosts.contains(host)) else { return nil }
        if let port = components.port, !(1...65535).contains(port) { return nil }

        var origin = URLComponents()
        origin.scheme = scheme
        origin.host = host
        origin.port = components.port
        guard let url = origin.url else { return nil }
        self.origin = url
        self.host = host
    }
}
