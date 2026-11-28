import Domain
import SwiftUI
import UIKit

/// The media of a post as previews in a grid, behind their blurhash while they are hidden, and the number of media
/// the server left out. Video and gifv show their preview image only: nothing plays in a timeline.
struct MediaGrid: View {
    let media: [MediaAttachment]
    let omittedCount: Int
    let isHidden: Bool
    let show: () -> Void

    @ScaledMetric(relativeTo: .body) private var tileHeight: CGFloat = 160

    var body: some View {
        VStack(alignment: .leading, spacing: 6) {
            ZStack {
                Grid(horizontalSpacing: 4, verticalSpacing: 4) {
                    ForEach(rows.indices, id: \.self) { row in
                        GridRow {
                            ForEach(rows[row]) { attachment in
                                MediaTile(attachment: attachment, isHidden: isHidden)
                                    .frame(maxWidth: .infinity)
                                    .frame(height: media.count == 1 ? tileHeight * 1.4 : tileHeight)
                                    .clipShape(RoundedRectangle(cornerRadius: 8))
                            }
                        }
                    }
                }
                if isHidden {
                    Button(action: show) {
                        Label("Show Media", systemImage: "eye")
                            .padding(.horizontal, 12)
                            .padding(.vertical, 8)
                    }
                    .buttonStyle(.bordered)
                    .background(.regularMaterial, in: Capsule())
                }
            }
            if omittedCount > 0 {
                Text("\(omittedCount) more attachments not shown")
                    .font(.footnote)
                    .foregroundStyle(.secondary)
            }
        }
    }

    /// The media two to a row.
    private var rows: [[MediaAttachment]] {
        stride(from: 0, to: media.count, by: 2).map { Array(media[$0..<min($0 + 2, media.count)]) }
    }
}

/// One preview: the image, its blurhash while it loads or while it is hidden, and a badge for its kind.
private struct MediaTile: View {
    let attachment: MediaAttachment
    let isHidden: Bool

    var body: some View {
        // The tile takes the size it is given; the image fills it in an overlay, so its own size never widens the tile.
        Color.clear
            .frame(maxWidth: .infinity, maxHeight: .infinity)
            .overlay { placeholder }
            .overlay {
                if !isHidden, let preview = attachment.preview {
                    AsyncImage(url: preview) { phase in
                        if let image = phase.image {
                            image.resizable().scaledToFill()
                        }
                    }
                }
            }
            .overlay {
                if !isHidden, let badge {
                    Image(systemName: badge)
                        .font(.title2)
                        .foregroundStyle(.white)
                        .shadow(radius: 2)
                        .accessibilityHidden(true)
                }
            }
            .clipped()
            .accessibilityElement(children: .ignore)
            .accessibilityLabel(accessibilityLabel)
            .accessibilityAddTraits(.isImage)
    }

    @ViewBuilder private var placeholder: some View {
        if let blurhash = attachment.blurhash {
            BlurhashImage(hash: blurhash)
        } else {
            Rectangle().fill(.fill.secondary)
        }
    }

    private var badge: String? {
        switch attachment.kind {
        case .image: nil
        case .gifv: "play.square"
        case .video: "play.circle.fill"
        case .audio: "waveform"
        case .unknown: "doc"
        }
    }

    private var accessibilityLabel: Text {
        let kind: Text =
            switch attachment.kind {
            case .image: Text("Image")
            case .gifv: Text("Animation")
            case .video: Text("Video")
            case .audio: Text("Audio")
            case .unknown: Text("Attachment")
            }
        if isHidden { return Text("\(kind), hidden") }
        guard let description = attachment.description else { return kind }
        return Text("\(kind): \(description)")
    }
}

/// A blurhash, decoded off the main thread at a small size and stretched over its container.
struct BlurhashImage: View {
    let hash: String

    @State private var image: UIImage?

    var body: some View {
        ZStack {
            if let image {
                Image(uiImage: image).resizable()
            } else {
                Rectangle().fill(.fill.secondary)
            }
        }
        .task(id: hash) { image = await Self.decode(hash) }
        .accessibilityHidden(true)
    }

    @concurrent nonisolated private static func decode(_ hash: String) async -> UIImage? {
        let width = 32
        let height = 32
        guard let pixels = Blurhash.decode(hash, width: width, height: height),
            let provider = CGDataProvider(data: Data(pixels) as CFData),
            let cgImage = CGImage(
                width: width,
                height: height,
                bitsPerComponent: 8,
                bitsPerPixel: 32,
                bytesPerRow: width * 4,
                space: CGColorSpaceCreateDeviceRGB(),
                bitmapInfo: CGBitmapInfo(rawValue: CGImageAlphaInfo.noneSkipLast.rawValue),
                provider: provider,
                decode: nil,
                shouldInterpolate: true,
                intent: .defaultIntent
            )
        else { return nil }
        return UIImage(cgImage: cgImage)
    }
}
