import API
import Domain
import Foundation
import HTTPTypes
import Testing

/// `APIClient.instance()` fetches `GET /api/v1/instance` and turns each answer into the instance or the kind of error the
/// server screen shows: the server did not answer, it is not a mycroblog server, or it answered with a Problem.
struct InstanceLookupTests {
    static let origin = URL(string: "https://example.social")!

    /// The error kind `instance()` threw on `transport`, or nil when it returned an instance.
    static func lookupError(on transport: StubTransport) async throws -> InstanceLookupError? {
        do {
            _ = try await APIClient(origin: origin, transport: transport, middlewares: []).instance()
            return nil
        } catch let error as InstanceLookupError {
            return error
        }
    }

    /// The bytes of `json`, an object written in a test.
    static func json(_ json: String) -> Data { Data(json.utf8) }

    @Test func fetchesTheInstanceFromTheOrigin() async throws {
        let transport = StubTransport(
            status: .ok,
            contentType: "application/json",
            body: try SharedFixture.data("instance/response.json")
        )

        _ = try await APIClient(origin: Self.origin, transport: transport, middlewares: []).instance()

        let sent = try #require(transport.sent.first, "nothing was sent")
        #expect(transport.sent.count == 1)
        #expect(sent.request.method == .get)
        let url = sent.baseURL.absoluteString.trimmingSuffix("/") + (sent.request.path ?? "")
        #expect(url == "https://example.social/api/v1/instance")
    }

    @Test func aSuccessGivesTheNameAndTheShortDescription() async throws {
        let transport = StubTransport(
            status: .ok,
            contentType: "application/json",
            body: try SharedFixture.data("instance/response.json")
        )
        let expected = try SharedFixture.decode(InstanceFixtureTests.Expected.self, from: "instance/expected.json")

        let instance = try await APIClient(origin: Self.origin, transport: transport, middlewares: []).instance()

        #expect(instance.name == expected.name)
        #expect(instance.shortDescription == expected.shortDescription)
    }

    @Test func anInstanceWithoutAShortDescriptionHasNone() async throws {
        var object = try #require(
            try JSONSerialization.jsonObject(with: SharedFixture.data("instance/response.json")) as? [String: Any]
        )
        object.removeValue(forKey: "short_description")
        let transport = StubTransport(
            status: .ok,
            contentType: "application/json",
            body: try JSONSerialization.data(withJSONObject: object)
        )

        let instance = try await APIClient(origin: Self.origin, transport: transport, middlewares: []).instance()

        #expect(instance.shortDescription == nil)
    }

    @Test(arguments: [
        URLError(.cannotConnectToHost),
        URLError(.cannotFindHost),
        URLError(.notConnectedToInternet),
        URLError(.timedOut),
        URLError(.networkConnectionLost),
        URLError(.secureConnectionFailed),
    ])
    func noAnswerIsUnreachable(_ failure: URLError) async throws {
        let error = try await Self.lookupError(on: StubTransport(failingWith: failure))
        #expect(error == .unreachable, "\(failure.code) gave \(String(describing: error))")
    }

    @Test(arguments: [
        (HTTPResponse.Status.ok, "text/html", "<html><body>Hello</body></html>"),
        (.ok, "application/json", #"{"hello": "world"}"#),
        (.ok, "application/json", "not json"),
        (.notFound, "text/html", "<html><body>Not Found</body></html>"),
        (.notFound, "application/json", #"{"error": "not found"}"#),
        (.internalServerError, "text/plain", "Internal Server Error"),
    ])
    func anAnswerThatIsNotMyIsNotMycroblog(_ status: HTTPResponse.Status, _ contentType: String, _ body: String)
        async throws
    {
        let transport = StubTransport(status: status, contentType: contentType, body: Self.json(body))
        let error = try await Self.lookupError(on: transport)
        #expect(error == .notMycroblog, "\(status.code) \(contentType) gave \(String(describing: error))")
    }

    @Test(arguments: [
        (HTTPResponse.Status.serviceUnavailable, "Service Unavailable"),
        (.tooManyRequests, "Too many requests"),
        (.notFound, "Not Found"),
    ])
    func aProblemGivesItsTitleAndStatus(_ status: HTTPResponse.Status, _ title: String) async throws {
        let problem = #"{"type": "about:blank", "title": "\#(title)", "status": \#(status.code)}"#
        let transport = StubTransport(status: status, contentType: "application/problem+json", body: Self.json(problem))
        let error = try await Self.lookupError(on: transport)
        #expect(
            error == .problem(title: title, status: status.code),
            "\(status.code) \(title) gave \(String(describing: error))"
        )
    }
}

extension String {
    /// The string without `suffix` at its end, when it ends with it.
    fileprivate func trimmingSuffix(_ suffix: String) -> String {
        hasSuffix(suffix) ? String(dropLast(suffix.count)) : self
    }
}
