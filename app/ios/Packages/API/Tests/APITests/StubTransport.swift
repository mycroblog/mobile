import Foundation
import HTTPTypes
import OpenAPIRuntime

/// A transport that answers every request with what `answer` returns, and records the requests it was sent.
final class StubTransport: ClientTransport, @unchecked Sendable {
    /// A request as the client sent it.
    struct Sent {
        let request: HTTPRequest
        let baseURL: URL
        let operationID: String
    }

    private let lock = NSLock()
    private var sentRequests: [Sent] = []
    private let answer: @Sendable () throws -> (HTTPResponse, HTTPBody?)

    init(answer: @escaping @Sendable () throws -> (HTTPResponse, HTTPBody?)) {
        self.answer = answer
    }

    /// An answer with `status`, the header Content-Type `contentType`, and `body`.
    convenience init(status: HTTPResponse.Status, contentType: String?, body: Data?) {
        self.init {
            var fields = HTTPFields()
            if let contentType { fields[.contentType] = contentType }
            return (HTTPResponse(status: status, headerFields: fields), body.map { HTTPBody($0) })
        }
    }

    /// A transport that fails as URLSession does when nothing answers.
    convenience init(failingWith error: any Error & Sendable) {
        self.init { throw error }
    }

    var sent: [Sent] {
        lock.withLock { sentRequests }
    }

    func send(
        _ request: HTTPRequest,
        body: HTTPBody?,
        baseURL: URL,
        operationID: String
    ) async throws -> (HTTPResponse, HTTPBody?) {
        lock.withLock { sentRequests.append(Sent(request: request, baseURL: baseURL, operationID: operationID)) }
        return try answer()
    }
}
