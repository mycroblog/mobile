// The decoder of a blurhash (https://blurha.sh), the placeholder of a media attachment: a few base-83 digits that
// encode the DC and AC components of a small DCT of the image.
import Foundation

/// A decoded blurhash.
public enum Blurhash {
    /// The pixels of `hash` at `width` × `height`, as RGBA bytes (alpha 255), row by row; nil when `hash` is not a
    /// blurhash or the size is not positive. `punch` scales the contrast of the AC components.
    public static func decode(_ hash: String, width: Int, height: Int, punch: Double = 1) -> [UInt8]? {
        guard width > 0, height > 0, let components = components(of: hash, punch: punch) else { return nil }
        let (xCount, yCount, colors) = components
        var pixels = [UInt8](repeating: 255, count: width * height * 4)
        var cosX = [Double](repeating: 0, count: width * xCount)
        for x in 0..<width {
            for i in 0..<xCount { cosX[x * xCount + i] = cos(Double.pi * Double(x * i) / Double(width)) }
        }
        var cosY = [Double](repeating: 0, count: height * yCount)
        for y in 0..<height {
            for j in 0..<yCount { cosY[y * yCount + j] = cos(Double.pi * Double(y * j) / Double(height)) }
        }
        for y in 0..<height {
            for x in 0..<width {
                var r = 0.0
                var g = 0.0
                var b = 0.0
                for j in 0..<yCount {
                    for i in 0..<xCount {
                        let basis = cosX[x * xCount + i] * cosY[y * yCount + j]
                        let color = colors[j * xCount + i]
                        r += color.0 * basis
                        g += color.1 * basis
                        b += color.2 * basis
                    }
                }
                let offset = (y * width + x) * 4
                pixels[offset] = sRGB(fromLinear: r)
                pixels[offset + 1] = sRGB(fromLinear: g)
                pixels[offset + 2] = sRGB(fromLinear: b)
            }
        }
        return pixels
    }

    /// Whether `hash` is a blurhash.
    public static func isValid(_ hash: String) -> Bool {
        components(of: hash, punch: 1) != nil
    }

    /// The average color of `hash` as sRGB bytes, or nil when it is not a blurhash.
    public static func averageColor(of hash: String) -> (red: UInt8, green: UInt8, blue: UInt8)? {
        guard let digits = digits(of: hash), digits.count >= 6, let value = number(digits[2..<6]) else { return nil }
        return (UInt8(value >> 16), UInt8((value >> 8) & 255), UInt8(value & 255))
    }

    // MARK: Decoding

    private typealias Color = (Double, Double, Double)

    private static func components(of hash: String, punch: Double) -> (Int, Int, [Color])? {
        guard let digits = digits(of: hash), digits.count >= 6, let sizeFlag = number(digits[0..<1]) else {
            return nil
        }
        let xCount = sizeFlag % 9 + 1
        let yCount = sizeFlag / 9 + 1
        guard digits.count == 4 + 2 * xCount * yCount, let quantizedMax = number(digits[1..<2]),
            let dc = number(digits[2..<6])
        else { return nil }
        let maximum = Double(quantizedMax + 1) / 166 * punch
        var colors: [Color] = [
            (linear(fromSRGB: dc >> 16), linear(fromSRGB: (dc >> 8) & 255), linear(fromSRGB: dc & 255))
        ]
        for index in 1..<(xCount * yCount) {
            let start = 4 + index * 2
            guard let value = number(digits[start..<(start + 2)]) else { return nil }
            colors.append(
                (
                    signedPow(Double(value / (19 * 19)) - 9, maximum),
                    signedPow(Double((value / 19) % 19) - 9, maximum),
                    signedPow(Double(value % 19) - 9, maximum)
                ))
        }
        return (xCount, yCount, colors)
    }

    private static func signedPow(_ quantized: Double, _ maximum: Double) -> Double {
        let value = quantized / 9
        return (value < 0 ? -1 : 1) * value * value * maximum
    }

    private static let alphabet = Array(
        "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz#$%*+,-.:;=?@[]^_{|}~".unicodeScalars)
    private static let values: [Unicode.Scalar: Int] = Dictionary(
        uniqueKeysWithValues: alphabet.enumerated().map { ($1, $0) })

    private static func digits(of hash: String) -> [Int]? {
        var digits: [Int] = []
        for scalar in hash.unicodeScalars {
            guard let value = values[scalar] else { return nil }
            digits.append(value)
        }
        return digits
    }

    private static func number(_ digits: ArraySlice<Int>) -> Int? {
        digits.reduce(0) { $0 * 83 + $1 }
    }

    private static func linear(fromSRGB value: Int) -> Double {
        let v = Double(value) / 255
        return v <= 0.04045 ? v / 12.92 : pow((v + 0.055) / 1.055, 2.4)
    }

    private static func sRGB(fromLinear value: Double) -> UInt8 {
        let v = max(0, min(1, value))
        let s = v <= 0.0031308 ? v * 12.92 : 1.055 * pow(v, 1 / 2.4) - 0.055
        return UInt8((s * 255 + 0.5).rounded(.down))
    }
}
