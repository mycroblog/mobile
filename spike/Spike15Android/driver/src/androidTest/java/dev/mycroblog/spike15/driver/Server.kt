package dev.mycroblog.spike15.driver

import android.graphics.Bitmap
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import java.io.ByteArrayOutputStream
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.InetAddress
import java.net.ServerSocket
import org.junit.Test

/**
 * One command per line on 127.0.0.1:9008 (reach it with `adb forward tcp:9008 tcp:9008`).
 * Every reply is frames of a 4-byte big-endian length followed by bytes; `step` replies with the tree, then the PNG.
 */
class Server {
    private val instr = InstrumentationRegistry.getInstrumentation()
    private val device = UiDevice.getInstance(instr)

    @Test
    fun serve() {
        ServerSocket(9008, 1, InetAddress.getByName("127.0.0.1")).use { server ->
            while (true) {
                server.accept().use { socket ->
                    val input = DataInputStream(socket.getInputStream()).bufferedReader()
                    val out = DataOutputStream(socket.getOutputStream().buffered())
                    while (true) {
                        val line = input.readLine() ?: break
                        if (line == "quit") return
                        val result = runCatching { handle(line) }.getOrElse { listOf("error: $it".toByteArray()) }
                        result.forEach { out.writeInt(it.size); out.write(it) }
                        out.flush()
                    }
                }
            }
        }
    }

    private fun handle(line: String): List<ByteArray> {
        val words = line.split(" ")
        val arg = line.substringAfter(" ", "")
        when (words[0]) {
            "tap" -> device.click(words[1].toInt(), words[2].toInt())
            "tapText" -> device.findObject(By.text(arg))?.click() ?: error("no element with text $arg")
            "type" -> device.findObject(By.focused(true))?.text = arg
            "swipe" -> device.swipe(words[1].toInt(), words[2].toInt(), words[3].toInt(), words[4].toInt(), 10)
            "back" -> device.pressBack()
            "home" -> device.pressHome()
            "tree" -> return listOf(tree())
            "shot" -> return listOf(shot())
            "none" -> {}
            else -> error("unknown command ${words[0]}")
        }
        device.waitForIdle(1000)
        return listOf(tree(), shot())
    }

    private fun tree(): ByteArray = ByteArrayOutputStream().also { device.dumpWindowHierarchy(it) }.toByteArray()

    private fun shot(): ByteArray {
        val bitmap = instr.uiAutomation.takeScreenshot() ?: error("no screenshot")
        return ByteArrayOutputStream().also { bitmap.compress(Bitmap.CompressFormat.PNG, 100, it) }.toByteArray()
    }
}
