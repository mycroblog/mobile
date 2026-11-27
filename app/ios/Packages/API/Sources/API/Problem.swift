// An error answer of the API, an RFC 9457 problem document (`application/problem+json`). The operations that document
// it come with the generated Problem; an answer the document does not describe is read here from its body.
import Foundation
import GeneratedAPI
import HTTPTypes
import OpenAPIRuntime

/// An error the server answered with. Branch on `type` and `status`, never on `title` or `detail`, which are localized.
public struct Problem: Error, Sendable, Hashable {
    /// The URI reference of the problem type, such as `/api/v1/problems/invalid-token`, or `about:blank`.
    public var type: String
    /// A short summary of the problem type, localized from `Accept-Language`.
    public var title: String
    /// The HTTP status code.
    public var status: Int
    /// An explanation of this occurrence, localized from `Accept-Language`.
    public var detail: String?
    /// The request ID, for reporting the problem.
    public var requestID: String?
    /// The member of the request a 422 is about, by its name in the request.
    public var field: String?

    public init(
        type: String,
        title: String,
        status: Int,
        detail: String? = nil,
        requestID: String? = nil,
        field: String? = nil
    ) {
        self.type = type
        self.title = title
        self.status = status
        self.detail = detail
        self.requestID = requestID
        self.field = field
    }

    package init(_ problem: Components.Schemas.Problem) {
        self.init(
            type: problem._type,
            title: problem.title,
            status: problem.status,
            detail: problem.detail,
            requestID: problem.requestId,
            field: problem.field
        )
    }

    /// The problem of an answer the document does not describe, or nil when its body is not a problem document.
    package init?(_ payload: UndocumentedPayload, maxBytes: Int = 64 * 1024) async {
        guard let body = payload.body,
              let contentType = payload.headerFields[.contentType],
              contentType.lowercased().hasPrefix("application/problem+json"),
              let data = try? await Data(collecting: body, upTo: maxBytes),
              let problem = try? JSONDecoder().decode(Components.Schemas.Problem.self, from: data)
        else { return nil }
        self.init(problem)
    }
}
