// The host app of the DriveRunner UI tests. XCUITest needs a target app to start its runner; the runner drives other
// apps by bundle id. Launched with `-DriveShare <text>`, it opens the system share sheet for that text (a URL when it
// is one), which is where the runner's `share` op starts.
import SwiftUI
import UIKit

@main
struct DriveHostApp: App {
    var body: some Scene {
        WindowGroup {
            ShareOnLaunch(item: UserDefaults.standard.string(forKey: "DriveShare"))
        }
    }
}

private struct ShareOnLaunch: UIViewControllerRepresentable {
    let item: String?

    func makeUIViewController(context: Context) -> Presenter { Presenter(item: item) }
    func updateUIViewController(_ controller: Presenter, context: Context) {}

    final class Presenter: UIViewController {
        private let item: String?
        private var presented = false

        init(item: String?) {
            self.item = item
            super.init(nibName: nil, bundle: nil)
        }

        required init?(coder: NSCoder) { nil }

        override func viewDidAppear(_ animated: Bool) {
            super.viewDidAppear(animated)
            guard let item, !presented else { return }
            presented = true
            let content: Any = URL(string: item).flatMap { $0.scheme == nil ? nil : $0 } ?? item
            let sheet = UIActivityViewController(activityItems: [content], applicationActivities: nil)
            sheet.popoverPresentationController?.sourceView = view
            sheet.popoverPresentationController?.sourceRect = CGRect(x: view.bounds.midX, y: view.bounds.midY, width: 1, height: 1)
            present(sheet, animated: true)
        }
    }
}
