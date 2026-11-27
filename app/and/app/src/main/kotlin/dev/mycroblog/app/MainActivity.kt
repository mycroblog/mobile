package dev.mycroblog.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import dev.mycroblog.app.navigation.MycroblogRoot
import dev.mycroblog.core.designsystem.MycroblogTheme

/** The one activity of the app (ADR-0010 §1). */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            MycroblogTheme {
                MycroblogRoot()
            }
        }
    }
}
