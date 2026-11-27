// Throwaway spike for mycroblog/mobile#3. Never merges.
// Launch with `-variant tabs` (TabView .sidebarAdaptable, split view inside each tab)
// or `-variant split3` (three-column NavigationSplitView in regular width, tab bar in compact).
import SwiftUI
import Observation

enum Destination: String, CaseIterable, Hashable, Identifiable {
    case home, local, federated, notifications, search, dms, profile, bookmarks, settings
    var id: String { rawValue }
    var title: String {
        switch self {
        case .home: "Home"; case .local: "Local"; case .federated: "Federated"
        case .notifications: "Notifications"; case .search: "Search"; case .dms: "DMs"
        case .profile: "Profile"; case .bookmarks: "Bookmarks"; case .settings: "Settings"
        }
    }
    var symbol: String {
        switch self {
        case .home: "house"; case .local: "person.2"; case .federated: "globe"
        case .notifications: "bell"; case .search: "magnifyingglass"; case .dms: "envelope"
        case .profile: "person.crop.circle"; case .bookmarks: "bookmark"; case .settings: "gear"
        }
    }
    /// At most five in the tab bar (HIG); the rest only in the sidebar.
    var inTabBar: Bool { [.home, .notifications, .search, .dms, .profile].contains(self) }
    var hasList: Bool { ![.settings, .profile].contains(self) }
}

struct PostID: Hashable, Codable { var dest: String; var n: Int }

/// Navigation state, one per scene (ADR-0009 §3).
@Observable final class SceneNav {
    var tab: Destination = .home
    var selection: [Destination: PostID] = [:]
    var composerPresented = false
    var draft = ""
}

@main struct Spike3App: App {
    var body: some Scene {
        WindowGroup { SceneRoot() }
    }
}

struct SceneRoot: View {
    @State private var nav = SceneNav()
    @State private var sceneID = String(UUID().uuidString.prefix(4))
    @Environment(\.horizontalSizeClass) private var h
    @Environment(\.verticalSizeClass) private var v
    private let variant = UserDefaults.standard.string(forKey: "variant") ?? "tabs"

    var body: some View {
        Group {
            if variant == "split3" { ThreeColumnRoot() } else { TabsRoot() }
        }
        .environment(nav)
        .sheet(isPresented: $nav.composerPresented) { Composer().environment(nav) }
        .overlay(alignment: .bottom) {
            GeometryReader { g in
                Text("\(variant) h:\(h == .regular ? "R" : "C") v:\(v == .regular ? "R" : "C") \(Int(g.size.width))x\(Int(g.size.height)) scene:\(sceneID) tab:\(nav.tab.rawValue) sel:\(nav.selection[nav.tab].map { String($0.n) } ?? "-")")
                    .font(.caption2.monospaced()).padding(4)
                    .background(.yellow.opacity(0.85))
                    .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .bottom)
                    .padding(.bottom, 90)
            }
            .allowsHitTesting(false)
        }
    }
}

// MARK: Variant A: OS tab container, split inside each tab

struct TabsRoot: View {
    @Environment(SceneNav.self) private var nav
    var body: some View {
        @Bindable var nav = nav
        TabView(selection: $nav.tab) {
            ForEach(Destination.allCases) { d in
                Tab(d.title, systemImage: d.symbol, value: d) {
                    if d.hasList { SplitInTab(dest: d) } else { NavigationStack { Leaf(dest: d) } }
                }
                .defaultVisibility(d.inTabBar ? .automatic : .hidden, for: .tabBar)
            }
        }
        .tabViewStyle(.sidebarAdaptable)
        // `-sidebar 1`: ask for the sidebar where the system allows it (Duo inner display is opt-in).
        .defaultTabBarPlacement(UserDefaults.standard.bool(forKey: "sidebar") ? .sidebar : .automatic)
    }
}

struct SplitInTab: View {
    let dest: Destination
    @Environment(SceneNav.self) private var nav
    var body: some View {
        NavigationSplitView {
            Timeline(dest: dest)
        } detail: {
            if let id = nav.selection[dest] { Detail(id: id) } else { Text("Select a post") }
        }
    }
}

// MARK: Variant B: three-column split at the top in regular, tab bar in compact

struct ThreeColumnRoot: View {
    @Environment(SceneNav.self) private var nav
    @Environment(\.horizontalSizeClass) private var h
    var body: some View {
        @Bindable var nav = nav
        if h == .regular {
            NavigationSplitView {
                List(Destination.allCases, selection: Binding(get: { nav.tab }, set: { if let t = $0 { nav.tab = t } })) { d in
                    Label(d.title, systemImage: d.symbol).tag(d)
                }
                .navigationTitle("mycroblog")
            } content: {
                if nav.tab.hasList { Timeline(dest: nav.tab) } else { Leaf(dest: nav.tab) }
            } detail: {
                if let id = nav.selection[nav.tab] { Detail(id: id) } else { Text("Select a post") }
            }
        } else {
            TabView(selection: $nav.tab) {
                ForEach(Destination.allCases) { d in
                    Tab(d.title, systemImage: d.symbol, value: d) { CompactStack(dest: d) }
                        .defaultVisibility(d.inTabBar ? .automatic : .hidden, for: .tabBar)
                }
            }
        }
    }
}

/// Compact: the selection of the model becomes the stack (ADR-0009 §3).
struct CompactStack: View {
    let dest: Destination
    @Environment(SceneNav.self) private var nav
    var body: some View {
        let path = Binding<[PostID]>(
            get: { nav.selection[dest].map { [$0] } ?? [] },
            set: { nav.selection[dest] = $0.last })
        NavigationStack(path: path) {
            Group { if dest.hasList { Timeline(dest: dest) } else { Leaf(dest: dest) } }
                .navigationDestination(for: PostID.self) { Detail(id: $0) }
        }
    }
}

// MARK: Shared screens

struct Timeline: View {
    let dest: Destination
    @Environment(SceneNav.self) private var nav
    @State private var plainTapCount = 0  // plain view state, to see whether it survives
    var body: some View {
        let sel = Binding<PostID?>(get: { nav.selection[dest] }, set: { nav.selection[dest] = $0 })
        List(0..<200, id: \.self, selection: sel) { n in
            NavigationLink(value: PostID(dest: dest.rawValue, n: n)) {
                VStack(alignment: .leading) {
                    Text("@user\(n % 17) · \(n)m").font(.caption).foregroundStyle(.secondary)
                    Text("\(dest.title) post #\(n): lorem ipsum dolor sit amet, consectetur adipiscing elit.")
                }
            }
        }
        .navigationTitle(dest.title)
        .toolbar {
            ToolbarItem(placement: .topBarLeading) {
                Button("Plain \(plainTapCount)", systemImage: "plus.circle") { plainTapCount += 1 }
            }
            ToolbarItem(placement: .primaryAction) {
                Button("Compose", systemImage: "square.and.pencil") { nav.composerPresented = true }
            }
        }
    }
}

struct Detail: View {
    let id: PostID
    @State private var plainLiked = false
    var body: some View {
        ScrollView {
            VStack(alignment: .leading, spacing: 12) {
                Text("Post #\(id.n) in \(id.dest)").font(.title.bold())
                Toggle("Plain @State liked", isOn: $plainLiked)
                ForEach(0..<40, id: \.self) { i in Text("Reply \(i) to #\(id.n)") }
            }.padding()
        }
        .navigationTitle("#\(id.n)")
        .toolbar {
            ToolbarItem(placement: .bottomBar) { Button("Reply", systemImage: "arrowshape.turn.up.left") {} }
            ToolbarItem(placement: .bottomBar) { Button("Boost", systemImage: "arrow.2.squarepath") {} }
        }
    }
}

struct Leaf: View {
    let dest: Destination
    var body: some View { Text(dest.title).navigationTitle(dest.title) }
}

struct Composer: View {
    @Environment(SceneNav.self) private var nav
    @State private var plainText = ""
    var body: some View {
        @Bindable var nav = nav
        NavigationStack {
            Form {
                Section("Model draft") { TextField("Model-held text", text: $nav.draft, axis: .vertical) }
                Section("Plain @State") { TextField("Plain text", text: $plainText) }
                // Fills both fields without the keyboard, so IME quirks in the simulator do not matter.
                Button("Fill both") {
                    let stamp = Int(Date().timeIntervalSince1970) % 10000
                    nav.draft += "model-\(stamp) "
                    plainText += "plain-\(stamp) "
                }
            }
            .navigationTitle("New post")
            .toolbar {
                ToolbarItem(placement: .cancellationAction) { Button("Cancel", systemImage: "xmark") { nav.composerPresented = false } }
                ToolbarItem(placement: .confirmationAction) { Button("Post", systemImage: "paperplane") { nav.composerPresented = false } }
            }
        }
    }
}
