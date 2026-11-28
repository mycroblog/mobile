import Foundation
import XCTest

/// A reader who has not signed in chooses the local my, reads its Local timeline, opens and closes a content warning,
/// and finds the same server and timeline after a relaunch (#42). The local my of `task ios:test-flow` is named in
/// MY_URL; its seed offers the local timeline to everyone and has a post with a content warning.
@MainActor
final class PublicTimelineFlowTests: XCTestCase {
    override func setUp() {
        continueAfterFailure = false
    }

    /// A post of the local timeline as my gives it: its content warning, and its content as plain text.
    struct Post {
        let spoiler: String
        let text: String
    }

    /// The origin of the local my.
    func myURL() throws -> URL {
        let value = try XCTUnwrap(
            ProcessInfo.processInfo.environment["MY_URL"],
            "MY_URL is not set: run these tests with task ios:test-flow, which starts a local my and passes its URL"
        )
        return try XCTUnwrap(URL(string: value))
    }

    /// The posts of the local timeline of the local my, newest first.
    func localPosts() async throws -> [Post] {
        let url = try myURL().appending(path: "api/v1/timelines/local").appending(queryItems: [
            URLQueryItem(name: "limit", value: "40")
        ])
        let (data, _) = try await URLSession.shared.data(from: url)
        let page = try XCTUnwrap(try JSONSerialization.jsonObject(with: data) as? [String: Any])
        let items = try XCTUnwrap(page["items"] as? [[String: Any]])
        return items.map { item in
            Post(spoiler: item["spoiler_text"] as? String ?? "", text: Self.plainText(item["content"] as? String ?? ""))
        }
    }

    /// The text of sanitized HTML, close enough to find a post on the screen by its first words.
    static func plainText(_ html: String) -> String {
        html.replacingOccurrences(of: "<[^>]+>", with: " ", options: .regularExpression)
            .replacingOccurrences(of: "&#39;", with: "'")
            .replacingOccurrences(of: "&quot;", with: "\"")
            .replacingOccurrences(of: "&lt;", with: "<")
            .replacingOccurrences(of: "&gt;", with: ">")
            .replacingOccurrences(of: "&amp;", with: "&")
            .split(whereSeparator: \.isWhitespace).joined(separator: " ")
    }

    /// The first words of `text`, which a post's element shows whatever its width.
    static func start(of text: String) -> String {
        String(text.split(separator: " ").prefix(3).joined(separator: " ").prefix(24))
    }

    /// An element of the app whose label contains `text`.
    func element(containing text: String, in app: XCUIApplication) -> XCUIElement {
        app.descendants(matching: .any).matching(NSPredicate(format: "label CONTAINS %@", text)).firstMatch
    }

    /// Scrolls the timeline down until `element` shows in the upper part of the screen, clear of the tab bar, at most
    /// `swipes` times.
    func scroll(to element: XCUIElement, in app: XCUIApplication, swipes: Int = 12) -> Bool {
        for _ in 0..<swipes {
            if element.exists && element.isHittable && element.frame.minY < app.frame.height * 0.6 { return true }
            // A drag that stops before it lifts scrolls a third of the screen, with no momentum to pass the element.
            let from = app.coordinate(withNormalizedOffset: CGVector(dx: 0.5, dy: 0.7))
            let to = app.coordinate(withNormalizedOffset: CGVector(dx: 0.5, dy: 0.35))
            from.press(forDuration: 0.1, thenDragTo: to, withVelocity: .slow, thenHoldForDuration: 0.3)
        }
        return element.exists
    }

    /// Opens the server screen, from Home before a server is chosen, else from Settings.
    func openTheServerScreen(in app: XCUIApplication) {
        let choose = app.buttons["Choose a Server"]
        if choose.waitForExistence(timeout: 5) {
            choose.tap()
            return
        }
        openDestination("Settings", in: app)
        let change = app.buttons["Change Server"]
        XCTAssertTrue(change.waitForExistence(timeout: 5), "Settings has no Change Server")
        change.tap()
    }

    /// Opens the destination `name` from the tab bar, or from More when it does not fit there.
    func openDestination(_ name: String, in app: XCUIApplication) {
        let tab = app.tabBars.buttons[name]
        if tab.waitForExistence(timeout: 3) {
            tab.tap()
            return
        }
        app.tabBars.buttons["More"].tap()
        // A relaunch restores the destination last opened from More: go back to its list.
        let back = app.navigationBars.buttons["BackButton"]
        if back.waitForExistence(timeout: 2) { back.tap() }
        let row = app.buttons[name]
        XCTAssertTrue(row.waitForExistence(timeout: 5), "neither the tab bar nor More has \(name)")
        row.tap()
    }

    func testReadTheLocalTimelineOfTheLocalMyAndFindItAgainAfterARelaunch() async throws {
        let posts = try await localPosts()
        let newest = try XCTUnwrap(posts.first, "the local timeline of the local my is empty")
        let warned = posts.first { !$0.spoiler.isEmpty }
        let app = XCUIApplication()
        app.launch()

        openTheServerScreen(in: app)
        let address = app.textFields.firstMatch
        XCTAssertTrue(address.waitForExistence(timeout: 5), "the server screen has no address field")
        address.tap()
        address.typeText(try myURL().absoluteString)
        app.buttons["Look up"].tap()
        let read = app.buttons["Read This Server"]
        XCTAssertTrue(read.waitForExistence(timeout: 15), "the local my was not found")
        read.tap()

        openDestination("Local", in: app)
        // The timeline comes back where the reader of an earlier run left it: go up to the newest post.
        let newestPost = element(containing: Self.start(of: newest.text), in: app)
        for _ in 0..<10 where !newestPost.waitForExistence(timeout: 3) {
            app.swipeDown()
        }
        XCTAssertTrue(
            newestPost.waitForExistence(timeout: 15),
            "the Local timeline does not show its newest post \"\(newest.text)\""
        )

        var anchor = newest
        if let warned {
            anchor = warned
            // A post is one element, whose label reads all of it (#41).
            let post = app.otherElements.matching(NSPredicate(format: "label CONTAINS %@", warned.spoiler)).firstMatch
            XCTAssertTrue(scroll(to: post, in: app), "the post warning \"\(warned.spoiler)\" is missing")
            let content = element(containing: Self.start(of: warned.text), in: app)
            // The reader's choice is kept across launches (ADR-0008 §2): a warning an earlier run left open is closed
            // first.
            let leftOpen = post.buttons["Hide Content"]
            if leftOpen.exists {
                leftOpen.tap()
                XCTAssertTrue(content.waitForNonExistence(timeout: 5), "an open content warning did not close")
            }
            XCTAssertFalse(content.exists, "the content under the warning shows before it is opened")

            let show = post.buttons["Show Content"]
            XCTAssertTrue(show.waitForExistence(timeout: 3), "the content warning has no Show Content")
            show.tap()
            XCTAssertTrue(content.waitForExistence(timeout: 5), "opening the content warning did not show its content")

            let hide = post.buttons["Hide Content"]
            XCTAssertTrue(hide.waitForExistence(timeout: 3), "the opened content warning has no Hide Content")
            hide.tap()
            XCTAssertTrue(
                content.waitForNonExistence(timeout: 5),
                "closing the content warning did not hide its content"
            )
        } else {
            print("the local my has no post with a content warning: opening one is not covered")
        }

        // The system ends an app only once it is suspended in the background, after it saved its state.
        XCUIDevice.shared.press(.home)
        _ = app.wait(for: .runningBackgroundSuspended, timeout: 10)
        app.terminate()
        app.launch()

        XCTAssertTrue(app.tabBars.buttons["Local"].waitForExistence(timeout: 10), "a relaunch forgot the server")
        XCTAssertFalse(app.buttons["Choose a Server"].exists, "a relaunch asks for a server again")
        XCTAssertTrue(app.navigationBars["Local"].waitForExistence(timeout: 10), "a relaunch did not open Local")
        let mark = anchor.spoiler.isEmpty ? Self.start(of: anchor.text) : anchor.spoiler
        XCTAssertTrue(
            element(containing: mark, in: app).waitForExistence(timeout: 10),
            "after a relaunch the Local timeline is not where it was: \"\(mark)\" is not shown"
        )
    }
}
