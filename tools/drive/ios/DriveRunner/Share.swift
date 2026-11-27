// The `share` op: in the share sheet the source app shows, pick the target app. The share sheet and the target's
// share extension are snapshotted through the source app.
import XCTest

struct Share {
    let driver: Driver
    let source: XCUIApplication

    func pick(_ target: String) throws {
        let sheet = source.otherElements["ShareSheet.RemoteContainerView"]
        let cells = sheet.cells.matching(identifier: "shareCell")
        guard cells.firstMatch.waitForExistence(timeout: 5) else { throw Driver.Failure("the share sheet did not open") }
        awaitStill(sheet)
        let app = cells.matching(NSPredicate(format: "label == %@", target)).firstMatch
        if app.exists {
            // The target's extension replaces the sheet a moment after the tap; until then the screen looks settled.
            // A tap while the sheet is still settling can be dropped, so tap again while the sheet stays.
            for _ in 0..<3 {
                app.tap()
                let deadline = Date().addingTimeInterval(5)
                while Date() < deadline {
                    RunLoop.current.run(until: Date().addingTimeInterval(0.2))
                    if !Self.showsSheet(source) { return }
                }
            }
            throw Driver.Failure("the share sheet kept showing after tapping \"\(target)\"")
        }
        // The row shows the apps used most; its last cell opens the list of every app.
        cells.element(boundBy: cells.count - 1).tap()
        guard let listed = try? driver.element(["label": target], in: source, preferring: [.cell], timeout: 3) else {
            throw Driver.Failure("no app \"\(target)\" in the share sheet")
        }
        listed.tap()
    }

    /// Whether the app's snapshot still holds the share sheet: a query keeps finding it under the extension.
    private static func showsSheet(_ app: XCUIElement) -> Bool {
        guard let snapshot = try? app.snapshot() else { return false }
        func find(_ s: XCUIElementSnapshot) -> Bool {
            s.identifier == "ShareSheet.RemoteContainerView" || s.children.contains(where: find)
        }
        return find(snapshot)
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
}
