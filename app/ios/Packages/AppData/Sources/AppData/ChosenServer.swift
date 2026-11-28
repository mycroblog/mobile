// The server a reader chose, remembered across launches with what it was last known to offer, so that the app shows
// the same server and destinations before the network answers.
import Domain
import Foundation

/// The server a reader reads without an account, and the public timelines it was last known to offer.
public struct ChosenServer: Sendable, Hashable, Codable {
    public var context: ReaderContext
    /// The public timelines it offers to a reader who has not signed in, as its instance last said.
    public var publicTimelines: [PublicTimeline]

    public init(context: ReaderContext, publicTimelines: [PublicTimeline]) {
        self.context = context
        self.publicTimelines = publicTimelines
    }
}

/// Remembers the chosen server in the user defaults: a setting of the reader, not a secret.
public struct ChosenServerStore: Sendable {
    private let suiteName: String?
    private let key: String

    /// A store in the standard user defaults, or in the suite `suiteName` (a test passes its own).
    public init(suiteName: String? = nil, key: String = "ChosenServer") {
        self.suiteName = suiteName
        self.key = key
    }

    private var defaults: UserDefaults { suiteName.flatMap(UserDefaults.init(suiteName:)) ?? .standard }

    /// The server chosen last, or nil when none was.
    public func load() -> ChosenServer? {
        guard let data = defaults.data(forKey: key) else { return nil }
        return try? JSONDecoder().decode(ChosenServer.self, from: data)
    }

    /// Remembers `server`, or forgets the chosen one with nil.
    public func save(_ server: ChosenServer?) {
        if let server, let data = try? JSONEncoder().encode(server) {
            defaults.set(data, forKey: key)
        } else {
            defaults.removeObject(forKey: key)
        }
    }
}
