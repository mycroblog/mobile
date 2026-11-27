// The windows of iPadOS windowed apps. Springboard shows each window as a card holding its resize grabber; the
// frames of an app in a window are relative to the window.
import XCTest

struct Windows {
    let driver: Driver

    /// The card of the app's window, when the app is in a window.
    private func card(of id: String) -> XCUIElement? {
        guard UIDevice.current.userInterfaceIdiom == .pad else { return nil }
        let card = driver.springboard.otherElements.matching(
            NSPredicate(format: "identifier BEGINSWITH %@", "card:\(id):")
        )
        .firstMatch
        return card.exists ? card : nil
    }

    /// Where the app's window is on the screen: zero for an app that fills it.
    func origin(of app: XCUIApplication) -> CGPoint {
        guard let id = driver.id(of: app), let card = card(of: id) else { return .zero }
        return card.frame.origin
    }

    /// Drags the window's resize grabber until the window has `size`, as near as the app lets it.
    func resize(_ id: String, to size: CGSize) throws {
        guard let card = card(of: id) else {
            throw Driver.Failure("\(id) is not in a window: turn on Windowed Apps in Settings, Multitasking & Gestures")
        }
        let grabber = card.descendants(matching: .any)["resize-grabber"]
        guard grabber.waitForExistence(timeout: 2) else { throw Driver.Failure("\(id)'s window has no resize grabber") }
        let screen = driver.springboard.coordinate(withNormalizedOffset: .zero)
        // A window kept in the middle grows on both sides, by twice the drag; the gain is measured after each drag.
        var gain = CGVector(dx: 2, dy: 2)
        for _ in 0..<4 {
            let frame = card.frame
            let dx = size.width - frame.width
            let dy = size.height - frame.height
            if abs(dx) < 2 && abs(dy) < 2 { return }
            let drag = CGVector(dx: dx / gain.dx, dy: dy / gain.dy)
            let from = grabber.frame
            let to = screen.withOffset(CGVector(dx: from.midX + drag.dx, dy: from.midY + drag.dy))
            screen.withOffset(CGVector(dx: from.midX, dy: from.midY)).press(forDuration: 0.5, thenDragTo: to)
            RunLoop.current.run(until: Date().addingTimeInterval(0.5))
            let now = card.frame
            if abs(drag.dx) > 10, case let g = (now.width - frame.width) / drag.dx, g > 0.5 { gain.dx = g }
            if abs(drag.dy) > 10, case let g = (now.height - frame.height) / drag.dy, g > 0.5 { gain.dy = g }
        }
    }
}
