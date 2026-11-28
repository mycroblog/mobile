import API
import Domain
import Foundation
import Testing

/// A page of `GET /api/v1/timelines/local`, shared/fixtures/timeline-page/response.json, shows each post as
/// timeline-page/expected.json says, as both apps must: the author, who boosted it, the content warning, what is
/// hidden, the media, and the counts.
struct TimelinePageFixtureTests {
    struct Expected: Decodable {
        let nextCursor: String?
        let items: [Item]

        private enum CodingKeys: String, CodingKey {
            case items
            case nextCursor = "next_cursor"
        }
    }

    struct Person: Decodable, Sendable {
        let displayName: [ContentJSON.Run]
        let handle: String?
        let avatar: String?

        private enum CodingKeys: String, CodingKey {
            case handle, avatar
            case displayName = "display_name"
        }
    }

    struct Media: Decodable, Hashable, Sendable {
        let type: String
        let description: String?
        let preview: String?
        let blurhash: String?

        init(type: String, description: String?, preview: String?, blurhash: String?) {
            self.type = type
            self.description = description
            self.preview = preview
            self.blurhash = blurhash
        }

        init(_ media: MediaAttachment) {
            self.init(
                type: media.kind.rawValue,
                description: media.description,
                preview: media.preview?.absoluteString,
                blurhash: media.blurhash
            )
        }
    }

    struct Counts: Decodable, Hashable, Sendable {
        let replies: Int
        let boosts: Int
        let favourites: Int
    }

    struct Item: Decodable, CustomTestStringConvertible, Sendable {
        let id: String
        let boostedBy: Person?
        let author: Person
        let contentWarning: [ContentJSON.Run]?
        let content: [ContentJSON.Block]
        let contentHidden: Bool
        let mediaHidden: Bool
        let media: [Media]
        let omittedMediaCount: Int
        let edited: Bool
        let counts: Counts

        private enum CodingKeys: String, CodingKey {
            case id, author, content, media, edited, counts
            case boostedBy = "boosted_by"
            case contentWarning = "content_warning"
            case contentHidden = "content_hidden"
            case mediaHidden = "media_hidden"
            case omittedMediaCount = "omitted_media_count"
        }

        var testDescription: String { id }
    }

    static func page() throws -> PostPage {
        try PostPage(decodingJSON: SharedFixture.data("timeline-page/response.json"))
    }

    static let expected = try? SharedFixture.decode(Expected.self, from: "timeline-page/expected.json")
    static let expectedItems: [Item] = expected?.items ?? []

    /// The item of the page with the outer ID of `expected`.
    static func item(like expected: Item) throws -> TimelineItem {
        try #require(try page().items.first { $0.id == expected.id }, "the page has no item \(expected.id)")
    }

    @Test func thePageHasTheItemsInOrderAndTheCursor() throws {
        let page = try Self.page()
        let expected = try #require(Self.expected, "shared/fixtures/timeline-page/expected.json did not decode")

        #expect(page.items.map(\.id) == expected.items.map(\.id))
        #expect(page.entryIDs == expected.items.map(\.id))
        #expect(page.nextCursor == expected.nextCursor)
    }

    @Test(arguments: expectedItems)
    func theAuthor(_ expected: Item) throws {
        let author = try Self.item(like: expected).author

        #expect(author.displayName.map(ContentJSON.Run.init) == expected.author.displayName)
        #expect(author.handle == expected.author.handle)
        #expect(author.avatar?.absoluteString == expected.author.avatar)
    }

    @Test(arguments: expectedItems)
    func whoBoostedIt(_ expected: Item) throws {
        let booster = try Self.item(like: expected).boostedBy

        #expect((booster == nil) == (expected.boostedBy == nil))
        if let booster, let wanted = expected.boostedBy {
            #expect(booster.displayName.map(ContentJSON.Run.init) == wanted.displayName)
            #expect(booster.handle == wanted.handle)
        }
    }

    @Test(arguments: expectedItems)
    func theContentAndItsWarning(_ expected: Item) throws {
        let post = try Self.item(like: expected).post

        #expect(post.contentWarning?.map(ContentJSON.Run.init) == expected.contentWarning)
        #expect(post.content.map(ContentJSON.Block.init) == expected.content)
    }

    @Test(arguments: expectedItems)
    func whatIsHidden(_ expected: Item) throws {
        let item = try Self.item(like: expected)

        #expect(item.isContentHidden == expected.contentHidden)
        #expect(item.isMediaHidden == expected.mediaHidden)
    }

    @Test(arguments: expectedItems)
    func theMedia(_ expected: Item) throws {
        let post = try Self.item(like: expected).post

        #expect(post.media.map(Media.init) == expected.media)
        #expect(post.omittedMediaCount == expected.omittedMediaCount)
    }

    @Test(arguments: expectedItems)
    func editedAndTheCounts(_ expected: Item) throws {
        let post = try Self.item(like: expected).post

        #expect(post.isEdited == expected.edited)
        #expect(
            Counts(replies: post.counts.replies, boosts: post.counts.boosts, favourites: post.counts.favourites)
                == expected.counts
        )
    }

    /// A boost is one entry of the timeline with its own ID, whose post is the boosted one.
    @Test func aBoostShowsTheBoostedPostUnderItsOwnID() throws {
        let boost = try #require(try Self.page().items.first { $0.boostedBy != nil }, "the page has no boost")

        #expect(boost.id == "01890a5d-ac96-774b-bcce-b30209990006")
        #expect(boost.post.id == "01890a5d-ac96-774b-bcce-b30209990005")
    }
}
