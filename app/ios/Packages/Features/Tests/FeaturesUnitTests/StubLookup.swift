import AppData
import Domain

/// A lookup that answers from a list, one answer per lookup, and records each address it was asked. While it is held,
/// a lookup waits until `release()`, so that a test sees the state during it.
@MainActor final class StubLookup: InstanceLookup {
    private(set) var addresses: [ServerAddress] = []
    private var answers: [Result<Instance, InstanceLookupError>]
    private var waiting: [CheckedContinuation<Void, Never>] = []
    var isHeld = false

    init(answers: [Result<Instance, InstanceLookupError>]) {
        self.answers = answers
    }

    @MainActor func instance(at address: ServerAddress) async throws -> Instance {
        addresses.append(address)
        if isHeld {
            await withCheckedContinuation { waiting.append($0) }
        }
        guard !answers.isEmpty else { throw InstanceLookupError.unreachable }
        return try answers.removeFirst().get()
    }

    /// Lets the lookups that wait go on.
    func release() {
        isHeld = false
        let continuations = waiting
        waiting = []
        for continuation in continuations { continuation.resume() }
    }

    /// Waits until the lookup was asked `count` addresses, or gives up after a while.
    func waitForLookups(_ count: Int) async {
        var turns = 0
        while addresses.count < count && turns < 10_000 {
            await Task.yield()
            turns += 1
        }
    }
}
