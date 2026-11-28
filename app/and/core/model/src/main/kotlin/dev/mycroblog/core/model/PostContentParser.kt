package dev.mycroblog.core.model

/**
 * Turns the sanitized HTML of a post's content, or the plain text of a content warning or a display name, with its
 * custom emoji, into [ContentBlock]s, by the rule of `shared/fixtures/post-content/cases.json`. It never throws:
 * malformed HTML keeps the text it can find. It is pure and runs on any thread; parse a post once, off the main thread,
 * and keep the result.
 */
object PostContentParser {
    /** The blocks of the HTML [html], whose `:shortcode:`s of [emojis] become emoji runs. */
    fun parseHtml(html: String, emojis: List<CustomEmoji>): List<ContentBlock> =
        HtmlWalker(emojis.mapTo(HashSet()) { it.shortcode }).walk(html)

    /** The blocks of the plain text [text]: one paragraph, its characters as they are, or none when it is empty. */
    fun parsePlainText(text: String, emojis: List<CustomEmoji>): List<ContentBlock> {
        val runs = splitEmoji(listOf(TextRun(text)), emojis.mapTo(HashSet()) { it.shortcode })
        return if (runs.isEmpty()) emptyList() else listOf(ContentBlock(BlockType.PARAGRAPH, runs))
    }

    /** The runs of the plain text [text], as one line such as a display name shows. */
    fun parsePlainRuns(text: String, emojis: List<CustomEmoji>): List<ContentRun> =
        parsePlainText(text, emojis).flatMap { it.runs }
}

/** The styles and the link that a piece of text has where it stands. */
private data class Attributes(val styles: List<RunStyle>, val link: RunLink?)

/** One character of a block, with its attributes; [isBreak] for the newline of a `br`. */
private class Cell(val char: Char, val attributes: Attributes, val isBreak: Boolean = false)

private class Element(val name: String, val classes: Set<String>, val href: String?)

private class HtmlWalker(private val emoji: Set<String>) {
    private val stack = mutableListOf<Element>()
    private val cells = mutableListOf<Cell>()
    private val blocks = mutableListOf<ContentBlock>()

    fun walk(html: String): List<ContentBlock> {
        HtmlTokenizer(html).forEach { token ->
            when (token) {
                is HtmlToken.Text -> if (!invisible()) {
                    val attributes = attributes()
                    token.text.forEach { cells += Cell(it, attributes) }
                }

                is HtmlToken.Start -> start(token)

                is HtmlToken.End -> end(token.name)
            }
        }
        flush()
        return blocks
    }

    private fun start(token: HtmlToken.Start) {
        val name = token.name
        when {
            name == "br" -> if (!invisible()) cells += Cell('\n', attributes(), isBreak = true)

            name in BLOCK_ELEMENTS || name in VOID_BLOCK_ELEMENTS -> {
                flush()
                if (name !in VOID_BLOCK_ELEMENTS && !token.selfClosing) stack += token.toElement()
            }

            name in VOID_ELEMENTS || token.selfClosing -> Unit

            else -> stack += token.toElement()
        }
    }

    private fun end(name: String) {
        val index = stack.indexOfLast { it.name == name }
        if (index < 0) return
        if (name in BLOCK_ELEMENTS) {
            flush()
        }
        // Closing an element closes the elements left open inside it.
        while (stack.size > index) {
            val element = stack.removeAt(stack.lastIndex)
            if (element.name == "span" && "ellipsis" in element.classes && !invisible()) {
                cells += Cell('…', attributes())
            }
        }
    }

    private fun HtmlToken.Start.toElement(): Element {
        val classes = attributes["class"].orEmpty().split(' ', '\t', '\n').filter { it.isNotEmpty() }.toSet()
        return Element(name, classes, attributes["href"])
    }

    private fun invisible(): Boolean = stack.any { it.name == "span" && "invisible" in it.classes }

    private fun attributes(): Attributes {
        val styles = mutableSetOf<RunStyle>()
        var link: RunLink? = null
        for (element in stack) {
            STYLES[element.name]?.let { styles += it }
            if (element.name == "a") {
                link = element.href?.let { href ->
                    val type = when {
                        "hashtag" in element.classes -> LinkType.HASHTAG
                        "mention" in element.classes -> LinkType.MENTION
                        else -> LinkType.URL
                    }
                    RunLink(type, href)
                }
            }
        }
        return Attributes(styles.sortedBy { it.ordinal }, link)
    }

    /** The type of a block that the open elements make: the nearest `pre`, `li` or `blockquote`, or a paragraph. */
    private fun blockType(): BlockType {
        for (element in stack.asReversed()) {
            when (element.name) {
                "pre" -> return BlockType.CODE
                "li" -> return BlockType.LIST_ITEM
                "blockquote" -> return BlockType.QUOTE
            }
        }
        return BlockType.PARAGRAPH
    }

    private fun flush() {
        if (cells.isEmpty()) return
        val type = blockType()
        val text = if (type == BlockType.CODE) cells.toList() else collapse(cells)
        cells.clear()
        val runs = splitEmoji(merge(text), emoji)
        if (runs.isNotEmpty()) blocks += ContentBlock(type, runs)
    }

    /** Whitespace collapsed to one space and trimmed at both ends, and dropped around a line break. */
    private fun collapse(cells: List<Cell>): List<Cell> {
        val out = ArrayList<Cell>(cells.size)
        var afterBreak = false
        for (cell in cells) {
            when {
                cell.isBreak -> {
                    out.dropTrailingSpaces()
                    out += cell
                    afterBreak = true
                }

                cell.char.isHtmlWhitespace() -> {
                    val last = out.lastOrNull()
                    if (!afterBreak && last != null && last.char != ' ' && !last.isBreak) {
                        out += Cell(' ', cell.attributes)
                    }
                }

                else -> {
                    out += cell
                    afterBreak = false
                }
            }
        }
        out.dropTrailingSpaces()
        return out
    }

    private fun MutableList<Cell>.dropTrailingSpaces() {
        while (isNotEmpty() && last().char == ' ' && !last().isBreak) removeAt(lastIndex)
    }

    /** The cells as text runs, one per stretch of the same attributes. */
    private fun merge(cells: List<Cell>): List<TextRun> {
        val runs = mutableListOf<TextRun>()
        val text = StringBuilder()
        var current: Attributes? = null
        for (cell in cells) {
            if (cell.attributes != current) {
                current?.let { if (text.isNotEmpty()) runs += TextRun(text.toString(), it.styles, it.link) }
                text.clear()
                current = cell.attributes
            }
            text.append(cell.char)
        }
        current?.let { if (text.isNotEmpty()) runs += TextRun(text.toString(), it.styles, it.link) }
        return runs
    }

    private companion object {
        val STYLES = mapOf(
            "b" to RunStyle.STRONG,
            "strong" to RunStyle.STRONG,
            "i" to RunStyle.EMPHASIS,
            "em" to RunStyle.EMPHASIS,
            "del" to RunStyle.STRIKETHROUGH,
            "s" to RunStyle.STRIKETHROUGH,
            "u" to RunStyle.UNDERLINE,
            "code" to RunStyle.CODE,
        )

        /** The elements that begin and end a block. */
        val BLOCK_ELEMENTS = setOf(
            "p", "blockquote", "pre", "li", "ul", "ol", "div", "section", "article", "aside", "header", "footer",
            "nav", "main", "figure", "figcaption", "address", "h1", "h2", "h3", "h4", "h5", "h6", "dl", "dt", "dd",
            "table", "thead", "tbody", "tfoot", "tr", "td", "th", "caption", "details", "summary",
        )

        /** The block elements that have no content. */
        val VOID_BLOCK_ELEMENTS = setOf("hr")

        /** The elements that have no content and no end tag. */
        val VOID_ELEMENTS = setOf(
            "img", "input", "meta", "link", "wbr", "area", "base", "col", "embed", "param", "source", "track",
        )
    }
}

/** HTML's ASCII whitespace; a no-break space is text. */
private fun Char.isHtmlWhitespace(): Boolean = this in HTML_WHITESPACE

private const val HTML_WHITESPACE = " \t\n\r\u000C"

/**
 * The runs with each `:shortcode:` of [emoji] in their text as an [EmojiRun] of the same link; any other `:word:` stays
 * text. Adjacent text runs of the same styles and link are merged.
 */
internal fun splitEmoji(runs: List<TextRun>, emoji: Set<String>): List<ContentRun> {
    val out = mutableListOf<ContentRun>()
    fun addText(text: String, run: TextRun) {
        if (text.isEmpty()) return
        val last = out.lastOrNull()
        if (last is TextRun && last.styles == run.styles && last.link == run.link) {
            out[out.lastIndex] = last.copy(text = last.text + text)
        } else {
            out += TextRun(text, run.styles, run.link)
        }
    }
    for (run in runs) {
        val text = run.text
        var start = 0
        var i = 0
        while (i < text.length) {
            if (text[i] == ':') {
                var end = i + 1
                while (end < text.length && text[end].isShortcodeChar()) end++
                if (end < text.length && end > i + 1 && text[end] == ':' && text.substring(i + 1, end) in emoji) {
                    addText(text.substring(start, i), run)
                    out += EmojiRun(text.substring(i + 1, end), run.link)
                    i = end + 1
                    start = i
                    continue
                }
            }
            i++
        }
        addText(text.substring(start), run)
    }
    return out
}

private fun Char.isShortcodeChar(): Boolean = this == '_' || this in 'a'..'z' || this in 'A'..'Z' || this in '0'..'9'
