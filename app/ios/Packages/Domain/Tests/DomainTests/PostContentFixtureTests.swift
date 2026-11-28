import Domain
import Foundation
import Testing

/// The sanitized HTML of a post's content, and the plain text of a content warning or a display name, become the blocks
/// of styled and linked runs that both apps show (shared/fixtures/post-content/cases.json).
struct PostContentFixtureTests {
    /// A case of the fixture: the input in its format, its custom emoji, and the blocks a client makes of it.
    struct Case: Decodable, CustomTestStringConvertible, Sendable {
        let name: String
        let format: String
        let input: String
        let emojis: [ContentJSON.Emoji]
        let blocks: [ContentJSON.Block]

        var testDescription: String { name }

        var customEmoji: [CustomEmoji] {
            emojis.map {
                CustomEmoji(shortcode: $0.shortcode, url: URL(string: $0.url)!, staticURL: URL(string: $0.staticURL)!)
            }
        }

        /// What the parser makes of the input in its format.
        var parsed: [ContentBlock] {
            format == "html"
                ? PostContentParser.blocks(html: input, emojis: customEmoji)
                : PostContentParser.blocks(text: input, emojis: customEmoji)
        }
    }

    struct Fixture: Decodable {
        let cases: [Case]
    }

    static let cases: [Case] = (try? SharedFixture.decode(Fixture.self, from: "post-content/cases.json"))?.cases ?? []

    @Test func theFixtureHasCasesOfBothFormats() {
        #expect(!Self.cases.isEmpty, "shared/fixtures/post-content/cases.json did not decode")
        #expect(Set(Self.cases.map(\.format)) == ["html", "text"])
    }

    @Test(arguments: cases)
    func eachCaseGivesTheBlocksOfTheFixture(_ testCase: Case) {
        let blocks = testCase.parsed.map(ContentJSON.Block.init)
        #expect(blocks == testCase.blocks, "\(testCase.name): \(blocks)")
    }

    /// An emoji run carries the emoji of the post's list, so that a client draws its image.
    @Test(arguments: cases.filter { !$0.emojis.isEmpty })
    func anEmojiRunCarriesTheEmojiOfTheList(_ testCase: Case) {
        let byShortcode = Dictionary(uniqueKeysWithValues: testCase.customEmoji.map { ($0.shortcode, $0) })
        for run in testCase.parsed.flatMap(\.runs) {
            guard let emoji = run.emoji else { continue }
            #expect(byShortcode[emoji.shortcode] == emoji, "\(testCase.name): :\(emoji.shortcode):")
        }
    }

    /// A display name and a content warning are plain text: one line of runs, as `runs(text:emojis:)` gives them.
    @Test(arguments: cases.filter { $0.format == "text" })
    func plainTextRunsAreTheRunsOfItsParagraph(_ testCase: Case) {
        let runs = PostContentParser.runs(text: testCase.input, emojis: testCase.customEmoji).map(ContentJSON.Run.init)
        #expect(runs == testCase.blocks.flatMap(\.runs), "\(testCase.name): \(runs)")
    }
}
