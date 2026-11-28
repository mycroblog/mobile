import API
import Domain
import Foundation
import XCTest

/// The public timelines of a local my, whose seed has 15 local posts, offers the local timeline to everyone and the
/// federated one to signed-in readers only.
final class TimelineIntegrationTests: XCTestCase {
    func testTheInstanceOffersTheLocalTimelineToReaders() async throws {
        let instance = try await APIClient(origin: LocalMy.origin()).instance()

        XCTAssertEqual(instance.timelines, InstanceTimelines(local: .everyone, federated: .signedIn))
        XCTAssertFalse(instance.isSingleUser)
        XCTAssertEqual(instance.publicTimelinesForReaders, [.local])
    }

    func testTheLocalTimelineGivesPostsNewestFirst() async throws {
        let page = try await APIClient(origin: LocalMy.origin()).page(of: .local)

        XCTAssertFalse(page.items.isEmpty, "the local timeline of the local my is empty")
        XCTAssertEqual(page.items.map(\.id), page.entryIDs)
        let dates = page.items.map(\.post.createdAt)
        XCTAssertEqual(dates, dates.sorted(by: >), "the posts are not newest first")
        for item in page.items {
            XCTAssertFalse(item.author.displayName.isEmpty, "the author of \(item.id) has no name")
        }
    }

    /// Paging with a small limit reads every post once, in the order of one page of them all.
    func testOlderPagesFollowTheCursorToTheEnd() async throws {
        let client = try APIClient(origin: LocalMy.origin())
        let all = try await client.page(of: .local, limit: 40)

        var ids: [String] = []
        var cursor: String?
        var pages = 0
        repeat {
            let page = try await client.page(of: .local, cursor: cursor, limit: 5)
            XCTAssertLessThanOrEqual(page.entryIDs.count, 5)
            ids += page.entryIDs
            cursor = page.nextCursor
            pages += 1
        } while cursor != nil && pages < 10

        XCTAssertNil(cursor, "the cursor did not end after \(pages) pages")
        XCTAssertGreaterThan(pages, 1, "a limit of 5 gave one page")
        XCTAssertEqual(ids, all.entryIDs)
        XCTAssertEqual(Set(ids).count, ids.count, "a post came twice")
    }

    /// The seed has a post with a content warning, whose content and media are hidden.
    func testAPostWithAContentWarningIsHidden() async throws {
        let page = try await APIClient(origin: LocalMy.origin()).page(of: .local, limit: 40)

        let warned = try XCTUnwrap(page.items.first { $0.post.contentWarning != nil }, "no post has a content warning")
        XCTAssertTrue(warned.isContentHidden)
        XCTAssertTrue(warned.isMediaHidden)
        XCTAssertFalse(warned.post.contentWarning?.plainText.isEmpty ?? true)
    }

    /// A signed-in-only timeline answers an anonymous reader with 401.
    func testTheFederatedTimelineRefusesAnAnonymousReader() async throws {
        do {
            let page = try await APIClient(origin: LocalMy.origin()).page(of: .federated)
            XCTFail("the federated timeline gave \(page.items.count) posts to an anonymous reader")
        } catch let error as TimelineLoadError {
            guard case .problem(_, 401) = error else {
                return XCTFail("the federated timeline gave \(error), not a problem of status 401")
            }
        }
    }
}
