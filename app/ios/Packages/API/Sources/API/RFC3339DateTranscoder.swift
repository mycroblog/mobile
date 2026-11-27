// The dates of the API are RFC 3339 date-times, whose fraction of a second is optional: my writes it when it is not
// zero (`2026-11-27T11:19:24.134Z`) and leaves it out when it is (`2026-11-27T11:19:24Z`). The runtime's transcoders
// each read only one of the two forms.
import Foundation
import OpenAPIRuntime

/// Reads an RFC 3339 date-time with or without a fraction of a second, and writes it with one.
package struct RFC3339DateTranscoder: DateTranscoder {
    private let fractional = ISO8601DateTranscoder.iso8601WithFractionalSeconds
    private let whole = ISO8601DateTranscoder.iso8601

    package init() {}

    package func encode(_ date: Date) throws -> String {
        try fractional.encode(date)
    }

    package func decode(_ string: String) throws -> Date {
        if let date = try? fractional.decode(string) {
            return date
        }
        return try whole.decode(string)
    }
}
