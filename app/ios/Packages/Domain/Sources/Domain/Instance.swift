/// What a server says about itself: its name and its short description (`GET /api/v1/instance`).
public struct Instance: Sendable, Hashable {
    /// The name of the instance.
    public var name: String
    /// A line about the instance, or nil when it has none.
    public var shortDescription: String?

    public init(name: String, shortDescription: String? = nil) {
        self.name = name
        self.shortDescription = shortDescription
    }
}

/// Why looking up the instance of a server failed, as the screen tells it apart.
public enum InstanceLookupError: Error, Sendable, Hashable {
    /// The server did not answer: no connection, a time-out, or another failure of the network.
    case unreachable
    /// Something answered, but not as a mycroblog server does.
    case notMycroblog
    /// The server answered with a problem document; `title` is its summary, localized from `Accept-Language`.
    case problem(title: String, status: Int)
}
