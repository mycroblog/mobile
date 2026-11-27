// The client of the API of `my`. Its operations and types are generated into the target GeneratedAPI (ADR-0003 §2)
// and stay inside this package; what the rest of the app uses is written here.
import Foundation
import GeneratedAPI
import OpenAPIRuntime
import OpenAPIURLSession

/// A connection to the client API of one `my` server.
public struct APIClient: Sendable {
    /// The generated client, for the operations this package wraps.
    package let generated: Client

    /// A client of the server at `origin` (such as `https://example.social`), over `session`.
    public init(origin: URL, session: URLSession = .shared) {
        self.init(origin: origin, transport: URLSessionTransport(configuration: .init(session: session)))
    }

    /// A client of the server at `origin` over `transport`, such as a stub in a test.
    package init(origin: URL, transport: any ClientTransport, middlewares: [any ClientMiddleware] = []) {
        generated = Client(
            serverURL: Self.baseURL(of: origin),
            transport: transport,
            middlewares: middlewares
        )
    }

    /// The URL the operations' paths are relative to: the document's server, `/api/v1`, on `origin`.
    package static func baseURL(of origin: URL) -> URL {
        origin.appending(path: "api/v1")
    }
}
