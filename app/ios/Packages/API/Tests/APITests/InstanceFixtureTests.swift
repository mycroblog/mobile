import GeneratedAPI
import Testing

/// The answer of `GET /api/v1/instance` decodes with the generated types into what both apps take from it.
struct InstanceFixtureTests {
    /// What a client takes from the answer: shared/fixtures/instance/expected.json.
    struct Expected: Decodable {
        let name: String
        let shortDescription: String

        enum CodingKeys: String, CodingKey {
            case name
            case shortDescription = "short_description"
        }
    }

    @Test func decodesTheNameAndTheShortDescription() throws {
        let instance = try SharedFixture.decode(Components.Schemas.Instance.self, from: "instance/response.json")
        let expected = try SharedFixture.decode(Expected.self, from: "instance/expected.json")

        #expect(instance.name == expected.name)
        #expect(instance.shortDescription == expected.shortDescription)
    }
}
