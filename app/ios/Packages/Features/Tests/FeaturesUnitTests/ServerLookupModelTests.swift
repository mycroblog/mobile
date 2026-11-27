import Domain
import Testing

@testable import Features

/// The states of the server screen: an address is looked up only when it is valid, the lookup shows its host while it
/// runs and then the instance or the error, changing the address clears a result, and "Try again" repeats the lookup.
@MainActor
struct ServerLookupModelTests {
    static let instance = Instance(name: "Example Social", shortDescription: "A place to test the apps with.")

    /// A state as a line that a failed expectation can show.
    static func describe(_ state: ServerLookupModel.State) -> String {
        switch state {
        case .idle: "idle"
        case .invalidAddress: "invalid address"
        case .loading(let host): "loading \(host)"
        case .loaded(let host, let instance): "loaded \(host): \(instance.name), \(instance.shortDescription ?? "-")"
        case .failed(let host, let error): "failed \(host): \(describe(error))"
        }
    }

    static func describe(_ error: InstanceLookupError) -> String {
        switch error {
        case .unreachable: "unreachable"
        case .notMycroblog: "not mycroblog"
        case .problem(let title, let status): "problem \(status) \(title)"
        }
    }

    /// The origin of `address`, without the slash that a URL may end with.
    static func origin(_ address: ServerAddress) -> String {
        let string = address.origin.absoluteString
        return string.hasSuffix("/") ? String(string.dropLast()) : string
    }

    static func model(_ lookup: StubLookup, address: String = "") -> ServerLookupModel {
        ServerLookupModel(lookup: lookup, address: address, state: .idle)
    }

    @Test func anEmptyAddressCannotBeLookedUp() {
        let model = Self.model(StubLookup(answers: []))
        #expect(!model.canLookUp)
        #expect(Self.describe(model.state) == "idle")
    }

    @Test func anAddressCanBeLookedUp() {
        let model = Self.model(StubLookup(answers: []), address: "example.social")
        #expect(model.canLookUp)
    }

    @Test(arguments: ["http://example.social", "ftp://example.social", "exa mple.social"])
    func anInvalidAddressIsNotFetched(_ address: String) async {
        let lookup = StubLookup(answers: [.success(Self.instance)])
        let model = Self.model(lookup, address: address)

        await model.lookUp()

        #expect(Self.describe(model.state) == "invalid address")
        #expect(lookup.addresses.isEmpty, "\(address) was fetched")
    }

    @Test func aLookupShowsItsHostWhileItRunsAndThenTheInstance() async {
        let lookup = StubLookup(answers: [.success(Self.instance)])
        lookup.isHeld = true
        let model = Self.model(lookup, address: " https://example.social/about ")

        let running = Task { await model.lookUp() }
        await lookup.waitForLookups(1)

        #expect(Self.describe(model.state) == "loading example.social")
        #expect(!model.canLookUp, "the action is enabled while the lookup runs")
        #expect(lookup.addresses.map { Self.origin($0) } == ["https://example.social"])

        lookup.release()
        await running.value

        #expect(Self.describe(model.state) == "loaded example.social: Example Social, A place to test the apps with.")
        #expect(model.canLookUp)
    }

    @Test(arguments: [
        InstanceLookupError.unreachable,
        .notMycroblog,
        .problem(title: "Service Unavailable", status: 503),
    ])
    func aFailedLookupShowsTheHostAndWhy(_ error: InstanceLookupError) async {
        let model = Self.model(StubLookup(answers: [.failure(error)]), address: "example.social")

        await model.lookUp()

        #expect(Self.describe(model.state) == "failed example.social: \(Self.describe(error))")
    }

    @Test func tryAgainRepeatsTheSameLookup() async {
        let lookup = StubLookup(answers: [.failure(.unreachable), .success(Self.instance)])
        let model = Self.model(lookup, address: "http://localhost:8790")

        await model.lookUp()
        #expect(Self.describe(model.state) == "failed localhost: unreachable")

        await model.tryAgain()

        #expect(lookup.addresses.count == 2)
        #expect(lookup.addresses.map { Self.origin($0) } == ["http://localhost:8790", "http://localhost:8790"])
        #expect(Self.describe(model.state) == "loaded localhost: Example Social, A place to test the apps with.")
    }

    @Test func changingTheAddressClearsAnInstance() async {
        let model = Self.model(StubLookup(answers: [.success(Self.instance)]), address: "example.social")
        await model.lookUp()
        #expect(Self.describe(model.state).hasPrefix("loaded"))

        model.address = "example.org"

        #expect(Self.describe(model.state) == "idle")
        #expect(model.canLookUp)
    }

    @Test func changingTheAddressClearsAnError() async {
        let model = Self.model(StubLookup(answers: [.failure(.notMycroblog)]), address: "example.social")
        await model.lookUp()
        #expect(Self.describe(model.state).hasPrefix("failed"))

        model.address = "example.org"

        #expect(Self.describe(model.state) == "idle")
    }

    /// The scene restores the screen with only its typed address: nothing is fetched until "Look up".
    @Test func aRestoredScreenKeepsTheAddressAndLooksItUpAgain() async {
        let lookup = StubLookup(answers: [.success(Self.instance)])
        let model = ServerLookupModel(lookup: lookup, address: "example.social")

        #expect(model.address == "example.social")
        #expect(Self.describe(model.state) == "idle")
        #expect(lookup.addresses.isEmpty, "restoring the screen fetched")

        await model.lookUp()

        #expect(Self.describe(model.state).hasPrefix("loaded example.social"))
    }

    @Test func aNewLookupFetchesTheNewAddress() async {
        let lookup = StubLookup(answers: [.success(Self.instance), .success(Self.instance)])
        let model = Self.model(lookup, address: "example.social")
        await model.lookUp()

        model.address = "example.org"
        await model.lookUp()

        #expect(lookup.addresses.map(\.host) == ["example.social", "example.org"])
        #expect(Self.describe(model.state).hasPrefix("loaded example.org"))
    }
}
