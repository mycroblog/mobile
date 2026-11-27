package dev.mycroblog.drive.runner

import android.app.UiAutomation
import android.graphics.Rect
import android.os.Build
import android.view.accessibility.AccessibilityNodeInfo
import android.view.accessibility.AccessibilityWindowInfo
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import org.json.JSONArray
import org.json.JSONObject

/**
 * The accessibility tree of every window on the screen, read from [AccessibilityNodeInfo] rather than
 * `dumpWindowHierarchy`, which leaves out role descriptions and headings.
 */
class Tree(private val automation: UiAutomation) {

    /** `{"role": "Screen", "children": [<one "Window" node per window>]}`. */
    fun read(): JSONObject {
        val windows = JSONArray()
        automation.windows.forEach { window -> windowNode(window)?.let(windows::put) }
        return JSONObject().put("role", "Screen").put("children", windows)
    }

    /** The visible bounds of the first node that [matches], in the windows of [app] first, then in any window. */
    fun find(app: String, matches: (AccessibilityNodeInfo) -> Boolean): Rect? =
        roots(app).firstNotNullOfOrNull { root -> first(root, matches) }?.let(::bounds)

    /** The visible bounds of the largest scrollable node, in the windows of [app] first, then in any window. */
    fun largestScrollable(app: String): Rect? {
        val (inApp, others) = roots(app).partition { it.packageName?.toString() == app }
        return listOf(inApp, others).firstNotNullOfOrNull { roots ->
            roots.flatMap { all(it) }
                .filter { it.isScrollable }
                .map(::bounds)
                .maxByOrNull { it.width() * it.height() }
        }
    }

    private fun roots(app: String): List<AccessibilityNodeInfo> {
        val roots = automation.windows.mapNotNull { it.root }
        val (inApp, others) = roots.partition { it.packageName?.toString() == app }
        return inApp + others
    }

    private fun windowNode(window: AccessibilityWindowInfo): JSONObject? {
        val root = window.root ?: return null
        val json = JSONObject().put("role", "Window")
        window.title?.toString()?.ifBlank { null }?.let { json.put("label", it) }
        json.put("frame", frame(Rect().also(window::getBoundsInScreen)))
        if (window.isFocused) json.put("states", JSONArray().put("focused"))
        val children = nodes(root)
        if (children.isNotEmpty()) json.put("children", JSONArray(children))
        return json
    }

    /** The node as JSON, or its children when it has no label, id, value, or state. */
    private fun nodes(node: AccessibilityNodeInfo): List<JSONObject> {
        if (!node.isVisibleToUser) return emptyList()
        val children = (0 until node.childCount).mapNotNull(node::getChild).flatMap(::nodes)
        val label = label(node)
        val id = node.viewIdResourceName?.ifBlank { null }
        val value = value(node)
        val states = states(node)
        if (label == null && id == null && value == null && states.isEmpty()) return children
        val json = JSONObject().put("role", role(node))
        label?.let { json.put("label", it) }
        id?.let { json.put("id", it) }
        value?.let { json.put("value", it) }
        json.put("frame", frame(bounds(node)))
        if (states.isNotEmpty()) json.put("states", JSONArray(states))
        if (children.isNotEmpty()) json.put("children", JSONArray(children))
        return listOf(json)
    }

    companion object {
        /** True when [label] is the node's label, text, or content description. */
        fun hasLabel(node: AccessibilityNodeInfo, label: String): Boolean =
            node.isVisibleToUser && (label(node) == label || node.text?.toString() == label ||
                node.contentDescription?.toString() == label)

        /** True when [id] is the node's resource name, whole or after `<package>:id/`. */
        fun hasId(node: AccessibilityNodeInfo, id: String): Boolean {
            val name = node.viewIdResourceName ?: return false
            return node.isVisibleToUser && (name == id || name.substringAfter(":id/") == id)
        }

        private fun first(node: AccessibilityNodeInfo, matches: (AccessibilityNodeInfo) -> Boolean): AccessibilityNodeInfo? {
            if (matches(node)) return node
            return (0 until node.childCount).asSequence().mapNotNull(node::getChild).firstNotNullOfOrNull { first(it, matches) }
        }

        private fun all(node: AccessibilityNodeInfo): List<AccessibilityNodeInfo> =
            if (!node.isVisibleToUser) emptyList()
            else listOf(node) + (0 until node.childCount).mapNotNull(node::getChild).flatMap(::all)

        /** The role description when set (Compose maps some `Role`s there), else the simple class name. */
        private fun role(node: AccessibilityNodeInfo): String =
            AccessibilityNodeInfoCompat.wrap(node).roleDescription?.toString()?.ifBlank { null }
                ?: node.className?.toString()?.substringAfterLast('.')?.ifBlank { null }
                ?: "View"

        /** The content description, else the hint of an editable field or the text of any other node. */
        private fun label(node: AccessibilityNodeInfo): String? {
            val text = if (node.isEditable) node.hintText else node.text
            return (node.contentDescription ?: text)?.toString()?.ifBlank { null }
        }

        /** The text of an editable field, else the state description, else the current value of a range. */
        private fun value(node: AccessibilityNodeInfo): String? {
            if (node.isEditable) return if (node.isShowingHintText) null else node.text?.toString()?.ifEmpty { null }
            val state = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) node.stateDescription else null
            return state?.toString()?.ifBlank { null } ?: node.rangeInfo?.current?.toString()
        }

        @Suppress("DEPRECATION") // isChecked is the only checked state below API 36.
        private fun states(node: AccessibilityNodeInfo): List<String> = buildList {
            if (node.isSelected) add("selected")
            if (!node.isEnabled) add("disabled")
            if (node.isFocused) add("focused")
            if (node.isChecked) add("checked")
            if (node.isHeading) add("heading")
        }

        private fun bounds(node: AccessibilityNodeInfo): Rect = Rect().also(node::getBoundsInScreen)

        private fun frame(rect: Rect): JSONArray =
            JSONArray().put(rect.left).put(rect.top).put(rect.width()).put(rect.height())
    }
}
