// Throwaway spike for mycroblog/mobile#15. Never merges.
// A share extension that only shows what it received, so a driver can see a share arrive in the tree.
import UIKit
import UniformTypeIdentifiers

final class ShareViewController: UIViewController {
    private let label = UILabel()

    override func viewDidLoad() {
        super.viewDidLoad()
        view.backgroundColor = .systemBackground
        label.numberOfLines = 0
        label.text = "Shared: …"
        label.accessibilityIdentifier = "sharedContent"
        let done = UIButton(type: .system, primaryAction: UIAction(title: "Done") { [weak self] _ in
            self?.extensionContext?.completeRequest(returningItems: nil)
        })
        let stack = UIStackView(arrangedSubviews: [label, done])
        stack.axis = .vertical; stack.spacing = 16; stack.translatesAutoresizingMaskIntoConstraints = false
        view.addSubview(stack)
        NSLayoutConstraint.activate([
            stack.centerYAnchor.constraint(equalTo: view.centerYAnchor),
            stack.leadingAnchor.constraint(equalTo: view.layoutMarginsGuide.leadingAnchor),
            stack.trailingAnchor.constraint(equalTo: view.layoutMarginsGuide.trailingAnchor),
        ])
        let providers = (extensionContext?.inputItems as? [NSExtensionItem])?.flatMap { $0.attachments ?? [] } ?? []
        for p in providers {
            for type in [UTType.url, UTType.plainText] where p.hasItemConformingToTypeIdentifier(type.identifier) {
                p.loadItem(forTypeIdentifier: type.identifier) { item, _ in
                    let text = (item as? URL)?.absoluteString ?? (item as? String) ?? "?"
                    DispatchQueue.main.async { self.label.text = "Shared: \(text)" }
                }
                break
            }
        }
    }
}
