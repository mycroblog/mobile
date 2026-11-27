// The `share` op: Safari, showing the URL, opens its share sheet and picks the target app. The share sheet and the
// target's share extension are snapshotted through Safari.
import XCTest

struct Share {
    let driver: Driver
    let safari: XCUIApplication

    func pick(_ target: String) throws {
        try openSheet()
        let sheet = safari.otherElements["ShareSheet.RemoteContainerView"]
        let cells = sheet.cells.matching(identifier: "shareCell")
        guard cells.firstMatch.waitForExistence(timeout: 5) else { throw Driver.Failure("Safari did not open its share sheet") }
        awaitStill(sheet)
        let app = cells.matching(NSPredicate(format: "label == %@", target)).firstMatch
        if app.exists {
            app.tap()
            return
        }
        // The row shows the apps used most; its last cell opens the list of every app.
        cells.element(boundBy: cells.count - 1).tap()
        guard let listed = try? driver.element(["label": target], in: safari, preferring: [.cell], timeout: 3) else {
            throw Driver.Failure("no app \"\(target)\" in the share sheet")
        }
        listed.tap()
    }

    /// A tap while the sheet still slides in lands beside it and closes it.
    private func awaitStill(_ sheet: XCUIElement) {
        var last = sheet.frame
        let deadline = Date().addingTimeInterval(2)
        while Date() < deadline {
            RunLoop.current.run(until: Date().addingTimeInterval(0.1))
            let frame = sheet.frame
            if frame == last { return }
            last = frame
        }
    }

    /// The share button is in Safari's toolbar in a wide window, and in the page menu otherwise.
    private func openSheet() throws {
        let share = safari.buttons["ShareButton"]
        if share.waitForExistence(timeout: 2), share.isHittable {
            share.tap()
            return
        }
        let menu = safari.buttons["MoreMenuButton"]
        guard menu.waitForExistence(timeout: 3) else { throw Driver.Failure("Safari shows no share button") }
        menu.tap()
        guard share.waitForExistence(timeout: 3) else { throw Driver.Failure("Safari's page menu has no share button") }
        share.tap()
    }
}
