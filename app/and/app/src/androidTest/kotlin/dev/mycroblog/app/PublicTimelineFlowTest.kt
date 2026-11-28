package dev.mycroblog.app

import android.Manifest
import android.os.Build
import androidx.annotation.StringRes
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasScrollAction
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.printToString
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import dev.mycroblog.core.designsystem.R as DesignR
import dev.mycroblog.feature.settings.impl.R as SettingsR
import dev.mycroblog.feature.timeline.impl.R as TimelineR
import java.net.HttpURLConnection
import java.net.URL
import org.json.JSONObject
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * A reader who has not signed in chooses the local my (task my:up), reads its Local timeline, opens and closes a
 * content warning, and sees the same timeline after the activity is recreated.
 *
 * The local my is reached at the instrumentation argument `myUrl`, by default `http://10.0.2.2:8790`: the emulator's
 * address of the host's loopback.
 */
@RunWith(AndroidJUnit4::class)
class PublicTimelineFlowTest {
    @get:Rule
    val compose = createAndroidComposeRule<MainActivity>()

    private val myUrl: String =
        InstrumentationRegistry.getArguments().getString("myUrl") ?: "http://10.0.2.2:8790"

    private fun string(@StringRes id: Int): String = compose.activity.getString(id)

    private fun waitFor(matcher: SemanticsMatcher, what: String, timeoutMillis: Long = 30_000) {
        try {
            compose.waitUntil(timeoutMillis) { compose.onAllNodes(matcher).fetchSemanticsNodes().isNotEmpty() }
        } catch (e: androidx.compose.ui.test.ComposeTimeoutException) {
            val screen = compose.onRoot().printToString(maxDepth = Int.MAX_VALUE)
            throw AssertionError("$what did not show in ${timeoutMillis / 1000} s at $myUrl; the screen:\n$screen", e)
        }
    }

    /** The newest posts of the local my's Local timeline, as my answers them to this app's process. */
    private fun localTimeline(): List<JSONObject> {
        val connection = URL("$myUrl/api/v1/timelines/local?limit=40").openConnection() as HttpURLConnection
        connection.connectTimeout = 15_000
        connection.readTimeout = 15_000
        val body = try {
            connection.inputStream.bufferedReader().use { it.readText() }
        } catch (e: Exception) {
            throw AssertionError("the app's process could not read $myUrl/api/v1/timelines/local", e)
        } finally {
            connection.disconnect()
        }
        val items = JSONObject(body).getJSONArray("items")
        return (0 until items.length()).map { items.getJSONObject(it) }
    }

    @Test
    fun aReaderReadsTheLocalTimelineOfTheChosenServer() {
        // The system's dialog of the local network permission is not part of the app's flow: the reader grants it.
        if (Build.VERSION.SDK_INT >= 37) {
            InstrumentationRegistry.getInstrumentation().uiAutomation.grantRuntimePermission(
                compose.activity.packageName,
                Manifest.permission.ACCESS_LOCAL_NETWORK,
            )
        }
        val posts = localTimeline()
        check(posts.isNotEmpty()) { "the Local timeline of $myUrl has no post" }
        val newestAuthor = posts.first().getJSONObject("account").getString("display_name")

        compose.onNode(hasText(string(TimelineR.string.home_choose_server)) and hasClickAction()).performClick()
        compose.onNode(hasSetTextAction()).performTextInput(myUrl)
        compose.onNodeWithTextAndClick(string(SettingsR.string.choose_server_look_up))
        waitFor(hasText(string(SettingsR.string.choose_server_read)) and hasClickAction(), "the found server")
        compose.onNodeWithTextAndClick(string(SettingsR.string.choose_server_read))

        // The destination appears once the server's instance says it offers the timeline, in the navigation bar or,
        // when the bar has no room for it, in "More".
        val local = hasText(string(R.string.destination_local)) and hasClickAction()
        compose.waitForIdle()
        if (compose.onAllNodes(local).fetchSemanticsNodes().isEmpty()) {
            compose.onNodeWithTextAndClick(string(R.string.destination_more))
        }
        waitFor(local, "the Local destination")
        compose.onNodeWithTextAndClick(string(R.string.destination_local))
        waitFor(hasText(newestAuthor, substring = true), "the newest post of the Local timeline, by $newestAuthor")

        val warned = posts.firstOrNull { it.getString("spoiler_text").isNotEmpty() }
        val warning = warned?.getString("spoiler_text")
        if (warned != null && warning != null) {
            // The content of a warned post with plain paragraphs, as the reader reads it once opened.
            val content = warned.getString("content").replace(Regex("<[^>]+>"), " ").trim()
            compose.onNode(hasScrollAction()).performScrollToNode(hasText(warning, substring = true))
            compose.onNode(hasText(warning, substring = true)).assertIsDisplayed()
            check(compose.onAllNodes(hasText(content, substring = true)).fetchSemanticsNodes().isEmpty()) {
                "the content of the post warned as \"$warning\" shows before it is opened"
            }

            compose.onNodeWithTextAndClick(string(DesignR.string.post_show_more))
            compose.onNode(hasText(content, substring = true)).assertIsDisplayed()

            compose.onNodeWithTextAndClick(string(DesignR.string.post_show_less))
            compose.waitForIdle()
            check(compose.onAllNodes(hasText(content, substring = true)).fetchSemanticsNodes().isEmpty()) {
                "the content of the post warned as \"$warning\" still shows once closed"
            }
        }

        compose.activityRule.scenario.recreate()

        val shown = warning ?: newestAuthor
        waitFor(hasText(shown, substring = true), "after the activity was recreated, \"$shown\" of the Local timeline")
        compose.onNode(hasText(shown, substring = true)).assertIsDisplayed()
    }

    private fun androidx.compose.ui.test.junit4.ComposeTestRule.onNodeWithTextAndClick(text: String) {
        onNode(hasText(text) and hasClickAction()).performClick()
    }
}
