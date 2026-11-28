package dev.mycroblog.app.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mycroblog.core.data.ReaderRepository
import dev.mycroblog.core.data.TimelineSync
import dev.mycroblog.core.model.PublicTimeline
import dev.mycroblog.core.model.ServerAddress
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * The state of the root: which server-provided timelines the reader's server offers (ADR-0002 §6). At launch it prunes
 * the store (ADR-0008 §3) and reads the server's instance again.
 */
@HiltViewModel
class RootViewModel @Inject constructor(private val readers: ReaderRepository, sync: TimelineSync) : ViewModel() {
    /** The timelines offered, none before a server is chosen. */
    val offered: StateFlow<Set<PublicTimeline>> = readers.reader
        .map { it?.timelines.orEmpty().toSet() }
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptySet())

    init {
        viewModelScope.launch {
            sync.prune()
            readers.refreshInstance()
        }
    }

    /** Makes [address] the server the reader reads, which switches every timeline to it. */
    fun readServer(address: ServerAddress) {
        viewModelScope.launch { readers.choose(address) }
    }
}
