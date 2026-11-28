import SafariServices
import SwiftUI
import UIKit

extension View {
    /// Opens the http and https links of the text below in the in-app browser; other links go to the system.
    ///
    /// The browser is presented modally from the view controller on top of the window, as Apple asks for
    /// SFSafariViewController, so that the SwiftUI screens under it are left as they are: a list keeps its place.
    func opensLinksInAppBrowser() -> some View {
        environment(
            \.openURL,
            OpenURLAction { url in
                guard let scheme = url.scheme?.lowercased(), scheme == "https" || scheme == "http",
                    let presenter = UIApplication.shared.topViewController
                else {
                    return .systemAction
                }
                presenter.present(SFSafariViewController(url: url), animated: true)
                return .handled
            }
        )
    }
}

extension UIApplication {
    /// The view controller on top of the key window of the foreground scene, which presents the in-app browser.
    fileprivate var topViewController: UIViewController? {
        let scenes = connectedScenes.compactMap { $0 as? UIWindowScene }
        let scene =
            scenes.first { $0.activationState == .foregroundActive }
            ?? scenes.first { $0.activationState == .foregroundInactive }
        var top = scene?.keyWindow?.rootViewController
        while let presented = top?.presentedViewController, !presented.isBeingDismissed {
            top = presented
        }
        return top
    }
}
