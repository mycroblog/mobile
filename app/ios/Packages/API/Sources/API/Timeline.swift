// `GET /api/v1/timelines/local` and `/federated`: a page of a server's public timeline, read without signing in, as the
// records the store keeps (ADR-0008 §2). The content of each post is parsed here, once, off the main thread.
import Domain
import Foundation
import GeneratedAPI
import HTTPTypes
import OpenAPIRuntime

extension APIClient {
    /// A page of the public timeline `timeline`: the newest, or the one older than `cursor`.
    ///
    /// - Throws: A ``TimelineLoadError``, or `CancellationError` when the task was cancelled.
    public func page(of timeline: PublicTimeline, cursor: String? = nil, limit: Int? = nil) async throws -> PostPage {
        do {
            switch timeline {
            case .local:
                switch try await generated.getLocalTimeline(query: .init(cursor: cursor, limit: limit)) {
                case .ok(let ok):
                    return try PostPage(ok.body.json)
                case .default(let status, let answer):
                    throw Self.timelineError(status: status, answer)
                }
            case .federated:
                switch try await generated.getFederatedTimeline(query: .init(cursor: cursor, limit: limit)) {
                case .ok(let ok):
                    return try PostPage(ok.body.json)
                case .default(let status, let answer):
                    throw Self.timelineError(status: status, answer)
                }
            }
        } catch let error as TimelineLoadError {
            throw error
        } catch {
            throw try Self.timelineError(error)
        }
    }

    private static func timelineError(status: Int, _ answer: Components.Responses.Problem) -> TimelineLoadError {
        switch status {
        case 404: return .notFound
        case 429: return .rateLimited(retryAfter: answer.headers.retryAfter)
        default:
            switch answer.body {
            case .applicationProblemJson(let problem): return .problem(title: problem.title, status: problem.status)
            }
        }
    }

    /// What a failure of the generated client means to the screen; a cancellation is thrown again as itself.
    private static func timelineError(_ error: any Error) throws -> TimelineLoadError {
        let underlying = (error as? ClientError)?.underlyingError ?? error
        if underlying is CancellationError || (underlying as? URLError)?.code == .cancelled {
            throw CancellationError()
        }
        if underlying is URLError {
            return .unreachable
        }
        if let clientError = error as? ClientError {
            if clientError.response == nil { return .unreachable }
            // An answer the document does not describe, such as a 404 or a 429 without a problem document.
            switch clientError.response?.status.code {
            case 404: return .notFound
            case 429:
                let retryAfter = clientError.response?.headerFields[.retryAfter].flatMap { Int($0) }
                return .rateLimited(retryAfter: retryAfter)
            default: break
            }
        }
        return .invalidAnswer
    }
}

extension PostPage {
    /// The page of `json`, an answer of `GET /timelines/local` or `/federated` (shared/fixtures/timeline-page).
    public init(decodingJSON json: Data) throws {
        let decoder = JSONDecoder()
        let transcoder = RFC3339DateTranscoder()
        decoder.dateDecodingStrategy = .custom { decoder in
            try transcoder.decode(decoder.singleValueContainer().decode(String.self))
        }
        try self.init(decoder.decode(Components.Schemas.StatusPage.self, from: json))
    }

    package init(_ page: Components.Schemas.StatusPage) {
        var posts: [Post] = []
        var accounts: [String: Account] = [:]
        func add(_ status: Components.Schemas.Status) {
            if let boost = status.boost { add(boost) }
            posts.append(Post(status))
            accounts[status.account.id] = Account(status.account)
        }
        for status in page.value2.items { add(status) }
        self.init(
            posts: posts,
            accounts: Array(accounts.values).sorted { $0.id < $1.id },
            entryIDs: page.value2.items.map(\.id),
            nextCursor: page.value1.nextCursor
        )
    }
}

extension Post {
    package init(_ status: Components.Schemas.Status) {
        let emojis = status.emojis.compactMap { CustomEmoji($0) }
        self.init(
            id: status.id,
            accountID: status.account.id,
            boostOf: status.boost?.id,
            url: status.url.flatMap(URL.init(string:)),
            createdAt: status.createdAt,
            editedAt: status.editedAt,
            contentWarning: status.spoilerText.isEmpty
                ? nil : PostContentParser.runs(text: status.spoilerText, emojis: emojis),
            content: PostContentParser.blocks(html: status.content, emojis: emojis),
            isSensitive: status.sensitive,
            media: status.mediaAttachments.map { MediaAttachment($0) },
            omittedMediaCount: status.omittedMediaCount ?? 0,
            counts: PostCounts(
                replies: status.repliesCount,
                boosts: status.boostsCount,
                favourites: status.favouritesCount
            ),
            unshownParts: Self.unshownParts(of: status)
        )
    }

    private static func unshownParts(of status: Components.Schemas.Status) -> [UnshownPart]? {
        var parts: [UnshownPart] = []
        if status.poll != nil { parts.append(.poll) }
        if status.quote != nil { parts.append(.quote) }
        if status.article != nil { parts.append(.article) }
        if status.eventBoard != nil { parts.append(.eventBoard) }
        return parts.isEmpty ? nil : parts
    }
}

extension Account {
    package init(_ account: Components.Schemas.Account) {
        self.init(
            id: account.id,
            displayName: account.displayName,
            username: account.username,
            domain: account.domain,
            acct: account.acct,
            emojis: account.emojis.compactMap { CustomEmoji($0) },
            avatar: account.avatar.flatMap { URL(string: $0.small.url) },
            url: URL(string: account.url)
        )
    }
}

extension MediaAttachment {
    package init(_ attachment: Components.Schemas.MediaAttachment) {
        let kind: MediaKind =
            switch attachment._type {
            case .image: .image
            case .gifv: .gifv
            case .video: .video
            case .audio: .audio
            case .unknown: .unknown
            }
        var aspectRatio: Double?
        if let width = attachment.meta.width, let height = attachment.meta.height, width > 0, height > 0 {
            aspectRatio = Double(width) / Double(height)
        }
        self.init(
            id: attachment.id,
            kind: kind,
            description: attachment.description.flatMap { $0.isEmpty ? nil : $0 },
            preview: attachment.previewUrl.flatMap(URL.init(string:)),
            blurhash: attachment.blurhash,
            aspectRatio: aspectRatio
        )
    }
}

extension CustomEmoji {
    package init?(_ emoji: Components.Schemas.CustomEmoji) {
        guard let url = URL(string: emoji.url), let staticURL = URL(string: emoji.staticUrl) else { return nil }
        self.init(shortcode: emoji.shortcode, url: url, staticURL: staticURL)
    }
}
