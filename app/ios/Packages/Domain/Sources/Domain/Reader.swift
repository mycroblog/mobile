// Reading a server without an account: the reader context of ADR-0007 §3, an account bundle without an account,
// keyed by the server's origin.
import Foundation

/// The context of a reader who has not signed in: the server they read. The store keys its records by it.
public struct ReaderContext: Sendable, Hashable, Codable {
    /// The origin of the server, such as `https://example.social`.
    public var origin: URL

    public init(origin: URL) {
        self.origin = origin
    }

    public init(server: ServerAddress) {
        self.init(origin: server.origin)
    }

    /// The key of the context in the store.
    public var key: String { "reader:" + origin.absoluteString }

    /// The host of the server, as a screen shows it.
    public var host: String { origin.host() ?? origin.absoluteString }
}

/// Why reading a timeline failed, as the screen tells it apart.
public enum TimelineLoadError: Error, Sendable, Hashable {
    /// The server did not answer.
    case unreachable
    /// The server does not offer the timeline (404): its destination is hidden.
    case notFound
    /// The rate limit was reached (429); trying again is allowed after `retryAfter` seconds, when the server says.
    case rateLimited(retryAfter: Int?)
    /// The server answered with another problem; `title` is its summary, localized from `Accept-Language`.
    case problem(title: String, status: Int)
    /// The server answered with something that is not a timeline.
    case invalidAnswer
}
