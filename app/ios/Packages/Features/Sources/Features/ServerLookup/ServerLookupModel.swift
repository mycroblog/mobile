import AppData
import Domain
import Observation

/// The state of the screen that looks up a server by its address and shows its instance.
@Observable
public final class ServerLookupModel: Identifiable {
    /// What the screen shows below the address.
    public enum State: Hashable, Sendable {
        /// Nothing has been looked up for the address.
        case idle
        /// The address is not one; nothing was fetched.
        case invalidAddress
        /// The instance of `host` is being fetched.
        case loading(host: String)
        /// The instance that `host` described.
        case loaded(host: String, instance: Instance)
        /// The lookup of `host` failed for `error`.
        case failed(host: String, error: InstanceLookupError)
    }

    /// The address as it is typed. Changing it clears what was looked up.
    public var address: String {
        didSet {
            guard address != oldValue, state != .idle else { return }
            generation += 1
            state = .idle
        }
    }

    /// What the screen shows below the address.
    public private(set) var state: State

    @ObservationIgnored private let lookup: any InstanceLookup
    /// The address of the last lookup, which "Try again" repeats.
    @ObservationIgnored private var lastAddress: ServerAddress?
    /// Counts the changes that make a lookup in flight stale, so that its answer is dropped.
    @ObservationIgnored private var generation = 0

    /// A screen with `address` typed and showing `state`.
    public init(lookup: any InstanceLookup, address: String = "", state: State = .idle) {
        self.lookup = lookup
        self.address = address
        self.state = state
        if case .failed = state { lastAddress = ServerAddress(parsing: address) }
    }

    /// Whether "Look up" can be used: something is typed, and no lookup is in flight.
    public var canLookUp: Bool {
        !address.trimmingCharacters(in: .whitespacesAndNewlines).isEmpty && !isLoading
    }

    private var isLoading: Bool {
        if case .loading = state { true } else { false }
    }

    /// Looks up the server at the typed address, or says that the address is not one.
    public func lookUp() async {
        guard canLookUp else { return }
        guard let address = ServerAddress(parsing: address) else {
            state = .invalidAddress
            return
        }
        await lookUp(address)
    }

    /// Repeats the lookup that failed.
    public func tryAgain() async {
        guard case .failed = state, let lastAddress else { return }
        await lookUp(lastAddress)
    }

    private func lookUp(_ address: ServerAddress) async {
        lastAddress = address
        generation += 1
        let current = generation
        state = .loading(host: address.host)
        let next: State
        do {
            next = .loaded(host: address.host, instance: try await lookup.instance(at: address))
        } catch let error as InstanceLookupError {
            next = .failed(host: address.host, error: error)
        } catch is CancellationError {
            // The screen went away.
            if current == generation { state = .idle }
            return
        } catch {
            next = .failed(host: address.host, error: .unreachable)
        }
        if current == generation { state = next }
    }
}
