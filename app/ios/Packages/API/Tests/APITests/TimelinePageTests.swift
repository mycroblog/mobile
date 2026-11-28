import API
import Domain
import Foundation
import HTTPTypes
import OpenAPIRuntime
import Testing

/// `APIClient.page(of:cursor:limit:)` fetches `GET /api/v1/timelines/local` or `/federated` and turns each answer into a
/// page or the kind of error a timeline's screen shows: the server does not offer it (404, its destination is hidden),
/// the rate limit was reached (429, with `Retry-After`), or another problem.
struct TimelinePageTests {
    static let origin = URL(string: "https://example.social")!

    /// A transport that answers with `status`, the headers `headers`, and `body`.
    static func transport(_ status: HTTPResponse.Status, headers: [HTTPField.Name: String] = [:], body: String?)
        -> StubTransport
    {
        StubTransport {
            var fields = HTTPFields()
            for (name, value) in headers { fields[name] = value }
            return (HTTPResponse(status: status, headerFields: fields), body.map { HTTPBody(Data($0.utf8)) })
        }
    }

    static func problem(_ status: Int, _ title: String) -> String {
        #"{"type": "about:blank", "title": "\#(title)", "status": \#(status)}"#
    }

    /// The error `page(of:)` threw on `transport`, or nil when it returned a page.
    static func pageError(_ timeline: PublicTimeline = .local, on transport: StubTransport) async throws
        -> TimelineLoadError?
    {
        do {
            _ = try await APIClient(origin: origin, transport: transport, middlewares: []).page(of: timeline)
            return nil
        } catch let error as TimelineLoadError {
            return error
        }
    }

    /// The URL a request was sent to, with its query.
    static func url(of sent: StubTransport.Sent) -> URLComponents? {
        URLComponents(string: sent.baseURL.absoluteString.trimmingSuffix("/") + (sent.request.path ?? ""))
    }

    @Test(arguments: [(PublicTimeline.local, "/api/v1/timelines/local"), (.federated, "/api/v1/timelines/federated")])
    func fetchesTheTimelineFromTheOrigin(_ timeline: PublicTimeline, _ path: String) async throws {
        let transport = StubTransport(
            status: .ok,
            contentType: "application/json",
            body: try SharedFixture.data("timeline-page/response.json")
        )

        _ = try await APIClient(origin: Self.origin, transport: transport, middlewares: []).page(of: timeline)

        let sent = try #require(transport.sent.first, "nothing was sent")
        #expect(transport.sent.count == 1)
        #expect(sent.request.method == .get)
        let url = try #require(Self.url(of: sent))
        #expect(url.host == "example.social")
        #expect(url.path == path)
        #expect((url.queryItems ?? []).isEmpty, "the newest page was asked with \(url.queryItems ?? [])")
    }

    @Test func anOlderPageSendsTheCursorAndTheLimit() async throws {
        let transport = StubTransport(
            status: .ok,
            contentType: "application/json",
            body: try SharedFixture.data("timeline-page/response.json")
        )
        let cursor = "b2xkZXI+/=="

        _ = try await APIClient(origin: Self.origin, transport: transport, middlewares: [])
            .page(of: .local, cursor: cursor, limit: 5)

        let url = try #require(transport.sent.first.flatMap(Self.url(of:)))
        let query = Dictionary(uniqueKeysWithValues: (url.queryItems ?? []).map { ($0.name, $0.value) })
        #expect(query["cursor"] == cursor)
        #expect(query["limit"] == "5")
    }

    @Test func aSuccessGivesThePage() async throws {
        let transport = StubTransport(
            status: .ok,
            contentType: "application/json",
            body: try SharedFixture.data("timeline-page/response.json")
        )

        let page = try await APIClient(origin: Self.origin, transport: transport, middlewares: []).page(of: .local)

        #expect(page == (try PostPage(decodingJSON: SharedFixture.data("timeline-page/response.json"))))
    }

    @Test func notFoundIsATimelineTheServerDoesNotOffer() async throws {
        let transport = Self.transport(
            .notFound,
            headers: [.contentType: "application/problem+json"],
            body: Self.problem(404, "Not Found")
        )
        #expect(try await Self.pageError(on: transport) == .notFound)
        #expect(try await Self.pageError(.federated, on: transport) == .notFound)
    }

    @Test func aSingleUserServerIsNotFound() async throws {
        let body = #"{"type": "/api/v1/problems/single-user-mode", "title": "Single user", "status": 404}"#
        let transport = Self.transport(.notFound, headers: [.contentType: "application/problem+json"], body: body)
        #expect(try await Self.pageError(on: transport) == .notFound)
    }

    @Test(arguments: [("120", 120), ("1", 1)])
    func tooManyRequestsGivesTheSecondsOfRetryAfter(_ header: String, _ seconds: Int) async throws {
        let transport = Self.transport(
            .tooManyRequests,
            headers: [.contentType: "application/problem+json", .retryAfter: header],
            body: Self.problem(429, "Too Many Requests")
        )
        #expect(try await Self.pageError(on: transport) == .rateLimited(retryAfter: seconds))
    }

    @Test func tooManyRequestsWithoutRetryAfterHasNoDelay() async throws {
        let transport = Self.transport(
            .tooManyRequests,
            headers: [.contentType: "application/problem+json"],
            body: Self.problem(429, "Too Many Requests")
        )
        #expect(try await Self.pageError(on: transport) == .rateLimited(retryAfter: nil))
    }

    @Test(arguments: [(HTTPResponse.Status.unauthorized, "Unauthorized"), (.serviceUnavailable, "Unavailable")])
    func anotherProblemGivesItsTitleAndStatus(_ status: HTTPResponse.Status, _ title: String) async throws {
        let transport = Self.transport(
            status,
            headers: [.contentType: "application/problem+json"],
            body: Self.problem(status.code, title)
        )
        #expect(try await Self.pageError(on: transport) == .problem(title: title, status: status.code))
    }

    @Test(arguments: [
        URLError(.cannotConnectToHost), URLError(.notConnectedToInternet), URLError(.timedOut),
    ])
    func noAnswerIsUnreachable(_ failure: URLError) async throws {
        #expect(try await Self.pageError(on: StubTransport(failingWith: failure)) == .unreachable)
    }

    @Test(arguments: [
        (HTTPResponse.Status.ok, "application/json", #"{"hello": "world"}"#),
        (.ok, "application/json", "not json"),
        (.ok, "text/html", "<html></html>"),
    ])
    func anAnswerThatIsNotAPageIsInvalid(_ status: HTTPResponse.Status, _ contentType: String, _ body: String)
        async throws
    {
        let transport = Self.transport(status, headers: [.contentType: contentType], body: body)
        #expect(try await Self.pageError(on: transport) == .invalidAnswer)
    }
}

extension String {
    /// The string without `suffix` at its end, when it ends with it.
    fileprivate func trimmingSuffix(_ suffix: String) -> String {
        hasSuffix(suffix) ? String(dropLast(suffix.count)) : self
    }
}
