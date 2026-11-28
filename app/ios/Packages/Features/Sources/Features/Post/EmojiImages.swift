import Domain
import Foundation
import ImageIO
import Observation
import SwiftUI
import UIKit

/// The images of custom emoji, loaded once at the size they are drawn at, for every post of the app.
///
/// An emoji is drawn from its still image: an image inside Text does not animate, so an animated emoji never moves,
/// whether or not the reader asks to reduce motion.
@Observable
final class EmojiImages {
    static let shared = EmojiImages()

    private struct Key: Hashable {
        let url: URL
        let pixelSize: Int
    }

    private var images: [Key: UIImage] = [:]
    @ObservationIgnored private var requested: Set<Key> = []

    /// The image of `emoji` at `pointSize` points on a display of `scale`, or nil until it is loaded; asking for one
    /// that is not loaded starts loading it.
    func image(of emoji: CustomEmoji, pointSize: CGFloat, scale: CGFloat) -> UIImage? {
        let key = Key(url: emoji.staticURL, pixelSize: max(1, Int((pointSize * scale).rounded(.up))))
        if let image = images[key] { return image }
        if !requested.contains(key) {
            requested.insert(key)
            Task {
                if let image = await Self.load(key.url, pixelSize: key.pixelSize) {
                    images[key] = image
                } else {
                    // Let a later drawing try again.
                    requested.remove(key)
                }
            }
        }
        return nil
    }

    /// The image at `url`, decoded at most `pixelSize` pixels on its longer side, off the main thread.
    @concurrent nonisolated private static func load(_ url: URL, pixelSize: Int) async -> UIImage? {
        guard let (data, _) = try? await URLSession.shared.data(from: url),
            let source = CGImageSourceCreateWithData(data as CFData, nil)
        else { return nil }
        let options: [CFString: Any] = [
            kCGImageSourceCreateThumbnailFromImageAlways: true,
            kCGImageSourceCreateThumbnailWithTransform: true,
            kCGImageSourceThumbnailMaxPixelSize: pixelSize,
        ]
        return CGImageSourceCreateThumbnailAtIndex(source, 0, options as CFDictionary).map { UIImage(cgImage: $0) }
    }
}
