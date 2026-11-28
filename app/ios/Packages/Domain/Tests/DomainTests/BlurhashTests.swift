import Domain
import Testing

/// Sensitive media stay hidden behind their blurhash: a hash decodes to opaque RGBA pixels, and a malformed one to
/// nothing, so that a client falls back to a plain placeholder.
struct BlurhashTests {
    /// The blurhash of the image of shared/fixtures/timeline-page.
    static let hash = "LEHV6nWB2yk8pyo0adR*.7kCMdnj"

    @Test func aHashDecodesToOpaquePixelsOfTheSize() throws {
        let pixels = try #require(Blurhash.decode(Self.hash, width: 8, height: 6))

        #expect(pixels.count == 8 * 6 * 4)
        #expect(stride(from: 3, to: pixels.count, by: 4).allSatisfy { pixels[$0] == 255 })
        #expect(Set(pixels).count > 2, "the pixels are all one value")
    }

    @Test func aHashIsValidAndHasAnAverageColor() {
        #expect(Blurhash.isValid(Self.hash))
        #expect(Blurhash.averageColor(of: Self.hash) != nil)
    }

    @Test(arguments: ["", "L", "LEHV6nWB2yk8pyo0adR*.7kCMdn", "LEHV6nWB2yk8pyo0adR*.7kCMdnj!", "{{{{{{"])
    func aMalformedHashDecodesToNothing(_ hash: String) {
        #expect(!Blurhash.isValid(hash))
        #expect(Blurhash.decode(hash, width: 4, height: 4) == nil)
    }
}
