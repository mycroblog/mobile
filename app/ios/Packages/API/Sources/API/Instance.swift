// `GET /api/v1/instance`: what a server says about itself, and why it could not be read.
import Domain
import Foundation
import GeneratedAPI
import OpenAPIRuntime

extension APIClient {
    /// The instance of the server this client talks to.
    ///
    /// - Throws: An ``InstanceLookupError``, or `CancellationError` when the task was cancelled.
    public func instance() async throws -> Instance {
        let output: Operations.GetInstance.Output
        do {
            output = try await generated.getInstance()
        } catch {
            throw try Self.lookupError(error)
        }
        switch output {
        case .ok(let ok):
            switch ok.body {
            case .json(let instance): return Instance(instance)
            }
        case .default(_, let answer):
            switch answer.body {
            case .applicationProblemJson(let problem):
                throw InstanceLookupError.problem(title: problem.title, status: problem.status)
            }
        }
    }

    /// What a failure of the generated client means to the screen; a cancellation is thrown again as itself.
    private static func lookupError(_ error: any Error) throws -> InstanceLookupError {
        let underlying = (error as? ClientError)?.underlyingError ?? error
        if underlying is CancellationError || (underlying as? URLError)?.code == .cancelled {
            throw CancellationError()
        }
        if underlying is URLError {
            return .unreachable
        }
        // A failure before any answer is the transport's; one after it is an answer that my would not give.
        if let clientError = error as? ClientError, clientError.response == nil {
            return .unreachable
        }
        return .notMycroblog
    }
}

extension Instance {
    init(_ instance: Components.Schemas.Instance) {
        let description = instance.shortDescription?.trimmingCharacters(in: .whitespacesAndNewlines)
        self.init(
            name: instance.name,
            shortDescription: description?.isEmpty == false ? description : nil,
            timelines: InstanceTimelines(
                local: InstanceAudience(instance.timelines.local),
                federated: InstanceAudience(instance.timelines.federated)
            ),
            isSingleUser: instance.singleUser
        )
    }
}

extension InstanceAudience {
    init(_ audience: Components.Schemas.InstanceAudience) {
        switch audience {
        case .off: self = .off
        case .signedIn: self = .signedIn
        case .everyone: self = .everyone
        }
    }
}
