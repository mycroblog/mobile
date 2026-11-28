package dev.mycroblog.core.model

/** A piece of HTML: text with its character references decoded, a start tag, or an end tag. */
internal sealed interface HtmlToken {
    data class Text(val text: String) : HtmlToken

    /** A start tag, its [name] and attribute names in lower case. */
    data class Start(val name: String, val attributes: Map<String, String>, val selfClosing: Boolean) : HtmlToken

    data class End(val name: String) : HtmlToken
}

/**
 * The tokens of a fragment of HTML, leniently: a `<` that begins no tag is text, comments and declarations are
 * skipped, and an unterminated tag ends the input. The contents of `script` and `style` are dropped.
 */
internal class HtmlTokenizer(private val html: String) : Iterable<HtmlToken> {
    override fun iterator(): Iterator<HtmlToken> = tokens().iterator()

    private fun tokens(): List<HtmlToken> {
        val tokens = mutableListOf<HtmlToken>()
        val text = StringBuilder()
        var i = 0
        fun flushText() {
            if (text.isNotEmpty()) tokens += HtmlToken.Text(decodeEntities(text.toString()))
            text.clear()
        }
        while (i < html.length) {
            val c = html[i]
            if (c != '<') {
                text.append(c)
                i++
                continue
            }
            val next = html.getOrNull(i + 1)
            when {
                html.startsWith("<!--", i) -> {
                    flushText()
                    val end = html.indexOf("-->", i + 4)
                    i = if (end < 0) html.length else end + 3
                }

                next == '!' || next == '?' -> {
                    flushText()
                    val end = html.indexOf('>', i)
                    i = if (end < 0) html.length else end + 1
                }

                next == '/' && html.getOrNull(i + 2)?.isLetter() == true -> {
                    flushText()
                    val end = html.indexOf('>', i)
                    val body = html.substring(i + 2, if (end < 0) html.length else end)
                    tokens += HtmlToken.End(body.takeWhile { it.isTagNameChar() }.lowercase())
                    i = if (end < 0) html.length else end + 1
                }

                next?.isLetter() == true -> {
                    flushText()
                    val (tag, end) = readStartTag(i + 1)
                    tokens += tag
                    i = end
                    if (tag.name == "script" || tag.name == "style") {
                        val close = html.indexOf("</${tag.name}", i, ignoreCase = true)
                        i = if (close < 0) html.length else close
                    }
                }

                else -> {
                    text.append(c)
                    i++
                }
            }
        }
        flushText()
        return tokens
    }

    /** The start tag whose name begins at [from], and the index after its `>`. */
    private fun readStartTag(from: Int): Pair<HtmlToken.Start, Int> {
        var i = from
        while (i < html.length && html[i].isTagNameChar()) i++
        val name = html.substring(from, i).lowercase()
        val attributes = mutableMapOf<String, String>()
        var selfClosing = false
        while (i < html.length) {
            val c = html[i]
            when {
                c == '>' -> return HtmlToken.Start(name, attributes, selfClosing) to i + 1

                c == '/' -> {
                    selfClosing = true
                    i++
                }

                c.isWhitespace() -> i++

                else -> {
                    selfClosing = false
                    val nameStart = i
                    while (i < html.length && !html[i].isWhitespace() && html[i] != '=' && html[i] != '>' &&
                        html[i] != '/'
                    ) {
                        i++
                    }
                    val attribute = html.substring(nameStart, i).lowercase()
                    while (i < html.length && html[i].isWhitespace()) i++
                    var value = ""
                    if (i < html.length && html[i] == '=') {
                        i++
                        while (i < html.length && html[i].isWhitespace()) i++
                        val quote = html.getOrNull(i)
                        if (quote == '"' || quote == '\'') {
                            val end = html.indexOf(quote, i + 1).let { if (it < 0) html.length else it }
                            value = html.substring(i + 1, end)
                            i = (end + 1).coerceAtMost(html.length)
                        } else {
                            val valueStart = i
                            while (i < html.length && !html[i].isWhitespace() && html[i] != '>') i++
                            value = html.substring(valueStart, i)
                        }
                    }
                    if (attribute.isNotEmpty()) attributes.putIfAbsent(attribute, decodeEntities(value))
                }
            }
        }
        return HtmlToken.Start(name, attributes, selfClosing) to html.length
    }
}

private fun Char.isTagNameChar(): Boolean = isLetterOrDigit() || this == '-' || this == ':'

private val NAMED_ENTITIES = mapOf(
    "amp" to "&", "lt" to "<", "gt" to ">", "quot" to "\"", "apos" to "'", "nbsp" to " ",
    "hellip" to "…", "mdash" to "—", "ndash" to "–", "lsquo" to "‘", "rsquo" to "’", "ldquo" to "“",
    "rdquo" to "”", "copy" to "©", "reg" to "®", "trade" to "™",
)

/** [text] with its character references (`&amp;`, `&#39;`, `&#x27;`) decoded; an unknown one stays as it is. */
internal fun decodeEntities(text: String): String {
    if ('&' !in text) return text
    val out = StringBuilder(text.length)
    var i = 0
    while (i < text.length) {
        val c = text[i]
        if (c == '&') {
            val end = text.indexOf(';', i + 1)
            if (end > i + 1 && end - i <= MAX_ENTITY_LENGTH) {
                val name = text.substring(i + 1, end)
                val decoded = when {
                    name.startsWith("#x") || name.startsWith("#X") -> name.substring(2).toIntOrNull(16)?.codePoint()
                    name.startsWith("#") -> name.substring(1).toIntOrNull()?.codePoint()
                    else -> NAMED_ENTITIES[name]
                }
                if (decoded != null) {
                    out.append(decoded)
                    i = end + 1
                    continue
                }
            }
        }
        out.append(c)
        i++
    }
    return out.toString()
}

private const val MAX_ENTITY_LENGTH = 10

private fun Int.codePoint(): String? =
    if (Character.isValidCodePoint(this) && this != 0) String(Character.toChars(this)) else null
