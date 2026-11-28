// A post and its author as a client keeps and shows them (the `Status` and `Account` of the API). The content is
// parsed once, when the post is read from the server, and kept parsed (ADR-0008 §2: one record per post and account).
// What a client shows of each is pinned by shared/fixtures/timeline-page.
import Foundation

/// The author of a post, or who boosted it, as a client shows them.
public struct Account: Sendable, Hashable, Codable, Identifiable {
    public var id: String
    /// The display name with its custom emoji; the username when it is empty, or the domain when that is too.
    public var displayName: [ContentRun]
    /// `@` and the `acct`, such as `@alice` or `@carol@remote.example`, or nil when the account has no `acct`.
    public var handle: String?
    /// The small avatar, or nil when the account has none.
    public var avatar: URL?
    /// The profile on the web.
    public var url: URL?

    public init(id: String, displayName: [ContentRun], handle: String?, avatar: URL?, url: URL? = nil) {
        self.id = id
        self.displayName = displayName
        self.handle = handle
        self.avatar = avatar
        self.url = url
    }

    /// The account of the API's fields: `display_name` with `emojis`, `username`, `domain`, `acct`, and the small
    /// avatar.
    public init(
        id: String,
        displayName: String,
        username: String,
        domain: String,
        acct: String,
        emojis: [CustomEmoji],
        avatar: URL?,
        url: URL?
    ) {
        var name = PostContentParser.runs(text: displayName, emojis: emojis)
        if name.isEmpty { name = PostContentParser.runs(text: username, emojis: []) }
        if name.isEmpty { name = PostContentParser.runs(text: domain, emojis: []) }
        self.init(id: id, displayName: name, handle: acct.isEmpty ? nil : "@" + acct, avatar: avatar, url: url)
    }
}

/// The kind of a media attachment.
public enum MediaKind: String, Sendable, Hashable, Codable {
    case image
    case gifv
    case video
    case audio
    case unknown
}

/// A media attachment of a post, as a preview.
public struct MediaAttachment: Sendable, Hashable, Codable, Identifiable {
    public var id: String
    public var kind: MediaKind
    /// The description, which is the accessible label of the preview.
    public var description: String?
    /// The preview image (`preview_url`), or nil when there is none.
    public var preview: URL?
    /// The blurhash shown while the preview loads and over hidden media.
    public var blurhash: String?
    /// The width divided by the height, when the server knows them.
    public var aspectRatio: Double?

    public init(
        id: String,
        kind: MediaKind,
        description: String?,
        preview: URL?,
        blurhash: String?,
        aspectRatio: Double? = nil
    ) {
        self.id = id
        self.kind = kind
        self.description = description
        self.preview = preview
        self.blurhash = blurhash
        self.aspectRatio = aspectRatio
    }
}

/// How many replies, boosts and favourites a post has.
public struct PostCounts: Sendable, Hashable, Codable {
    public var replies: Int
    public var boosts: Int
    public var favourites: Int

    public init(replies: Int, boosts: Int, favourites: Int) {
        self.replies = replies
        self.boosts = boosts
        self.favourites = favourites
    }
}

/// A post, with its content parsed. A boost is a post of its own whose `boostOf` names the boosted post.
public struct Post: Sendable, Hashable, Codable, Identifiable {
    public var id: String
    /// The ID of the author's account.
    public var accountID: String
    /// The ID of the boosted post, when this post is a boost.
    public var boostOf: String?
    /// The post on the web.
    public var url: URL?
    public var createdAt: Date
    /// When the post was last edited, or nil when it never was.
    public var editedAt: Date?
    /// The content warning, or nil when there is none.
    public var contentWarning: [ContentRun]?
    public var content: [ContentBlock]
    /// Whether the media are sensitive.
    public var isSensitive: Bool
    public var media: [MediaAttachment]
    /// How many media the server left out of `media`.
    public var omittedMediaCount: Int
    public var counts: PostCounts

    public init(
        id: String,
        accountID: String,
        boostOf: String? = nil,
        url: URL? = nil,
        createdAt: Date,
        editedAt: Date? = nil,
        contentWarning: [ContentRun]? = nil,
        content: [ContentBlock],
        isSensitive: Bool = false,
        media: [MediaAttachment] = [],
        omittedMediaCount: Int = 0,
        counts: PostCounts = PostCounts(replies: 0, boosts: 0, favourites: 0)
    ) {
        self.id = id
        self.accountID = accountID
        self.boostOf = boostOf
        self.url = url
        self.createdAt = createdAt
        self.editedAt = editedAt
        self.contentWarning = contentWarning
        self.content = content
        self.isSensitive = isSensitive
        self.media = media
        self.omittedMediaCount = omittedMediaCount
        self.counts = counts
    }

    /// Whether the post was edited.
    public var isEdited: Bool { editedAt != nil }
}

/// What a timeline shows for one of its entries: a post with its author, or, for a boost, the boosted post with its
/// author and who boosted it.
public struct TimelineItem: Sendable, Hashable, Identifiable {
    /// The ID of the timeline's entry: the post, or the boost.
    public var id: String
    /// The post shown: the boosted post for a boost.
    public var post: Post
    public var author: Account
    /// Who boosted the post, for a boost.
    public var boostedBy: Account?

    public init(id: String, post: Post, author: Account, boostedBy: Account? = nil) {
        self.id = id
        self.post = post
        self.author = author
        self.boostedBy = boostedBy
    }

    /// The item of the entry `id`, from the records `posts` and `accounts`, or nil when one it needs is missing.
    public init?(id: String, posts: [String: Post], accounts: [String: Account]) {
        guard let entry = posts[id] else { return nil }
        if let boostOf = entry.boostOf {
            guard let boosted = posts[boostOf], let author = accounts[boosted.accountID],
                let booster = accounts[entry.accountID]
            else { return nil }
            self.init(id: id, post: boosted, author: author, boostedBy: booster)
        } else {
            guard let author = accounts[entry.accountID] else { return nil }
            self.init(id: id, post: entry, author: author)
        }
    }

    /// Whether the content is hidden until the reader opens it: the post has a content warning.
    public var isContentHidden: Bool { post.contentWarning != nil }

    /// Whether the media are hidden until the reader shows them: the post has a content warning, or sensitive media.
    public var isMediaHidden: Bool { post.contentWarning != nil || post.isSensitive }
}

/// A page of a timeline as the server answers it: the records of its posts (boosted posts included) and their
/// authors, the order of its entries, and the cursor of the next, older page.
public struct PostPage: Sendable, Hashable {
    /// Every post of the page, the boosted ones included.
    public var posts: [Post]
    public var accounts: [Account]
    /// The IDs of the page's entries, newest first.
    public var entryIDs: [String]
    /// The cursor of the older page, or nil when this is the last one.
    public var nextCursor: String?

    public init(posts: [Post], accounts: [Account], entryIDs: [String], nextCursor: String?) {
        self.posts = posts
        self.accounts = accounts
        self.entryIDs = entryIDs
        self.nextCursor = nextCursor
    }

    /// What the page shows, in order.
    public var items: [TimelineItem] {
        let posts = Dictionary(posts.map { ($0.id, $0) }, uniquingKeysWith: { _, last in last })
        let accounts = Dictionary(accounts.map { ($0.id, $0) }, uniquingKeysWith: { _, last in last })
        return entryIDs.compactMap { TimelineItem(id: $0, posts: posts, accounts: accounts) }
    }
}
