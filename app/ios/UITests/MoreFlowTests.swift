import XCTest

/// On an iPhone, the destinations that do not fit the tab bar open from More (ADR-0009 §2).
@MainActor
final class MoreFlowTests: XCTestCase {
    override func setUp() {
        continueAfterFailure = false
    }

    func testSettingsOpensFromMore() {
        let app = XCUIApplication()
        app.launch()

        app.tabBars.buttons["More"].tap()
        app.buttons["Settings"].tap()

        XCTAssertTrue(
            app.navigationBars["Settings"].waitForExistence(timeout: 5),
            "Settings did not open from More"
        )
        XCTAssertTrue(
            app.staticTexts["Settings"].exists,
            "the placeholder of Settings does not show its title"
        )
    }
}
