import UIKit

/// Runs the configured refresh loop as soon as the share sheet opens it, then completes.
final class ShareViewController: UIViewController {
    override func viewDidLoad() {
        super.viewDidLoad()
        Log.who = "ext"
        view.backgroundColor = .systemBackground
        let label = UILabel(); label.text = "Spike 4 ext running"; label.frame = view.bounds; label.textAlignment = .center
        view.addSubview(label)
        let cfg = RunConfig.load()
        Log.t("ext loaded; keychain token gen=\(Keychain.read()?.gen ?? -1) mode=\(cfg.mode.rawValue)")
        DispatchQueue.global().async {
            Refresher.loop(cfg)
            DispatchQueue.main.async { self.extensionContext?.completeRequest(returningItems: nil) }
        }
    }
}
