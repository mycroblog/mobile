package dev.mycroblog.feature.timeline.impl

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mycroblog.core.data.ReaderRepository
import dev.mycroblog.core.designsystem.NavigateUpButton
import dev.mycroblog.core.designsystem.PlaceholderScreen
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/** What Home shows before the reader signs in: whether a server was chosen, or null while that is being read. */
@HiltViewModel
class HomeViewModel @Inject constructor(readers: ReaderRepository) : ViewModel() {
    val hasServer: StateFlow<Boolean?> = readers.reader
        .map { it != null }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS), null)

    private companion object {
        const val STOP_TIMEOUT_MILLIS = 5_000L
    }
}

/** Home: before a server is chosen, the way to choose one ([onChooseServer]); after, the home timeline's place. */
@Composable
internal fun HomeRoute(onChooseServer: () -> Unit, onUp: (() -> Unit)?, viewModel: HomeViewModel = hiltViewModel()) {
    val hasServer by viewModel.hasServer.collectAsStateWithLifecycle()
    if (hasServer == false) {
        ChooseServerPrompt(onChooseServer = onChooseServer, onUp = onUp)
    } else {
        PlaceholderScreen(stringResource(R.string.home_timeline_title), onUp = onUp)
    }
}

/** Home before a server is chosen: what the app needs, and the action that chooses a server. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseServerPrompt(onChooseServer: () -> Unit, modifier: Modifier = Modifier, onUp: (() -> Unit)? = null) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.home_timeline_title)) },
                navigationIcon = { if (onUp != null) NavigateUpButton(onClick = onUp) },
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).verticalScroll(rememberScrollState()).padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        ) {
            Text(
                stringResource(R.string.home_choose_server_message),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
            )
            Button(onClick = onChooseServer) { Text(stringResource(R.string.home_choose_server)) }
        }
    }
}
