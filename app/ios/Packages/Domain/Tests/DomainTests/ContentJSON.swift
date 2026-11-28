import Domain

/// The content of a post in the form of the shared fixtures (shared/fixtures/post-content), so that what a client makes
/// of it compares with what the fixture says, field by field.
enum ContentJSON {
    /// A link of a run: its kind (`mention`, `hashtag` or `url`) and its target.
    struct Link: Decodable, Hashable, Sendable {
        let type: String
        let href: String
    }

    /// A run: its text or the shortcode of its custom emoji, its styles in order, and its link.
    struct Run: Decodable, Hashable, Sendable, CustomStringConvertible {
        let text: String?
        let emoji: String?
        let styles: [String]
        let link: Link?

        init(text: String?, emoji: String?, styles: [String], link: Link?) {
            self.text = text
            self.emoji = emoji
            self.styles = styles
            self.link = link
        }

        init(from decoder: any Decoder) throws {
            let container = try decoder.container(keyedBy: CodingKeys.self)
            text = try container.decodeIfPresent(String.self, forKey: .text)
            emoji = try container.decodeIfPresent(String.self, forKey: .emoji)
            styles = try container.decodeIfPresent([String].self, forKey: .styles) ?? []
            link = try container.decodeIfPresent(Link.self, forKey: .link)
        }

        private enum CodingKeys: String, CodingKey { case text, emoji, styles, link }

        /// The run as the client made it.
        init(_ run: ContentRun) {
            self.init(
                text: run.text,
                emoji: run.emoji?.shortcode,
                styles: run.styles.map(\.rawValue),
                link: run.link.map { Link(type: $0.kind.rawValue, href: $0.href) }
            )
        }

        var description: String {
            (text.map { "\"\($0)\"" } ?? ":\(emoji ?? "?"):") + (styles.isEmpty ? "" : " \(styles)")
                + (link.map { " -> \($0.type) \($0.href)" } ?? "")
        }
    }

    /// A block: its type (`paragraph`, `quote`, `code` or `list_item`) and its runs.
    struct Block: Decodable, Hashable, Sendable, CustomStringConvertible {
        let type: String
        let runs: [Run]

        init(type: String, runs: [Run]) {
            self.type = type
            self.runs = runs
        }

        /// The block as the client made it.
        init(_ block: ContentBlock) {
            self.init(type: block.kind.rawValue, runs: block.runs.map(Run.init))
        }

        var description: String { "\(type) \(runs)" }
    }

    /// A custom emoji of a fixture's input, as the API gives it.
    struct Emoji: Decodable, Hashable, Sendable {
        let shortcode: String
        let url: String
        let staticURL: String

        private enum CodingKeys: String, CodingKey {
            case shortcode, url
            case staticURL = "static_url"
        }
    }
}
