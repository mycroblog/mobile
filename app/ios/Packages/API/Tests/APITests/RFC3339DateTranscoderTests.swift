import API
import Foundation
import Testing

/// The date-times of the API client: my writes RFC 3339 with a fraction of a second, such as
/// `2026-11-27T11:19:24.134Z`, and the client reads them with or without one.
struct RFC3339DateTranscoderTests {
    /// 2026-11-27T11:19:24Z.
    static let whole = Date(timeIntervalSince1970: 1_795_778_364)
    /// 2026-11-27T11:19:24.134Z.
    static let withMilliseconds = Date(timeIntervalSince1970: 1_795_778_364.134)

    let transcoder = RFC3339DateTranscoder()

    /// Whether two instants are the same to well under a millisecond, the finest unit my writes.
    static func same(_ lhs: Date, _ rhs: Date) -> Bool {
        abs(lhs.timeIntervalSince(rhs)) < 0.000_01
    }

    @Test(arguments: [
        ("2026-11-27T11:19:24.134Z", withMilliseconds),
        ("2026-11-27T11:19:24Z", whole),
    ])
    func decodesWithAndWithoutFractionalSeconds(_ string: String, _ instant: Date) throws {
        let decoded = try transcoder.decode(string)
        #expect(Self.same(decoded, instant), "\(string) decoded as \(decoded.timeIntervalSince1970)")
    }

    @Test(arguments: [
        ("2026-11-27T11:19:24Z", whole),
        ("2026-11-27T11:19:24+00:00", whole),
        ("2026-11-27T20:19:24+09:00", whole),
        ("2026-11-27T06:19:24-05:00", whole),
        ("2026-11-27T20:19:24.134+09:00", withMilliseconds),
    ])
    func decodesUTCAndNumericOffsets(_ string: String, _ instant: Date) throws {
        let decoded = try transcoder.decode(string)
        #expect(Self.same(decoded, instant), "\(string) decoded as \(decoded.timeIntervalSince1970)")
    }

    @Test(arguments: [whole, withMilliseconds])
    func encodesWhatDecodesToTheSameInstant(_ instant: Date) throws {
        let encoded = try transcoder.encode(instant)
        let decoded = try transcoder.decode(encoded)
        #expect(Self.same(decoded, instant), "\(encoded) decoded as \(decoded.timeIntervalSince1970)")
    }

    @Test(arguments: [whole, withMilliseconds])
    func encodesAnRFC3339DateTime(_ instant: Date) throws {
        let encoded = try transcoder.encode(instant)
        let dateTime = /\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}(\.\d+)?(Z|[+-]\d{2}:\d{2})/
        #expect(try dateTime.wholeMatch(in: encoded) != nil, "\(encoded) is not an RFC 3339 date-time")
    }

    @Test(arguments: [
        "",
        "not a date",
        "2026-11-27",
        "11:19:24Z",
        "2026-11-27T11:19:24",
        "2026-11-27T11:19Z",
        "2026-13-27T11:19:24Z",
        "1795778364",
    ])
    func refusesWhatIsNotAnRFC3339DateTime(_ string: String) {
        #expect(throws: (any Error).self, "\(string) was read as a date-time") {
            try transcoder.decode(string)
        }
    }
}
