import Domain
import SwiftUI

/// Runs of a post's text as one Text: styles and links as attributes, custom emoji as images at the text's size.
/// A link opens through the `openURL` of the environment.
struct RunsText: View {
    let runs: [ContentRun]
    var textStyle: Font.TextStyle = .body

    @Environment(\.displayScale) private var displayScale
    @ScaledMetric private var emojiSize: CGFloat

    init(runs: [ContentRun], textStyle: Font.TextStyle = .body) {
        self.runs = runs
        self.textStyle = textStyle
        _emojiSize = ScaledMetric(wrappedValue: Self.emojiPointSize(for: textStyle), relativeTo: textStyle)
    }

    var body: some View {
        let text = Self.text(of: runs, emoji: emojiText)
        // VoiceOver skips the label of an image inside Text; with emoji, the text reads as its plain form, each emoji
        // as `:shortcode:`.
        if runs.contains(where: { $0.emoji != nil }) {
            text.accessibilityLabel(Text(verbatim: runs.plainText))
        } else {
            text
        }
    }

    /// The size of an emoji at the default Dynamic Type size: about the line height of the style.
    private static func emojiPointSize(for style: Font.TextStyle) -> CGFloat {
        switch style {
        case .largeTitle: 36
        case .title: 30
        case .title2: 24
        case .title3: 22
        case .headline, .body: 20
        case .callout: 19
        case .subheadline: 18
        case .footnote: 16
        case .caption, .caption2: 14
        @unknown default: 20
        }
    }

    private func emojiText(_ emoji: CustomEmoji) -> Text {
        let label = Text(verbatim: ":\(emoji.shortcode):")
        guard let image = EmojiImages.shared.image(of: emoji, pointSize: emojiSize, scale: displayScale),
            let cgImage = image.cgImage
        else { return label }
        let drawn = Image(cgImage, scale: displayScale, label: label)
        // An emoji sits on the baseline like a capital letter; lower it by about the descent.
        return Text("\(drawn)").baselineOffset(-emojiSize * 0.2)
    }

    /// The text of `runs`, drawing each emoji with `emoji`.
    static func text(of runs: [ContentRun], emoji: (CustomEmoji) -> Text) -> Text {
        var pieces: [Text] = []
        var pending = AttributedString()
        for run in runs {
            switch run.content {
            case .text(let text):
                pending += attributed(text, run: run)
            case .emoji(let custom):
                if !pending.characters.isEmpty {
                    pieces.append(Text(pending))
                    pending = AttributedString()
                }
                pieces.append(emoji(custom))
            }
        }
        if !pending.characters.isEmpty || pieces.isEmpty { pieces.append(Text(pending)) }
        guard var result = pieces.first else { return Text(verbatim: "") }
        for piece in pieces.dropFirst() {
            result = Text("\(result)\(piece)")
        }
        return result
    }

    private static func attributed(_ text: String, run: ContentRun) -> AttributedString {
        var string = AttributedString(text)
        var intent: InlinePresentationIntent = []
        for style in run.styles {
            switch style {
            case .strong: intent.insert(.stronglyEmphasized)
            case .emphasis: intent.insert(.emphasized)
            case .strikethrough: intent.insert(.strikethrough)
            case .code: intent.insert(.code)
            case .underline: string.swiftUI.underlineStyle = Text.LineStyle.single
            }
        }
        if !intent.isEmpty { string.inlinePresentationIntent = intent }
        if let url = run.link?.url { string.link = url }
        return string
    }
}

/// The blocks of a post's content: paragraphs, quotes, code blocks and list items.
struct PostContentView: View {
    let blocks: [ContentBlock]

    @ScaledMetric(relativeTo: .body) private var quoteBarWidth: CGFloat = 3
    @ScaledMetric(relativeTo: .body) private var codePadding: CGFloat = 8

    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            ForEach(Array(blocks.enumerated()), id: \.offset) { _, block in
                switch block.kind {
                case .paragraph:
                    RunsText(runs: block.runs)
                case .quote:
                    HStack(alignment: .top, spacing: 8) {
                        RoundedRectangle(cornerRadius: quoteBarWidth / 2)
                            .fill(.tertiary)
                            .frame(width: quoteBarWidth)
                            .accessibilityHidden(true)
                        RunsText(runs: block.runs)
                            .foregroundStyle(.secondary)
                    }
                    .fixedSize(horizontal: false, vertical: true)
                case .code:
                    RunsText(runs: block.runs)
                        .font(.body.monospaced())
                        .padding(codePadding)
                        .frame(maxWidth: .infinity, alignment: .leading)
                        .background(.fill.tertiary, in: RoundedRectangle(cornerRadius: 6))
                case .listItem:
                    HStack(alignment: .firstTextBaseline, spacing: 6) {
                        Text(verbatim: "•")
                            .accessibilityHidden(true)
                        RunsText(runs: block.runs)
                    }
                }
            }
        }
        .frame(maxWidth: .infinity, alignment: .leading)
    }
}
