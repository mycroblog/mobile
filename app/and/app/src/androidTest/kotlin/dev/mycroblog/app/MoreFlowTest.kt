package dev.mycroblog.app

import androidx.annotation.StringRes
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.mycroblog.feature.settings.impl.R as SettingsR
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/** On a phone, a destination that the navigation bar has no room for opens from "More". */
@RunWith(AndroidJUnit4::class)
class MoreFlowTest {
    @get:Rule
    val compose = createAndroidComposeRule<MainActivity>()

    private fun string(@StringRes id: Int): String = compose.activity.getString(id)

    @Test
    fun settingsOpensFromMore() {
        compose.onNodeWithText(string(R.string.destination_more)).performClick()
        compose.onNodeWithText(string(R.string.destination_settings)).performClick()

        compose.onNodeWithText(string(SettingsR.string.settings_title)).assertIsDisplayed()
        compose.onNodeWithText(string(SettingsR.string.settings_server_section)).assertIsDisplayed()
    }
}
