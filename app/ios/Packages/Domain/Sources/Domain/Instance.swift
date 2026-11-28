/// What a server says about itself: its name and its short description, and who may read its public timelines
/// (`GET /api/v1/instance`).
public struct Instance: Sendable, Hashable, Codable {
    /// The name of the instance.
    public var name: String
    /// A line about the instance, or nil when it has none.
    public var shortDescription: String?
    /// Who may read the local and the federated timeline.
    public var timelines: InstanceTimelines
    /// Whether the server is the home of one person alone.
    public var isSingleUser: Bool

    public init(
        name: String,
        shortDescription: String? = nil,
        timelines: InstanceTimelines = InstanceTimelines(local: .off, federated: .off),
        isSingleUser: Bool = false
    ) {
        self.name = name
        self.shortDescription = shortDescription
        self.timelines = timelines
        self.isSingleUser = isSingleUser
    }

    /// The server-provided timelines offered to a reader who has not signed in, in the order of ADR-0002 §6.
    public var publicTimelinesForReaders: [PublicTimeline] {
        PublicTimeline.offeredToReaders(timelines: timelines, isSingleUser: isSingleUser)
    }
}

/// Who may read something public of an instance (`InstanceAudience`).
public enum InstanceAudience: String, Sendable, Hashable, Codable {
    /// Nobody: the resource is a 404.
    case off
    /// The users signed in to the server.
    case signedIn = "signed_in"
    /// Everyone, anonymous readers included.
    case everyone
}

/// Who may read the public timelines of an instance (`InstanceTimelines`).
public struct InstanceTimelines: Sendable, Hashable, Codable {
    public var local: InstanceAudience
    public var federated: InstanceAudience

    public init(local: InstanceAudience, federated: InstanceAudience) {
        self.local = local
        self.federated = federated
    }
}

/// A timeline a server provides: its local or its federated one.
public enum PublicTimeline: String, Sendable, Hashable, Codable, CaseIterable {
    case local
    case federated

    /// The timelines offered to a reader who has not signed in (shared/fixtures/public-timelines): each whose
    /// audience is everyone, unless the server is single-user.
    public static func offeredToReaders(timelines: InstanceTimelines, isSingleUser: Bool) -> [PublicTimeline] {
        guard !isSingleUser else { return [] }
        return allCases.filter { timeline in
            switch timeline {
            case .local: timelines.local == .everyone
            case .federated: timelines.federated == .everyone
            }
        }
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
