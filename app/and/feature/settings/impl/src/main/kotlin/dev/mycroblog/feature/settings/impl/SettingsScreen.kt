package dev.mycroblog.feature.settings.impl

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextDirection
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mycroblog.core.data.ReaderRepository
import dev.mycroblog.core.designsystem.NavigateUpButton
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/** What the settings show of the reader: the host of the server being read, or null before one is chosen. */
@HiltViewModel
class SettingsViewModel @Inject constructor(readers: ReaderRepository) : ViewModel() {
    val server: StateFlow<String?> = readers.reader
        .map { it?.server?.host }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS), null)

    private companion object {
        const val STOP_TIMEOUT_MILLIS = 5_000L
    }
}

/** The settings with their ViewModel. */
@Composable
internal fun SettingsRoute(
    onChangeServer: () -> Unit,
    onUp: (() -> Unit)?,
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    val server by viewModel.server.collectAsStateWithLifecycle()
    SettingsScreen(server = server, onChangeServer = onChangeServer, onUp = onUp)
}

/**
 * The settings: the server being read ([server], null before one is chosen) with the action that changes it
 * ([onChangeServer]). With [onUp], it has an Up button. Accounts come with signing in (#44).
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    server: String?,
    onChangeServer: () -> Unit,
    modifier: Modifier = Modifier,
    onUp: (() -> Unit)? = null,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.settings_title)) },
                navigationIcon = { if (onUp != null) NavigateUpButton(onClick = onUp) },
            )
        },
    ) { padding ->
        LazyColumn(contentPadding = padding) {
            item {
                ListItem(modifier = Modifier.semantics { heading() }) {
                    Text(
                        stringResource(R.string.settings_server_section),
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            }
            item {
                ListItem(
                    supportingContent = {
                        Text(
                            text = server ?: stringResource(R.string.settings_no_server),
                            // A host reads left to right in every language.
                            style = MaterialTheme.typography.bodyMedium.copy(
                                textDirection = if (server != null) TextDirection.Ltr else TextDirection.Content,
                            ),
                        )
                    },
                    modifier = Modifier.clickable(onClick = onChangeServer),
                ) {
                    Text(
                        stringResource(
                            if (server != null) R.string.settings_change_server else R.string.settings_choose_server,
                        ),
                    )
                }
            }
        }
    }
}
