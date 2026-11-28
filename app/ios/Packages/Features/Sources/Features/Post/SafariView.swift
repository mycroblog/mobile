import SafariServices
import SwiftUI

/// A web page in the in-app browser. Its Done button calls `finish`, which dismisses it.
struct SafariView: UIViewControllerRepresentable {
    let url: URL
    let finish: () -> Void

    func makeUIViewController(context: Context) -> SFSafariViewController {
        let controller = SFSafariViewController(url: url)
        controller.delegate = context.coordinator
        return controller
    }

    func updateUIViewController(_ controller: SFSafariViewController, context: Context) {
        context.coordinator.finish = finish
    }

    func makeCoordinator() -> Coordinator {
        Coordinator(finish: finish)
    }

    final class Coordinator: NSObject, SFSafariViewControllerDelegate {
        var finish: () -> Void

        init(finish: @escaping () -> Void) {
            self.finish = finish
        }

        func safariViewControllerDidFinish(_ controller: SFSafariViewController) {
            finish()
        }
    }
}

/// A URL that the in-app browser shows.
struct BrowsedURL: Identifiable, Hashable {
    let url: URL
    var id: URL { url }
}

extension View {
    /// Opens the http and https links of the text below in the in-app browser, presented full screen; other links go
    /// to the system.
    func opensLinksInAppBrowser(_ browsed: Binding<BrowsedURL?>) -> some View {
        environment(
            \.openURL,
            OpenURLAction { url in
                guard let scheme = url.scheme?.lowercased(), scheme == "https" || scheme == "http" else {
                    return .systemAction
                }
                browsed.wrappedValue = BrowsedURL(url: url)
                return .handled
            }
        )
        .fullScreenCover(item: browsed) { page in
            SafariView(url: page.url) { browsed.wrappedValue = nil }
                .ignoresSafeArea()
        }
    }
}
