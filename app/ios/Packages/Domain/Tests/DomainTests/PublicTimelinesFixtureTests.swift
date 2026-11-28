import Domain
import Testing

/// A reader who has not signed in is offered the server-provided timelines whose audience is `everyone`, unless the
/// server is `single_user` (shared/fixtures/public-timelines/cases.json, ADR-0002 §6).
struct PublicTimelinesFixtureTests {
    struct Case: Decodable, CustomTestStringConvertible, Sendable {
        struct Timelines: Decodable, Sendable {
            let local: String
            let federated: String
        }

        let timelines: Timelines
        let singleUser: Bool
        let offered: [String]

        private enum CodingKeys: String, CodingKey {
            case timelines, offered
            case singleUser = "single_user"
        }

        var testDescription: String {
            "local \(timelines.local), federated \(timelines.federated)\(singleUser ? ", single user" : "")"
        }

        var instanceTimelines: InstanceTimelines? {
            guard let local = InstanceAudience(rawValue: timelines.local),
                let federated = InstanceAudience(rawValue: timelines.federated)
            else { return nil }
            return InstanceTimelines(local: local, federated: federated)
        }
    }

    struct Fixture: Decodable {
        let cases: [Case]
    }

    static let cases: [Case] =
        (try? SharedFixture.decode(Fixture.self, from: "public-timelines/cases.json"))?.cases ?? []

    @Test func theFixtureHasCases() {
        #expect(!Self.cases.isEmpty, "shared/fixtures/public-timelines/cases.json did not decode")
    }

    @Test(arguments: cases)
    func eachCaseOffersTheTimelinesOfTheFixture(_ testCase: Case) throws {
        let timelines = try #require(testCase.instanceTimelines, "an audience of the fixture is unknown")

        let offered = PublicTimeline.offeredToReaders(timelines: timelines, isSingleUser: testCase.singleUser)

        #expect(offered.map(\.rawValue) == testCase.offered)
    }

    @Test(arguments: cases)
    func anInstanceOffersTheSameTimelines(_ testCase: Case) throws {
        let timelines = try #require(testCase.instanceTimelines)
        let instance = Instance(name: "a server", timelines: timelines, isSingleUser: testCase.singleUser)

        #expect(instance.publicTimelinesForReaders.map(\.rawValue) == testCase.offered)
    }

    /// An instance that says nothing of its timelines offers none.
    @Test func anInstanceOffersNoneByDefault() {
        #expect(Instance(name: "a server").publicTimelinesForReaders.isEmpty)
    }
}
