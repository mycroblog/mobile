import Domain
import SwiftUI

/// A post as every list of posts shows it (#41): who boosted it, the author, the time, the content warning, the
/// content, the media and the counts. VoiceOver reads it as one element, whose links and buttons are its actions.
struct PostView: View {
    let item: TimelineItem
    /// Whether the reader opened the content warning.
    let isContentOpen: Bool
    /// Whether the reader showed the hidden media.
    let isMediaShown: Bool
    let toggleContent: () -> Void
    /// Shows the hidden media, or hides them again.
    let toggleMedia: () -> Void

    @Environment(\.openURL) private var openURL
    @Environment(\.dynamicTypeSize) private var dynamicTypeSize
    @ScaledMetric(relativeTo: .body) private var avatarSize: CGFloat = 44

    private var post: Post { item.post }
    private var isContentHidden: Bool { item.isContentHidden && !isContentOpen }
    private var isMediaHidden: Bool { item.isMediaHidden && !isMediaShown }

    /// The avatar beside the post, or above it at the accessibility text sizes, where the text needs the width.
    private var avatarLayout: AnyLayout {
        dynamicTypeSize.isAccessibilitySize
            ? AnyLayout(VStackLayout(alignment: .leading, spacing: 8))
            : AnyLayout(HStackLayout(alignment: .top, spacing: 12))
    }

    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            if let booster = item.boostedBy {
                BoostedByLine(booster: booster)
            }
            avatarLayout {
                Avatar(url: item.author.avatar)
                    .frame(width: avatarSize, height: avatarSize)
                VStack(alignment: .leading, spacing: 6) {
                    header
                    if let warning = post.contentWarning {
                        contentWarning(warning)
                    }
                    if !isContentHidden {
                        if !post.content.isEmpty {
                            PostContentView(blocks: post.content)
                        }
                        if !post.media.isEmpty || post.omittedMediaCount > 0 {
                            MediaGrid(
                                media: post.media,
                                omittedCount: post.omittedMediaCount,
                                isHidden: isMediaHidden,
                                canHide: item.isMediaHidden && isMediaShown,
                                toggle: toggleMedia
                            )
                        }
                        if post.hasUnshownParts, let url = post.url {
                            UnshownPartsLine(url: url)
                        }
                    }
                    PostCountsView(counts: post.counts)
                }
            }
        }
        .padding(.vertical, 12)
        .accessibilityElement(children: .combine)
        // A post is one element whatever it holds: not a button, not an image, not plain text. Its buttons are its
        // actions.
        .accessibilityRemoveTraits([.isButton, .isImage, .isStaticText, .isLink])
        .accessibilityActions {
            if post.contentWarning != nil {
                Button(isContentOpen ? LocalizedStringKey("Hide Content") : "Show Content", action: toggleContent)
            }
            if !isContentHidden, item.isMediaHidden, !post.media.isEmpty {
                Button(isMediaHidden ? LocalizedStringKey("Show Media") : "Hide Media", action: toggleMedia)
            }
            if !isContentHidden, post.hasUnshownParts, let url = post.url {
                Button("Open on the Web") { openURL(url) }
            }
            ForEach(Array(links.enumerated()), id: \.offset) { _, link in
                Button {
                    openURL(link.url)
                } label: {
                    Text(verbatim: link.title)
                }
            }
        }
    }

    private var header: some View {
        ViewThatFits(in: .horizontal) {
            HStack(alignment: .firstTextBaseline, spacing: 6) {
                authorName
                handle
                Spacer(minLength: 0)
                time
            }
            VStack(alignment: .leading, spacing: 2) {
                authorName
                HStack(alignment: .firstTextBaseline, spacing: 6) {
                    handle
                    time
                }
            }
            VStack(alignment: .leading, spacing: 2) {
                authorName
                handle
                time
            }
        }
    }

    private var authorName: some View {
        RunsText(runs: item.author.displayName, textStyle: .headline)
            .font(.headline)
            .lineLimit(dynamicTypeSize.isAccessibilitySize ? 3 : 1)
    }

    @ViewBuilder private var handle: some View {
        if let handle = item.author.handle {
            Text(verbatim: handle)
                .font(.subheadline)
                .foregroundStyle(.secondary)
                .lineLimit(1)
                .truncationMode(.middle)
        }
    }

    private var time: some View {
        HStack(spacing: 4) {
            Text(post.createdAt, format: .relative(presentation: .numeric, unitsStyle: .abbreviated))
            if post.isEdited {
                Text("Edited")
            }
        }
        .font(.subheadline)
        .foregroundStyle(.secondary)
        .lineLimit(1)
    }

    private func contentWarning(_ warning: [ContentRun]) -> some View {
        VStack(alignment: .leading, spacing: 6) {
            Label {
                RunsText(runs: warning)
                    .fontWeight(.semibold)
                    .fixedSize(horizontal: false, vertical: true)
            } icon: {
                Image(systemName: "exclamationmark.triangle")
            }
            .accessibilityElement(children: .ignore)
            .accessibilityLabel(Text("Content warning: \(warning.spokenText)"))
            Button(isContentOpen ? LocalizedStringKey("Hide Content") : "Show Content", action: toggleContent)
                .buttonStyle(.bordered)
                .controlSize(.small)
                .accessibilityHidden(true)
        }
    }

    /// The links of the content the reader can reach, in order, with what they read as.
    private var links: [(title: String, url: URL)] {
        guard !isContentHidden else { return [] }
        var links: [(title: String, url: URL)] = []
        for block in post.content {
            var current: (title: String, link: ContentLink)?
            for run in block.runs {
                if let link = run.link, link == current?.link {
                    current?.title += run.plainText
                    continue
                }
                if let finished = current, let url = finished.link.url { links.append((finished.title, url)) }
                current = run.link.map { (run.plainText, $0) }
            }
            if let finished = current, let url = finished.link.url { links.append((finished.title, url)) }
        }
        return links
    }
}

/// "Boosted by" and who boosted the post, above it.
private struct BoostedByLine: View {
    let booster: Account

    var body: some View {
        Label {
            HStack(spacing: 4) {
                Text("Boosted by")
                RunsText(runs: booster.displayName, textStyle: .subheadline)
            }
            .lineLimit(1)
        } icon: {
            Image(systemName: "arrow.2.squarepath")
                .accessibilityHidden(true)
        }
        .font(.subheadline)
        .foregroundStyle(.secondary)
    }
}

/// The avatar of an account, or a symbol when it has none or while it loads.
private struct Avatar: View {
    let url: URL?

    var body: some View {
        Group {
            if let url {
                AsyncImage(url: url) { phase in
                    if let image = phase.image {
                        image.resizable().scaledToFill()
                    } else {
                        placeholder
                    }
                }
            } else {
                placeholder
            }
        }
        .clipShape(RoundedRectangle(cornerRadius: 8))
        .accessibilityHidden(true)
    }

    private var placeholder: some View {
        Image(systemName: "person.crop.square.fill")
            .resizable()
            .foregroundStyle(.tertiary)
    }
}

/// The counts of replies, boosts and favourites, which a reader who has not signed in can only read. VoiceOver reads
/// each count with its name.
private struct PostCountsView: View {
    let counts: PostCounts

    @Environment(\.dynamicTypeSize) private var dynamicTypeSize

    var body: some View {
        // At the accessibility text sizes the counts go one to a line, so that no number is cut off.
        let layout =
            dynamicTypeSize.isAccessibilitySize
            ? AnyLayout(VStackLayout(alignment: .leading, spacing: 4)) : AnyLayout(HStackLayout(spacing: 20))
        layout {
            count(counts.replies, systemImage: "arrowshape.turn.up.left")
            count(counts.boosts, systemImage: "arrow.2.squarepath")
            count(counts.favourites, systemImage: "star")
        }
        .font(.footnote)
        .foregroundStyle(.secondary)
        .padding(.top, 2)
        .accessibilityElement(children: .ignore)
        .accessibilityLabel(spokenCounts)
    }

    /// The three counts with their names, zeros included, such as "0 replies, 1 boost, 1 favourite".
    private var spokenCounts: Text {
        let replies = Text("^[\(counts.replies) reply](inflect: true)")
        let boosts = Text("^[\(counts.boosts) boost](inflect: true)")
        let favourites = Text("^[\(counts.favourites) favourite](inflect: true)")
        return Text("\(replies), \(boosts), \(favourites)")
    }

    private func count(_ value: Int, systemImage: String) -> some View {
        Label {
            Text(value, format: .number)
        } icon: {
            Image(systemName: systemImage)
        }
        .labelStyle(.titleAndIcon)
        .fixedSize()
    }
}

/// A line that says the post has parts this app does not show yet, and opens the post on the web in the in-app
/// browser.
private struct UnshownPartsLine: View {
    let url: URL

    @Environment(\.openURL) private var openURL

    var body: some View {
        VStack(alignment: .leading, spacing: 4) {
            Label {
                Text("This post has content the app cannot show yet.")
                    .fixedSize(horizontal: false, vertical: true)
            } icon: {
                Image(systemName: "info.circle")
            }
            .font(.subheadline)
            .foregroundStyle(.secondary)
            Button("Open on the Web") { openURL(url) }
                .font(.subheadline)
                .accessibilityHidden(true)
        }
    }
}
