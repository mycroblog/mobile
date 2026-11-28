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

    var body: some View {
        content
            .navigationTitle(title)
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
                FailureView(error: error, retryAt: retryAt, retry: retry)
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
                    FailureBanner(error: error, retryAt: retryAt, retry: retry)
                }
                ForEach(model.items) { item in
                    PostView(
                        item: item,
                        isContentOpen: model.openedContentWarnings.contains(item.id),
                        isMediaShown: model.shownMedia.contains(item.id),
                        toggleContent: { toggleContent(of: item.id) },
                        showMedia: { model.shownMedia.insert(item.id) }
                    )
                    .padding(.horizontal)
                    .onGeometryChange(for: CGFloat.self) { proxy in
                        proxy.frame(in: .scrollView).minY
                    } action: { minY in
                        geometry.rowTops[item.id] = minY
                    }
                    Divider()
                }
                if model.hasOlder {
                    olderFooter
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
        .onScrollPhaseChange { _, phase in
            if phase == .idle { savePosition() }
        }
        .refreshable { await model.refresh() }
        .onAppear(perform: restorePosition)
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

    private func retry() {
        Task { await model.retry() }
    }

    /// Scrolls to where the reader was, once, when the store has been read: to the top of the saved anchor, then by
    /// the saved offset. When the anchor is gone, the timeline starts at the newest post.
    private func restorePosition() {
        guard !didRestore, model.hasReadStore, !model.items.isEmpty else { return }
        didRestore = true
        guard let saved = model.savedPosition, model.items.contains(where: { $0.id == saved.anchorID }) else { return }
        position.scrollTo(id: saved.anchorID, anchor: .top)
        guard saved.offset > 0 else { return }
        Task {
            try? await Task.sleep(for: .milliseconds(100))
            position.scrollTo(y: geometry.contentOffset + saved.offset)
        }
    }

    /// Saves the entry at the top and how far it is scrolled past, or forgets the position at the newest post.
    private func savePosition() {
        guard didRestore, let anchor = position.viewID(type: String.self) ?? topRowID else { return }
        // A row's top is measured from the top of the scroll view, under the bars; the reader sees from the inset.
        let offset = max(0, geometry.insetTop - (geometry.rowTops[anchor] ?? geometry.insetTop))
        if anchor == model.items.first?.id, offset == 0 {
            model.save(position: nil)
        } else {
            model.save(position: ReadingPosition(anchorID: anchor, offset: offset))
        }
    }

    /// The row whose top is the last one at or above the top of what the reader sees.
    private var topRowID: String? {
        model.items.last { (geometry.rowTops[$0.id] ?? .infinity) <= geometry.insetTop }?.id ?? model.items.first?.id
    }
}

/// The positions of the rows, kept out of observation so that scrolling redraws nothing.
private final class RowGeometry {
    var rowTops: [String: CGFloat] = [:]
    var contentOffset: CGFloat = 0
    var insetTop: CGFloat = 0
}

/// How far a scroll view is scrolled from its top, and the inset of its top under the bars.
private struct ScrollOffset: Equatable {
    var offset: CGFloat
    var insetTop: CGFloat
}

/// What a failure says, and its retry, which waits for the rate limit.
private struct FailureView: View {
    let error: TimelineLoadError
    let retryAt: Date?
    let retry: () -> Void

    var body: some View {
        ContentUnavailableView {
            Label(error.title, systemImage: "exclamationmark.triangle")
        } description: {
            Text(error.message)
        } actions: {
            RetryButton(retryAt: retryAt, retry: retry)
                .buttonStyle(.borderedProminent)
        }
    }
}

/// A failure above posts that are already shown.
private struct FailureBanner: View {
    let error: TimelineLoadError
    let retryAt: Date?
    let retry: () -> Void

    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            Text(error.title)
                .font(.headline)
            Text(error.message)
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

    fileprivate var message: String {
        switch self {
        case .unreachable: String(localized: "The server did not answer. Check your connection and try again.")
        case .notFound: String(localized: "This server no longer offers this timeline.")
        case .rateLimited(let seconds?):
            String(localized: "The server asked to wait \(seconds) seconds before trying again.")
        case .rateLimited(nil): String(localized: "The server asked to wait before trying again.")
        case .problem(let title, _): title
        case .invalidAnswer: String(localized: "The server answered with something that is not a timeline.")
        }
    }
}
