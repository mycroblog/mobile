package dev.mycroblog.spike15

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    private val route = mutableStateOf<String?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) route.value = routeOf(intent)
        else route.value = savedInstanceState.getString("route")
        setContent {
            MaterialTheme(colorScheme = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()) {
                App(route.value, onRoute = { route.value = it }, onNotify = ::postNotification)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("route", route.value)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        routeOf(intent)?.let { route.value = it }
    }

    private fun routeOf(intent: Intent?): String? = when (intent?.action) {
        Intent.ACTION_VIEW -> intent.data?.lastPathSegment?.let { "item/$it" }
        Intent.ACTION_SEND -> "shared/" + (intent.getStringExtra(Intent.EXTRA_TEXT) ?: "")
        else -> null
    }

    private fun postNotification() {
        val nm = getSystemService(NotificationManager::class.java)
        nm.createNotificationChannel(NotificationChannel("spike", "Spike", NotificationManager.IMPORTANCE_DEFAULT))
        val open = PendingIntent.getActivity(
            this, 0,
            Intent(Intent.ACTION_VIEW, Uri.parse("spike15://item/7"), this, MainActivity::class.java),
            PendingIntent.FLAG_IMMUTABLE,
        )
        nm.notify(1, android.app.Notification.Builder(this, "spike")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Spike15 notification")
            .setContentText("Open item 7")
            .setContentIntent(open)
            .setAutoCancel(true)
            .build())
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(route: String?, onRoute: (String?) -> Unit, onNotify: () -> Unit) {
    var draft by rememberSaveable { mutableStateOf("") }
    if (route != null) BackHandler { onRoute(null) }
    Scaffold(topBar = { TopAppBar(title = { Text(if (route == null) "Items" else route) }) }) { pad ->
        Column(Modifier.padding(pad).fillMaxSize()) {
            when {
                route == null -> {
                    OutlinedTextField(draft, { draft = it }, label = { Text("Note") },
                        modifier = Modifier.fillMaxWidth().padding(8.dp))
                    Button(onClick = onNotify, modifier = Modifier.padding(horizontal = 8.dp)) { Text("Post notification") }
                    LazyColumn {
                        items((1..200).toList()) { n ->
                            Text("Row $n", Modifier.fillMaxWidth().clickable { onRoute("item/$n") }.padding(16.dp))
                            HorizontalDivider()
                        }
                    }
                }
                route.startsWith("shared/") -> Text("Shared: " + route.removePrefix("shared/"), Modifier.padding(16.dp))
                else -> Text("Detail of " + route.removePrefix("item/"), Modifier.padding(16.dp))
            }
        }
    }
}
