package dev.mycroblog.core.designsystem

import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.captureRoboImage
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

/** The placeholder of a destination with no content yet, on a phone in the light theme. */
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(qualifiers = RobolectricDeviceQualifiers.MediumPhone)
class PlaceholderScreenScreenshotTest {
    @Test
    fun withUpButton() = captureRoboImage {
        MycroblogTheme(darkTheme = false) {
            PlaceholderScreen(title = "Settings", onUp = {})
        }
    }
}
