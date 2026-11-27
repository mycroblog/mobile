// Looking up a server before signing in: the instance it describes, read from the network and not stored.
import API
import Domain
import Foundation

/// Reads what a server says about itself. The screens use it; a test passes a stub.
public protocol InstanceLookup: Sendable {
    /// The instance of the server at `address`.
    ///
    /// - Throws: An `InstanceLookupError`, or `CancellationError` when the task was cancelled.
    func instance(at address: ServerAddress) async throws -> Instance
}

/// Looks up a server over the network with the client of the API.
public struct RemoteInstanceLookup: InstanceLookup {
    private let session: URLSession

    /// A lookup over `session`. The default session stores nothing and gives up on a server that does not answer
    /// within 15 seconds.
    public init(session: URLSession = RemoteInstanceLookup.defaultSession) {
        self.session = session
    }

    public func instance(at address: ServerAddress) async throws -> Instance {
        try await APIClient(origin: address.origin, session: session).instance()
    }

    /// An ephemeral session, so that a lookup leaves no cookie or cache behind, with a time-out of 15 seconds.
    public static let defaultSession: URLSession = {
        let configuration = URLSessionConfiguration.ephemeral
        configuration.timeoutIntervalForRequest = 15
        return URLSession(configuration: configuration)
    }()
}
