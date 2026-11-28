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
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation3.runtime.NavKey
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mycroblog.core.data.ReaderRepository
import dev.mycroblog.core.designsystem.NavigateUpButton
import dev.mycroblog.core.model.PublicTimeline
import dev.mycroblog.feature.timeline.api.FederatedTimelineKey
import dev.mycroblog.feature.timeline.api.LocalTimelineKey
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/** What Home shows before the reader signs in. */
sealed interface HomeUiState {
    /** The remembered reader is being read. */
    data object Loading : HomeUiState

    /** No server is chosen yet. */
    data object NoServer : HomeUiState

    /** A server is read without an account; [firstTimeline] is the first timeline it offers, or null for none. */
    data class Reading(val firstTimeline: PublicTimeline?) : HomeUiState
}

@HiltViewModel
class HomeViewModel @Inject constructor(readers: ReaderRepository) : ViewModel() {
    val uiState: StateFlow<HomeUiState> = readers.reader
        .map { reader ->
            if (reader == null) HomeUiState.NoServer else HomeUiState.Reading(reader.timelines.firstOrNull())
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS), HomeUiState.Loading)

    private companion object {
        const val STOP_TIMEOUT_MILLIS = 5_000L
    }
}

/**
 * Home before the reader signs in: before a server is chosen, the way to choose one ([onChooseServer]); after, what
 * Home will hold once they sign in, and the way to the first timeline the server offers ([onOpenTimeline]).
 */
@Composable
internal fun HomeRoute(
    onChooseServer: () -> Unit,
    onOpenTimeline: (NavKey) -> Unit,
    onUp: (() -> Unit)?,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    HomeScreen(uiState, onChooseServer, onOpenTimeline, onUp = onUp)
}

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onChooseServer: () -> Unit,
    onOpenTimeline: (NavKey) -> Unit,
    modifier: Modifier = Modifier,
    onUp: (() -> Unit)? = null,
) {
    when (uiState) {
        HomeUiState.Loading -> HomeMessage(message = null, modifier = modifier, onUp = onUp)

        HomeUiState.NoServer -> HomeMessage(
            message = stringResource(R.string.home_choose_server_message),
            action = stringResource(R.string.home_choose_server),
            onAction = onChooseServer,
            modifier = modifier,
            onUp = onUp,
        )

        is HomeUiState.Reading -> {
            val timeline = uiState.firstTimeline
            HomeMessage(
                message = stringResource(R.string.home_signed_out_message),
                action = timeline?.let {
                    stringResource(
                        when (it) {
                            PublicTimeline.LOCAL -> R.string.home_open_local
                            PublicTimeline.FEDERATED -> R.string.home_open_federated
                        },
                    )
                },
                onAction = {
                    when (timeline) {
                        PublicTimeline.LOCAL -> onOpenTimeline(LocalTimelineKey)
                        PublicTimeline.FEDERATED -> onOpenTimeline(FederatedTimelineKey)
                        null -> Unit
                    }
                },
                modifier = modifier,
                onUp = onUp,
            )
        }
    }
}

/** Home's title, a [message] in the middle (none while loading), and an optional [action]. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeMessage(
    message: String?,
    modifier: Modifier = Modifier,
    action: String? = null,
    onAction: () -> Unit = {},
    onUp: (() -> Unit)? = null,
) {
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
            if (message != null) {
                Text(
                    message,
                    style = MaterialTheme.typography.bodyLarge.copy(textDirection = TextDirection.Content),
                    textAlign = TextAlign.Center,
                )
            }
            if (action != null) Button(onClick = onAction) { Text(action) }
        }
    }
}
