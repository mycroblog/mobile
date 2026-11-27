package dev.mycroblog.drive.runner

import android.accessibilityservice.AccessibilityServiceInfo
import android.app.UiAutomation
import android.graphics.Bitmap
import android.graphics.Rect
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.util.Base64
import android.view.accessibility.AccessibilityNodeInfo
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.Configurator
import androidx.test.uiautomator.UiDevice
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.net.InetAddress
import java.net.ServerSocket
import kotlin.math.roundToInt
import org.json.JSONException
import org.json.JSONObject
import org.junit.Test

/**
 * The runner of the app-driving tool: one JSON request per line on device 127.0.0.1, one JSON reply per line.
 *
 * Start it with the instrumentation argument `port` (default 9008) and reach it with `adb forward`. It serves one
 * client at a time and ends on `{"op":"quit"}`.
 */
class Server {
    private val instrumentation = InstrumentationRegistry.getInstrumentation()
    private val device = UiDevice.getInstance(instrumentation)

    /** The UiAutomation that UiDevice uses, with the windows of the whole screen. */
    private val automation: UiAutomation by lazy {
        instrumentation.getUiAutomation(Configurator.getInstance().uiAutomationFlags).also { automation ->
            automation.serviceInfo?.let { info ->
                info.flags = info.flags or AccessibilityServiceInfo.FLAG_RETRIEVE_INTERACTIVE_WINDOWS
                automation.serviceInfo = info
            }
        }
    }

    private val tree by lazy { Tree(automation) }

    /** The tree before a gesture whose effect the next reply must wait for, as text; null for any other op. */
    private var before: String? = null

    @Test
    fun serve() {
        val port = InstrumentationRegistry.getArguments().getString("port")?.toInt() ?: DEFAULT_PORT
        ServerSocket(port, 1, InetAddress.getByName("127.0.0.1")).use { server ->
            while (true) {
                try {
                    server.accept().use { socket ->
                        val input = socket.getInputStream().bufferedReader()
                        val output = socket.getOutputStream().bufferedWriter()
                        while (true) {
                            val line = input.readLine() ?: break
                            if (line.isBlank()) continue
                            val (reply, quit) = respond(line)
                            output.write(reply.toString())
                            output.write("\n")
                            output.flush()
                            if (quit) return
                        }
                    }
                } catch (_: IOException) {
                    // The client went away; wait for the next one.
                }
            }
        }
    }

    private fun respond(line: String): Pair<JSONObject, Boolean> {
        val request = try {
            JSONObject(line)
        } catch (_: JSONException) {
            return screen("the request is not a JSON object") to false
        }
        return when (val op = request.optString("op")) {
            "ping" -> JSONObject().put("ok", true) to false
            "quit" -> JSONObject().put("ok", true) to true
            else -> {
                val error = try {
                    act(op, request)
                    null
                } catch (failure: Failure) {
                    before = null
                    failure.message
                } catch (e: Exception) {
                    before = null
                    "$op failed: $e"
                }
                screen(error) to false
            }
        }
    }

    private fun act(op: String, request: JSONObject) {
        val app = request.optString("app")
        when (op) {
            "look" -> {}
            "home" -> device.pressHome()
            "tap", "type", "scroll", "back" -> {
                requireRunning(app)
                if (op != "back") before = tree.read().toString()
                when (op) {
                    "tap" -> tap(request, app)
                    "type" -> type(request, app)
                    "scroll" -> scroll(request, app)
                    "back" -> device.pressBack()
                }
            }
            in IOS_ONLY -> fail("$op is an op of iOS only")
            else -> fail("unknown op \"$op\"")
        }
    }

    private fun tap(request: JSONObject, app: String) {
        if (request.has("x") && request.has("y")) {
            device.click(request.getDouble("x").roundToInt(), request.getDouble("y").roundToInt())
            return
        }
        val target = find(request, app) ?: fail("tap needs a label, an id, or x and y")
        click(target)
    }

    private fun type(request: JSONObject, app: String) {
        if (!request.has("text")) fail("type needs text")
        val text = request.getString("text")
        find(request, app)?.let { click(it) }
        val field = waitFor { automation.findFocus(AccessibilityNodeInfo.FOCUS_INPUT)?.takeIf { it.isEditable } }
            ?: fail("no field has the focus")
        val current = if (field.isShowingHintText || field.isPassword) "" else field.text?.toString().orEmpty()
        val arguments = Bundle().apply {
            putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, current + text)
        }
        if (!field.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments)) {
            fail("the focused field did not take the text")
        }
    }

    /** Swipes so that the content in [direction] comes into view: `down` shows what is below. */
    private fun scroll(request: JSONObject, app: String) {
        val direction = request.optString("direction")
        if (direction !in listOf("up", "down", "left", "right")) fail("scroll needs a direction: up, down, left, or right")
        val area = find(request, app)
            ?: tree.largestScrollable(app)
            ?: Rect(0, 0, device.displayWidth, device.displayHeight)
        val x = area.exactCenterX().roundToInt()
        val y = area.exactCenterY().roundToInt()
        val near = { start: Int, length: Int -> start + (length * 0.25).roundToInt() }
        val far = { start: Int, length: Int -> start + (length * 0.75).roundToInt() }
        val top = near(area.top, area.height())
        val bottom = far(area.top, area.height())
        val left = near(area.left, area.width())
        val right = far(area.left, area.width())
        when (direction) {
            "down" -> device.swipe(x, bottom, x, top, SWIPE_STEPS)
            "up" -> device.swipe(x, top, x, bottom, SWIPE_STEPS)
            "right" -> device.swipe(right, y, left, y, SWIPE_STEPS)
            "left" -> device.swipe(left, y, right, y, SWIPE_STEPS)
        }
    }

    /** The bounds of the element that `label` or `id` names, searched in [app] first; null when neither is given. */
    private fun find(request: JSONObject, app: String): Rect? {
        val label = request.optString("label").ifEmpty { null }
        val id = request.optString("id").ifEmpty { null }
        val (key, value) = when {
            label != null -> "label" to label
            id != null -> "id" to id
            else -> return null
        }
        return waitFor { tree.find(app) { if (label != null) Tree.hasLabel(it, label) else Tree.hasId(it, value) } }
            ?: fail("no element with $key \"$value\"")
    }

    private fun click(bounds: Rect) {
        device.click(bounds.exactCenterX().roundToInt(), bounds.exactCenterY().roundToInt())
    }

    private fun requireRunning(app: String) {
        if (app.isEmpty()) fail("the request names no app")
        if (shell("pidof $app").isBlank()) fail("$app is not running")
    }

    private fun shell(command: String): String =
        ParcelFileDescriptor.AutoCloseInputStream(automation.executeShellCommand(command)).use {
            it.readBytes().decodeToString()
        }

    /** The reply of an op: the tree and the screenshot after the screen settles, also when the op failed. */
    private fun screen(error: String?): JSONObject {
        val reply = JSONObject().put("ok", error == null)
        error?.let { reply.put("error", it) }
        reply.put("tree", settledTree())
        screenshot()?.let { reply.put("screenshot", it) }
        return reply
    }

    /**
     * Waits for idle, then until two reads of the tree 100 ms apart are equal (at most 3 s). After a gesture, two equal
     * reads that still show the screen before it count only after 1 s: idle and equal reads come before a navigation
     * has begun to draw.
     */
    private fun settledTree(): JSONObject {
        val unchanged = before
        before = null
        val start = System.currentTimeMillis()
        device.waitForIdle(IDLE_TIMEOUT_MS)
        var last = tree.read()
        var lastText = last.toString()
        val deadline = System.currentTimeMillis() + SETTLE_TIMEOUT_MS
        while (System.currentTimeMillis() < deadline) {
            Thread.sleep(POLL_MS)
            val next = tree.read()
            val nextText = next.toString()
            val settled = nextText != unchanged || System.currentTimeMillis() - start >= CHANGE_TIMEOUT_MS
            if (nextText == lastText && settled) return next
            last = next
            lastText = nextText
        }
        return last
    }

    private fun screenshot(): String? {
        val bitmap = automation.takeScreenshot() ?: return null
        val png = ByteArrayOutputStream().also { bitmap.compress(Bitmap.CompressFormat.PNG, 100, it) }.toByteArray()
        bitmap.recycle()
        return Base64.encodeToString(png, Base64.NO_WRAP)
    }

    private fun <T : Any> waitFor(read: () -> T?): T? {
        val deadline = System.currentTimeMillis() + FIND_TIMEOUT_MS
        while (true) {
            read()?.let { return it }
            if (System.currentTimeMillis() >= deadline) return null
            Thread.sleep(POLL_MS)
        }
    }

    private class Failure(message: String) : Exception(message)

    private fun fail(message: String): Nothing = throw Failure(message)

    private companion object {
        const val DEFAULT_PORT = 9008
        const val IDLE_TIMEOUT_MS = 1_000L
        const val SETTLE_TIMEOUT_MS = 3_000L
        const val FIND_TIMEOUT_MS = 2_000L
        const val CHANGE_TIMEOUT_MS = 1_000L
        const val POLL_MS = 100L
        const val SWIPE_STEPS = 40
        val IOS_ONLY = setOf("launch", "activate", "orientation", "open", "share", "resize")
    }
}
