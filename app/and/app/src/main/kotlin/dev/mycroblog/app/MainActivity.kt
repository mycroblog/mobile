package dev.mycroblog.app

import android.content.ActivityNotFoundException
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.core.net.toUri
import dagger.hilt.android.AndroidEntryPoint
import dev.mycroblog.app.navigation.MycroblogRoot
import dev.mycroblog.core.designsystem.MycroblogTheme

/** The one activity of the app (ADR-0010 §1). */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        val uriHandler = CustomTabsUriHandler(this)
        setContent {
            MycroblogTheme {
                CompositionLocalProvider(LocalUriHandler provides uriHandler) {
                    MycroblogRoot()
                }
            }
        }
    }
}

/** Opens the links of posts (mentions, hashtags, URLs) in a Custom Tab, the in-app browser. */
private class CustomTabsUriHandler(private val context: Context) : UriHandler {
    override fun openUri(uri: String) {
        try {
            CustomTabsIntent.Builder().build().launchUrl(context, uri.toUri())
        } catch (_: ActivityNotFoundException) {
            // No browser is installed: the link cannot be opened, and the post stays as it is.
        }
    }
}
