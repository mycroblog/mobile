import AppData
import Domain
import SwiftUI

/// The screen of a public timeline (#42): the posts the store holds, newest first, pulled to refresh, with older
/// pages read when the reader reaches the end, and the reading position kept across launches (ADR-0008 §3).
struct TimelineScreen: View {
    let model: TimelineModel
    let title: LocalizedStringKey

    @State private var position = ScrollPosition(idType: String.self)
    /// Where the rows are, read without redrawing: the top of each visible row in the scroll view, and the offset.
    @State private var geometry = RowGeometry()
    @State private var didRestore = false
    /// The height of what the reader sees of the list, which caps the height of media.
    @State private var visibleHeight: CGFloat = .infinity
    /// The widest the posts run, so that lines stay readable in a wide window.
    @ScaledMetric(relativeTo: .body) private var readableWidth: CGFloat = 640

    var body: some View {
        content
            .navigationTitle(title)
            // An inline title keeps the top of the list in one place, so that the reading position and the scroll
            // to the top that a tap on the tab asks for both land below the bar.
            .navigationBarTitleDisplayMode(.inline)
            .task { await model.run() }
    }

    @ViewBuilder private var content: some View {
        if model.items.isEmpty {
            emptyState
        } else {
            list
        }
    }

    @ViewBuilder private var emptyState: some View {
        switch model.status {
        case .failed(let error, let retryAt):
            ScrollView {
                FailureView(error: error, host: model.context.host, retryAt: retryAt, retry: retry)
                    .containerRelativeFrame(.vertical)
            }
            .refreshable { await model.refresh() }
        case .idle where model.wasLoaded:
            ScrollView {
                ContentUnavailableView {
                    Label("No Posts Yet", systemImage: "text.bubble")
                } description: {
                    Text("Nothing has been posted to this timeline. Pull down to check again.")
                }
                .containerRelativeFrame(.vertical)
            }
            .refreshable { await model.refresh() }
        default:
            ProgressView("Loading posts…")
        }
    }

    private var list: some View {
        ScrollView {
            LazyVStack(alignment: .leading, spacing: 0) {
                if case .failed(let error, let retryAt) = model.status {
                    FailureBanner(error: error, host: model.context.host, retryAt: retryAt, retry: retry)
                }
                ForEach(model.items) { item in
                    PostView(
                        item: item,
                        isContentOpen: model.openedContentWarnings.contains(item.id),
                        isMediaShown: model.shownMedia.contains(item.id),
                        toggleContent: { toggleContent(of: item.id) },
                        toggleMedia: { toggleMedia(of: item.id) }
                    )
                    .padding(.horizontal)
                    .frame(maxWidth: readableWidth)
                    .frame(maxWidth: .infinity)
                    .onGeometryChange(for: CGFloat.self) { proxy in
                        proxy.frame(in: .scrollView).minY
                    } action: { minY in
                        geometry.rowTops[item.id] = minY
                    }
                    // A row the lazy stack let go of has no position any more.
                    .onDisappear { geometry.rowTops[item.id] = nil }
                    Divider()
                }
                if model.hasOlder {
                    olderFooter
                } else {
                    Text("You have reached the oldest post.")
                        .font(.footnote)
                        .foregroundStyle(.secondary)
                        .multilineTextAlignment(.center)
                        .frame(maxWidth: .infinity)
                        .padding()
                }
            }
            .scrollTargetLayout()
        }
        .scrollPosition($position)
        .onScrollGeometryChange(for: ScrollOffset.self) { scroll in
            ScrollOffset(offset: scroll.contentOffset.y + scroll.contentInsets.top, insetTop: scroll.contentInsets.top)
        } action: { _, scroll in
            geometry.contentOffset = scroll.offset
            geometry.insetTop = scroll.insetTop
        }
        .onScrollGeometryChange(for: CGFloat.self) { scroll in
            scroll.containerSize.height - scroll.contentInsets.top - scroll.contentInsets.bottom
        } action: { _, height in
            guard height > 0 else { return }
            visibleHeight = height
        }
        .onScrollGeometryChange(for: CGSize.self) { scroll in
            scroll.containerSize
        } action: { old, new in
            // A rotation, a new window size or a bar that comes and goes lays the rows out again: keep the reader's
            // post where it was.
            if old != new, new.width > 0, new.height > 0 { keepReadingPositionSoon() }
        }
        .environment(\.visibleHeight, visibleHeight)
        .onScrollPhaseChange { _, phase in
            if phase == .idle { settle() }
        }
        .refreshable { await model.refresh() }
        .onAppear {
            // Coming back from another destination keeps the reader's post.
            if didRestore { keepReadingPositionSoon() } else { restorePosition() }
        }
        .onChange(of: model.items.first?.id) { restorePosition() }

        .onDisappear(perform: savePosition)
    }

    @ViewBuilder private var olderFooter: some View {
        if case .loadingOlder = model.status {
            ProgressView()
                .frame(maxWidth: .infinity)
                .padding()
        } else if case .failed = model.status {
            EmptyView()
        } else {
            ProgressView()
                .frame(maxWidth: .infinity)
                .padding()
                .onAppear { Task { await model.loadOlder() } }
        }
    }

    private func toggleContent(of id: String) {
        if model.openedContentWarnings.contains(id) {
            model.openedContentWarnings.remove(id)
        } else {
            model.openedContentWarnings.insert(id)
        }
    }

    private func toggleMedia(of id: String) {
        if model.shownMedia.contains(id) {
            model.shownMedia.remove(id)
        } else {
            model.shownMedia.insert(id)
        }
    }

    private func retry() {
        Task { await model.retry() }
    }

    /// Scrolls to where the reader was, once, when the store has been read: to the top of the saved anchor, then by
    /// the saved offset. When the anchor is gone, the timeline starts at the newest post.
    private func restorePosition() {
        guard !didRestore, model.hasReadStore, !model.items.isEmpty else { return }
        didRestore = true
        guard let saved = model.savedPosition, model.items.contains(where: { $0.id == saved.anchorID }) else { return }
        geometry.anchor = saved
        scroll(to: saved)
    }

    /// When scrolling stops: saves the position, and drops the target of the last scroll the screen asked for, so that
    /// the list showing again does not go back to that old target.
    private func settle() {
        savePosition()
        if geometry.isCorrecting == false {
            position = ScrollPosition(idType: String.self)
        }
    }

    /// Scrolls back to the post the reader was at once the list is shown again and laid out.
    private func keepReadingPositionSoon() {
        Task {
            try? await Task.sleep(for: .milliseconds(150))
            keepReadingPosition()
        }
    }

    /// Scrolls back to the post the reader was at, after the rows were laid out again.
    private func keepReadingPosition() {
        guard didRestore, let anchor = geometry.anchor, model.items.contains(where: { $0.id == anchor.anchorID })
        else { return }
        scroll(to: anchor)
    }

    /// Scrolls to the top of `anchor`'s post, then, once the rows are laid out, measures where it landed and moves by
    /// what is left, a few times, so that its top is the saved offset above the top of what the reader sees.
    private func scroll(to anchor: ReadingPosition) {
        geometry.generation += 1
        let generation = geometry.generation
        geometry.isCorrecting = true
        position.scrollTo(id: anchor.anchorID, anchor: .top)
        Task {
            defer { if generation == geometry.generation { geometry.isCorrecting = false } }
            for _ in 0..<3 {
                try? await Task.sleep(for: .milliseconds(120))
                guard generation == geometry.generation, let top = geometry.rowTops[anchor.anchorID] else { return }
                let correction = (top - geometry.insetTop) + anchor.offset
                guard abs(correction) >= 1 else { return }
                position.scrollTo(y: max(0, geometry.contentOffset + correction))
            }
        }
    }

    /// Saves the post at the top and how far it is scrolled past, or forgets the position at the newest post.
    private func savePosition() {
        // With no row laid out, as when a screen covers the list, there is nothing to measure: keep what was saved.
        guard didRestore, !geometry.rowTops.isEmpty, let anchor = topRowID else { return }
        // A row's top is measured from the top of the scroll view, under the bars; the reader sees from the inset.
        let offset = max(0, geometry.insetTop - (geometry.rowTops[anchor] ?? geometry.insetTop))
        let saved = ReadingPosition(anchorID: anchor, offset: offset)
        geometry.anchor = saved
        if anchor == model.items.first?.id, offset == 0 {
            model.save(position: nil)
        } else {
            model.save(position: saved)
        }
    }

    /// The post at the top of what the reader sees: of the rows laid out, the lowest whose top is at or above it.
    private var topRowID: String? {
        let above = geometry.rowTops.filter { $0.value <= geometry.insetTop + 0.5 }
        return above.max { $0.value < $1.value }?.key ?? model.items.first?.id
    }
}

/// The positions of the rows, kept out of observation so that scrolling redraws nothing.
private final class RowGeometry {
    var rowTops: [String: CGFloat] = [:]
    var contentOffset: CGFloat = 0
    var insetTop: CGFloat = 0
    /// The post the reader was at when the list last stopped, which a new layout scrolls back to.
    var anchor: ReadingPosition?
    /// Counts the scrolls to a post, so that a newer one stops the corrections of an older one.
    var generation = 0
    /// Whether a scroll to a post is still being corrected.
    var isCorrecting = false
}

/// How far a scroll view is scrolled from its top, and the inset of its top under the bars.
private struct ScrollOffset: Equatable {
    var offset: CGFloat
    var insetTop: CGFloat
}

/// What a failure says, and its retry, which waits for the rate limit.
private struct FailureView: View {
    let error: TimelineLoadError
    /// The host of the server, which the failure names.
    let host: String
    let retryAt: Date?
    let retry: () -> Void

    var body: some View {
        ContentUnavailableView {
            Label(error.title, systemImage: "exclamationmark.triangle")
        } description: {
            Text(error.message(host: host))
        } actions: {
            RetryButton(retryAt: retryAt, retry: retry)
                .buttonStyle(.borderedProminent)
        }
    }
}

/// A failure above posts that are already shown.
private struct FailureBanner: View {
    let error: TimelineLoadError
    /// The host of the server, which the failure names.
    let host: String
    let retryAt: Date?
    let retry: () -> Void

    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            Text(error.title)
                .font(.headline)
            Text(error.message(host: host))
                .font(.subheadline)
                .foregroundStyle(.secondary)
            RetryButton(retryAt: retryAt, retry: retry)
                .buttonStyle(.bordered)
        }
        .frame(maxWidth: .infinity, alignment: .leading)
        .padding()
        .background(.fill.quaternary)
        .accessibilityElement(children: .contain)
    }
}

/// "Try Again", disabled with the time left until the rate limit allows it.
private struct RetryButton: View {
    let retryAt: Date?
    let retry: () -> Void

    var body: some View {
        TimelineView(.periodic(from: .now, by: 1)) { context in
            let waiting = retryAt.map { $0 > context.date } ?? false
            Button(action: retry) {
                if waiting, let retryAt {
                    Text("Try Again in \(Text(retryAt, style: .timer))")
                } else {
                    Text("Try Again")
                }
            }
            .disabled(waiting)
        }
    }
}

extension TimelineLoadError {
    fileprivate var title: LocalizedStringKey {
        switch self {
        case .unreachable: "Could Not Load Posts"
        case .notFound: "Timeline Not Available"
        case .rateLimited: "Too Many Requests"
        case .problem, .invalidAnswer: "Could Not Load Posts"
        }
    }

    fileprivate func message(host: String) -> String {
        switch self {
        case .unreachable: String(localized: "Could not reach \(host). Check the address and your connection.")
        case .notFound: String(localized: "This server no longer offers this timeline.")
        case .rateLimited(let seconds?):
            String(localized: "The server asked to wait \(seconds) seconds before trying again.")
        case .rateLimited(nil): String(localized: "The server asked to wait before trying again.")
        case .problem(let title, _): title
        case .invalidAnswer: String(localized: "The server answered with something that is not a timeline.")
        }
    }
}
