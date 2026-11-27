package dev.mycroblog.feature.settings.impl

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.captureRoboImage
import dev.mycroblog.core.designsystem.MycroblogTheme
import dev.mycroblog.core.model.Instance
import dev.mycroblog.core.model.InstanceLookupFailure
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

/** The server screen in each of its states, on a phone in the light theme. */
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(qualifiers = RobolectricDeviceQualifiers.MediumPhone)
class ChooseServerScreenScreenshotTest {
    @get:Rule
    val compose = createComposeRule()

    private val host = "example.social"

    /**
     * Renders the screen on a stopped clock, so that the progress indicator of a lookup under way, which never stops
     * moving, is shot at the same moment each time.
     */
    private fun capture(address: String, lookup: ServerLookupState) {
        compose.mainClock.autoAdvance = false
        compose.setContent {
            MycroblogTheme(darkTheme = false) {
                ChooseServerScreen(
                    address = TextFieldState(address),
                    uiState = ChooseServerUiState(address = address, lookup = lookup),
                    onLookUp = {},
                    onRetry = {},
                    onUp = {},
                )
            }
        }
        compose.mainClock.advanceTimeBy(FRAME_MILLIS)
        compose.onRoot().captureRoboImage()
    }

    @Test
    fun idle() = capture("", ServerLookupState.Idle)

    @Test
    fun invalidAddress() = capture("not a server", ServerLookupState.InvalidAddress)

    @Test
    fun loading() = capture(host, ServerLookupState.Loading(host))

    @Test
    fun found() = capture(
        host,
        ServerLookupState.Found(
            host,
            Instance("Example Social", "A place on the fediverse to test the mobile apps with."),
        ),
    )

    @Test
    fun foundWithoutShortDescription() = capture(host, ServerLookupState.Found(host, Instance("Example Social", null)))

    @Test
    fun failedWithNoAnswer() = capture(host, ServerLookupState.Failed(host, InstanceLookupFailure.NoAnswer))

    @Test
    fun failedAsNotMycroblog() = capture(host, ServerLookupState.Failed(host, InstanceLookupFailure.NotMycroblog))

    @Test
    fun failedWithAProblem() =
        capture(host, ServerLookupState.Failed(host, InstanceLookupFailure.Problem("Service Unavailable")))

    private companion object {
        /** How far the clock runs before the shot: long enough for the first frames, as on a device. */
        const val FRAME_MILLIS = 500L
    }
}
