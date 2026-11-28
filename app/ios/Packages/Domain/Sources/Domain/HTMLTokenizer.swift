// A tokenizer for the sanitized HTML of my's statuses: tags, their attributes, and text with its character
// references decoded. It is not a full HTML parser; it never fails, and what it cannot read as a tag it reads as text.

enum HTMLToken: Equatable {
    case text(String)
    case start(name: String, attributes: [String: String], selfClosing: Bool)
    case end(name: String)
}

struct HTMLTokenizer {
    private let scalars: [Unicode.Scalar]
    private var index = 0

    init(_ html: String) {
        scalars = Array(html.unicodeScalars)
    }

    mutating func next() -> HTMLToken? {
        guard index < scalars.count else { return nil }
        if scalars[index] == "<", let token = readMarkup() {
            return token
        }
        return readText()
    }

    // MARK: Text

    private mutating func readText() -> HTMLToken {
        var raw = String.UnicodeScalarView()
        // The first character is text even when it is a '<' that starts no tag.
        raw.append(scalars[index])
        index += 1
        while index < scalars.count, scalars[index] != "<" {
            raw.append(scalars[index])
            index += 1
        }
        return .text(Self.decodingReferences(String(raw)))
    }

    // MARK: Markup

    /// A tag, or a comment or doctype skipped as empty text; nil when the '<' at the index starts none.
    private mutating func readMarkup() -> HTMLToken? {
        let start = index
        var cursor = index + 1
        guard cursor < scalars.count else { return nil }
        if scalars[cursor] == "!" {
            // A comment, up to "-->", or a doctype, up to ">".
            if starts(with: "<!--", at: start) {
                cursor = start + 4
                while cursor < scalars.count, !starts(with: "-->", at: cursor) { cursor += 1 }
                index = min(cursor + 3, scalars.count)
            } else {
                while cursor < scalars.count, scalars[cursor] != ">" { cursor += 1 }
                index = min(cursor + 1, scalars.count)
            }
            return .text("")
        }
        let isEnd = scalars[cursor] == "/"
        if isEnd { cursor += 1 }
        guard cursor < scalars.count, scalars[cursor].properties.isAlphabetic else { return nil }
        var name = ""
        while cursor < scalars.count, !Self.isSpace(scalars[cursor]), scalars[cursor] != ">", scalars[cursor] != "/" {
            name.unicodeScalars.append(scalars[cursor])
            cursor += 1
        }
        var attributes: [String: String] = [:]
        var selfClosing = false
        while cursor < scalars.count {
            let scalar = scalars[cursor]
            if Self.isSpace(scalar) {
                cursor += 1
            } else if scalar == ">" {
                cursor += 1
                index = cursor
                let lowered = name.lowercased()
                return isEnd
                    ? .end(name: lowered) : .start(name: lowered, attributes: attributes, selfClosing: selfClosing)
            } else if scalar == "/" {
                selfClosing = true
                cursor += 1
            } else {
                selfClosing = false
                var key = ""
                while cursor < scalars.count, !Self.isSpace(scalars[cursor]),
                    !["=", ">", "/"].contains(scalars[cursor])
                {
                    key.unicodeScalars.append(scalars[cursor])
                    cursor += 1
                }
                while cursor < scalars.count, Self.isSpace(scalars[cursor]) { cursor += 1 }
                var value = ""
                if cursor < scalars.count, scalars[cursor] == "=" {
                    cursor += 1
                    while cursor < scalars.count, Self.isSpace(scalars[cursor]) { cursor += 1 }
                    if cursor < scalars.count, scalars[cursor] == "\"" || scalars[cursor] == "'" {
                        let quote = scalars[cursor]
                        cursor += 1
                        while cursor < scalars.count, scalars[cursor] != quote {
                            value.unicodeScalars.append(scalars[cursor])
                            cursor += 1
                        }
                        cursor += 1
                    } else {
                        while cursor < scalars.count, !Self.isSpace(scalars[cursor]), scalars[cursor] != ">" {
                            value.unicodeScalars.append(scalars[cursor])
                            cursor += 1
                        }
                    }
                }
                let lowered = key.lowercased()
                if !lowered.isEmpty, attributes[lowered] == nil {
                    attributes[lowered] = Self.decodingReferences(value)
                }
            }
        }
        // No '>' closes the tag: the rest is text.
        return nil
    }

    private func starts(with prefix: String, at position: Int) -> Bool {
        var cursor = position
        for scalar in prefix.unicodeScalars {
            guard cursor < scalars.count, scalars[cursor] == scalar else { return false }
            cursor += 1
        }
        return true
    }

    private static func isSpace(_ scalar: Unicode.Scalar) -> Bool {
        scalar == " " || scalar == "\t" || scalar == "\n" || scalar == "\r" || scalar == "\u{0C}"
    }

    // MARK: Character references

    private static let namedReferences: [String: String] = [
        "amp": "&", "lt": "<", "gt": ">", "quot": "\"", "apos": "'", "nbsp": "\u{00A0}",
        "hellip": "…", "mdash": "—", "ndash": "–", "copy": "©", "reg": "®", "trade": "™",
    ]

    static func decodingReferences(_ text: String) -> String {
        guard text.contains("&") else { return text }
        var result = ""
        var rest = Substring(text)
        while let ampersand = rest.firstIndex(of: "&") {
            result += rest[..<ampersand]
            let afterAmpersand = rest.index(after: ampersand)
            if let semicolon = rest[afterAmpersand...].prefix(12).firstIndex(of: ";"),
                let decoded = decode(reference: rest[afterAmpersand..<semicolon])
            {
                result += decoded
                rest = rest[rest.index(after: semicolon)...]
            } else {
                result += "&"
                rest = rest[afterAmpersand...]
            }
        }
        result += rest
        return result
    }

    private static func decode(reference: Substring) -> String? {
        if reference.hasPrefix("#") {
            let number = reference.dropFirst()
            let value: UInt32? =
                number.first == "x" || number.first == "X"
                ? UInt32(number.dropFirst(), radix: 16) : UInt32(number, radix: 10)
            guard let value, value != 0, let scalar = Unicode.Scalar(value) else { return nil }
            return String(Character(scalar))
        }
        return namedReferences[String(reference)]
    }
}
