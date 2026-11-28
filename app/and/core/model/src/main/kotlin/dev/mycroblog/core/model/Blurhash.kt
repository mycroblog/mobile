package dev.mycroblog.core.model

import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.withSign

/**
 * The decoder of a BlurHash (https://github.com/woltapp/blurhash, its algorithm, written here): the placeholder of a
 * medium while its preview loads, and the cover of a hidden one.
 */
object Blurhash {
    /**
     * The [width] by [height] pixels of [hash], as ARGB colors row by row (opaque), or null when [hash] is not a
     * BlurHash. [punch] raises the contrast; 1 leaves it.
     */
    fun decode(hash: String, width: Int, height: Int, punch: Float = 1f): IntArray? {
        if (hash.length < MIN_LENGTH || width <= 0 || height <= 0) return null
        val size = decode83(hash, 0, 1) ?: return null
        val componentsX = size % 9 + 1
        val componentsY = size / 9 + 1
        if (hash.length != 4 + 2 * componentsX * componentsY) return null
        val quantizedMax = decode83(hash, 1, 2) ?: return null
        val maxValue = (quantizedMax + 1) / 166f * punch
        val colors = ArrayList<DoubleArray>(componentsX * componentsY)
        colors += decodeDc(decode83(hash, 2, 6) ?: return null)
        for (index in 1 until componentsX * componentsY) {
            colors += decodeAc(decode83(hash, 4 + index * 2, 6 + index * 2) ?: return null, maxValue)
        }
        val pixels = IntArray(width * height)
        val cosX = Array(componentsX) { i -> DoubleArray(width) { x -> cos(PI * x * i / width) } }
        val cosY = Array(componentsY) { j -> DoubleArray(height) { y -> cos(PI * y * j / height) } }
        for (y in 0 until height) {
            for (x in 0 until width) {
                var r = 0.0
                var g = 0.0
                var b = 0.0
                for (j in 0 until componentsY) {
                    for (i in 0 until componentsX) {
                        val basis = cosX[i][x] * cosY[j][y]
                        val color = colors[i + j * componentsX]
                        r += color[0] * basis
                        g += color[1] * basis
                        b += color[2] * basis
                    }
                }
                pixels[x + y * width] = (0xFF shl 24) or (linearToSrgb(r) shl 16) or (linearToSrgb(g) shl 8) or
                    linearToSrgb(b)
            }
        }
        return pixels
    }

    private const val MIN_LENGTH = 6
    private const val CHARACTERS =
        "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz#$%*+,-.:;=?@[]^_{|}~"

    private fun decode83(hash: String, from: Int, to: Int): Int? {
        var value = 0
        for (i in from until to) {
            val digit = CHARACTERS.indexOf(hash[i])
            if (digit < 0) return null
            value = value * 83 + digit
        }
        return value
    }

    private fun decodeDc(value: Int): DoubleArray = doubleArrayOf(
        srgbToLinear(value shr 16),
        srgbToLinear((value shr 8) and 0xFF),
        srgbToLinear(value and 0xFF),
    )

    private fun decodeAc(value: Int, maxValue: Float): DoubleArray {
        val r = value / (19 * 19)
        val g = (value / 19) % 19
        val b = value % 19
        return doubleArrayOf(
            signedPow2((r - 9) / 9.0) * maxValue,
            signedPow2((g - 9) / 9.0) * maxValue,
            signedPow2((b - 9) / 9.0) * maxValue,
        )
    }

    private fun signedPow2(value: Double): Double = (value * value).withSign(value)

    private fun srgbToLinear(value: Int): Double {
        val v = value / 255.0
        return if (v <= 0.04045) v / 12.92 else ((v + 0.055) / 1.055).pow(2.4)
    }

    private fun linearToSrgb(value: Double): Int {
        val v = value.coerceIn(0.0, 1.0)
        val srgb = if (v <= 0.0031308) v * 12.92 else 1.055 * v.pow(1 / 2.4) - 0.055
        return (srgb * 255 + 0.5).toInt().coerceIn(0, 255)
    }
}
