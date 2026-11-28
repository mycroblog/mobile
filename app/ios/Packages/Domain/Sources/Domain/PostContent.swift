// The text of a post as a client shows it: blocks of styled and linked runs, with custom emoji inline. The rule is
// shared/fixtures/post-content/cases.json, which both apps' unit tests read (ADR-0002 §2).
import Foundation

/// A custom emoji of a server, which a `:shortcode:` in a text stands for.
public struct CustomEmoji: Sendable, Hashable, Codable {
    /// The name between the colons, such as `blobcat`.
    public var shortcode: String
    /// The image, which may be animated.
    public var url: URL
    /// A still image of it.
    public var staticURL: URL

    public init(shortcode: String, url: URL, staticURL: URL) {
        self.shortcode = shortcode
        self.url = url
        self.staticURL = staticURL
    }
}

/// A block of a text: a paragraph, a quote, a code block, or an item of a list.
public struct ContentBlock: Sendable, Hashable, Codable {
    public enum Kind: String, Sendable, Hashable, Codable {
        case paragraph
        case quote
        case code
        case listItem = "list_item"
    }

    public var kind: Kind
    /// The runs of the block, never empty.
    public var runs: [ContentRun]

    public init(kind: Kind, runs: [ContentRun]) {
        self.kind = kind
        self.runs = runs
    }
}

/// A style of a run, in the order a run lists them.
public enum ContentStyle: String, Sendable, Hashable, Codable, CaseIterable, Comparable {
    case strong
    case emphasis
    case strikethrough
    case underline
    case code

    public static func < (lhs: ContentStyle, rhs: ContentStyle) -> Bool {
        allCases.firstIndex(of: lhs)! < allCases.firstIndex(of: rhs)!
    }
}

/// Where a run leads when it is tapped.
public struct ContentLink: Sendable, Hashable, Codable {
    public enum Kind: String, Sendable, Hashable, Codable {
        /// An account.
        case mention
        /// A hashtag.
        case hashtag
        /// Any other link.
        case url
    }

    public var kind: Kind
    /// The target as the server wrote it.
    public var href: String

    public init(kind: Kind, href: String) {
        self.kind = kind
        self.href = href
    }

    /// The target as a URL, or nil when it is not one.
    public var url: URL? { URL(string: href) }
}

/// A piece of a block with one set of styles and one link: text, or a custom emoji.
public struct ContentRun: Sendable, Hashable, Codable {
    public enum Content: Sendable, Hashable, Codable {
        case text(String)
        case emoji(CustomEmoji)
    }

    public var content: Content
    /// The styles, in the order of `ContentStyle`, without repeats.
    public var styles: [ContentStyle]
    public var link: ContentLink?

    public init(content: Content, styles: [ContentStyle] = [], link: ContentLink? = nil) {
        self.content = content
        self.styles = styles
        self.link = link
    }

    /// The text of a text run, or nil for an emoji.
    public var text: String? {
        if case .text(let text) = content { text } else { nil }
    }

    /// The custom emoji of an emoji run, or nil for text.
    public var emoji: CustomEmoji? {
        if case .emoji(let emoji) = content { emoji } else { nil }
    }

    /// What the run reads as: its text, or `:shortcode:` for an emoji.
    public var plainText: String {
        switch content {
        case .text(let text): text
        case .emoji(let emoji): ":\(emoji.shortcode):"
        }
    }
}

extension Array where Element == ContentRun {
    /// What the runs read as, emoji as `:shortcode:`.
    public var plainText: String { map(\.plainText).joined() }
}

extension Array where Element == ContentBlock {
    /// What the blocks read as, one line each.
    public var plainText: String { map(\.runs.plainText).joined(separator: "\n") }
}

/// Turns the content of a post into blocks of runs.
public enum PostContentParser {
    /// The blocks of `html`, the sanitized HTML of a status's `content`, with the custom emoji of `emojis`.
    public static func blocks(html: String, emojis: [CustomEmoji]) -> [ContentBlock] {
        var builder = BlockBuilder(emojis: emojis)
        var tokenizer = HTMLTokenizer(html)
        while let token = tokenizer.next() {
            builder.consume(token)
        }
        builder.flush()
        return builder.blocks
    }

    /// The blocks of `text`, a plain text such as a content warning or a display name, with the custom emoji of
    /// `emojis`: one paragraph, or none when it is blank. Markup characters stay text.
    public static func blocks(text: String, emojis: [CustomEmoji]) -> [ContentBlock] {
        var builder = BlockBuilder(emojis: emojis)
        builder.consume(.text(text))
        builder.flush()
        return builder.blocks
    }

    /// The runs of `text`, a plain text such as a content warning or a display name.
    public static func runs(text: String, emojis: [CustomEmoji]) -> [ContentRun] {
        blocks(text: text, emojis: emojis).flatMap(\.runs)
    }
}

// MARK: - Building blocks from tokens

/// A run before whitespace and emoji are resolved: a piece of text, or a line break.
private struct RawRun {
    var text: String
    var isBreak: Bool
    var styles: [ContentStyle]
    var link: ContentLink?
}

private struct OpenElement {
    var name: String
    var classes: Set<String>
    var href: String?
}

private struct BlockBuilder {
    static let blockElements: Set<String> = [
        "address", "article", "aside", "blockquote", "dd", "details", "div", "dl", "dt", "figcaption", "figure",
        "footer", "h1", "h2", "h3", "h4", "h5", "h6", "header", "hr", "li", "main", "nav", "ol", "p", "pre",
        "section", "summary", "table", "tbody", "td", "tfoot", "th", "thead", "tr", "ul",
    ]
    static let voidElements: Set<String> = [
        "area", "base", "br", "col", "embed", "hr", "img", "input", "link", "meta", "source", "track", "wbr",
    ]
    /// Elements whose content is not text.
    static let hiddenElements: Set<String> = ["script", "style", "template"]

    let emojis: [String: CustomEmoji]
    var blocks: [ContentBlock] = []
    private var stack: [OpenElement] = []
    private var pending: [RawRun] = []
    private var pendingKind: ContentBlock.Kind = .paragraph

    init(emojis: [CustomEmoji]) {
        self.emojis = Dictionary(emojis.map { ($0.shortcode, $0) }, uniquingKeysWith: { first, _ in first })
    }

    mutating func consume(_ token: HTMLToken) {
        switch token {
        case .text(let text):
            append(text: text)
        case .start(let name, let attributes, let selfClosing):
            if name == "br" {
                if !isHidden { appendBreak() }
                return
            }
            if Self.blockElements.contains(name) { flush() }
            if Self.voidElements.contains(name) || selfClosing { return }
            let classes = Set((attributes["class"] ?? "").split(whereSeparator: \.isWhitespace).map(String.init))
            stack.append(OpenElement(name: name, classes: classes, href: attributes["href"]))
        case .end(let name):
            guard let index = stack.lastIndex(where: { $0.name == name }) else { return }
            if Self.blockElements.contains(name) { flush() }
            let closed = stack[index...]
            if !isHidden, closed.contains(where: { $0.name == "span" && $0.classes.contains("ellipsis") }) {
                stack.removeSubrange((index + 1)...)
                append(text: "…")
            }
            stack.removeSubrange(index...)
        }
    }

    private var isHidden: Bool {
        stack.contains {
            ($0.name == "span" && $0.classes.contains("invisible")) || Self.hiddenElements.contains($0.name)
        }
    }

    private var kind: ContentBlock.Kind {
        for element in stack.reversed() {
            switch element.name {
            case "pre": return .code
            case "li": return .listItem
            case "blockquote": return .quote
            default: continue
            }
        }
        return .paragraph
    }

    private var styles: [ContentStyle] {
        var styles = Set<ContentStyle>()
        for element in stack {
            switch element.name {
            case "b", "strong": styles.insert(.strong)
            case "i", "em": styles.insert(.emphasis)
            case "del", "s", "strike": styles.insert(.strikethrough)
            case "u": styles.insert(.underline)
            case "code": styles.insert(.code)
            default: break
            }
        }
        return styles.sorted()
    }

    private var link: ContentLink? {
        guard let anchor = stack.last(where: { $0.name == "a" }), let href = anchor.href else { return nil }
        if anchor.classes.contains("hashtag") { return ContentLink(kind: .hashtag, href: href) }
        if anchor.classes.contains("mention") { return ContentLink(kind: .mention, href: href) }
        return ContentLink(kind: .url, href: href)
    }

    private mutating func startIfEmpty() {
        if pending.isEmpty { pendingKind = kind }
    }

    private mutating func append(text: String) {
        guard !isHidden, !text.isEmpty else { return }
        startIfEmpty()
        pending.append(RawRun(text: text, isBreak: false, styles: styles, link: link))
    }

    private mutating func appendBreak() {
        startIfEmpty()
        pending.append(RawRun(text: "\n", isBreak: true, styles: styles, link: link))
    }

    /// Ends the block being built, if it has any runs.
    mutating func flush() {
        defer { pending = [] }
        let raw = pendingKind == .code ? pending : Self.collapsingWhitespace(pending)
        var runs: [ContentRun] = []
        for piece in raw where !piece.text.isEmpty {
            for run in splitEmoji(piece) {
                if let last = runs.last, case .text(let previous) = last.content, case .text(let next) = run.content,
                    last.styles == run.styles, last.link == run.link
                {
                    runs[runs.count - 1].content = .text(previous + next)
                } else {
                    runs.append(run)
                }
            }
        }
        guard !runs.isEmpty else { return }
        blocks.append(ContentBlock(kind: pendingKind, runs: runs))
    }

    /// Collapses each stretch of whitespace to one space, drops the whitespace at both ends and around a line break.
    private static func collapsingWhitespace(_ runs: [RawRun]) -> [RawRun] {
        var result: [RawRun] = []
        // Whether the text so far ends in whitespace, or is at the start of the block or after a break.
        var atBoundary = true
        for run in runs {
            if run.isBreak {
                trimTrailingSpace(&result)
                result.append(run)
                atBoundary = true
                continue
            }
            var text = ""
            for character in run.text {
                if character.isHTMLWhitespace {
                    if !atBoundary {
                        text.append(" ")
                        atBoundary = true
                    }
                } else {
                    text.append(character)
                    atBoundary = false
                }
            }
            var collapsed = run
            collapsed.text = text
            result.append(collapsed)
        }
        trimTrailingSpace(&result)
        return result
    }

    private static func trimTrailingSpace(_ runs: inout [RawRun]) {
        var index = runs.count - 1
        while index >= 0, !runs[index].isBreak {
            if runs[index].text.isEmpty {
                index -= 1
                continue
            }
            if runs[index].text.hasSuffix(" ") { runs[index].text.removeLast() }
            return
        }
    }

    /// The runs of `run` with each `:shortcode:` of a known emoji made an emoji run.
    private func splitEmoji(_ run: RawRun) -> [ContentRun] {
        let text = run.text
        guard !emojis.isEmpty, !run.isBreak, text.contains(":") else {
            return [ContentRun(content: .text(text), styles: run.styles, link: run.link)]
        }
        var runs: [ContentRun] = []
        var plain = ""
        var index = text.startIndex
        while index < text.endIndex {
            if text[index] == ":",
                let close = text[text.index(after: index)...].firstIndex(of: ":"),
                close > text.index(after: index),
                let emoji = emojis[String(text[text.index(after: index)..<close])]
            {
                if !plain.isEmpty {
                    runs.append(ContentRun(content: .text(plain), styles: run.styles, link: run.link))
                    plain = ""
                }
                runs.append(ContentRun(content: .emoji(emoji), styles: run.styles, link: run.link))
                index = text.index(after: close)
            } else {
                plain.append(text[index])
                index = text.index(after: index)
            }
        }
        if !plain.isEmpty { runs.append(ContentRun(content: .text(plain), styles: run.styles, link: run.link)) }
        return runs
    }
}

extension Character {
    /// The ASCII whitespace of HTML: space, tab, line feed, form feed and carriage return.
    fileprivate var isHTMLWhitespace: Bool {
        self == " " || self == "\t" || self == "\n" || self == "\r" || self == "\u{0C}" || self == "\r\n"
    }
}
