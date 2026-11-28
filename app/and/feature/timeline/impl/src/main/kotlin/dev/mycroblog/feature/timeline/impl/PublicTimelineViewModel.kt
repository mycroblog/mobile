package dev.mycroblog.feature.timeline.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mycroblog.core.data.ReaderRepository
import dev.mycroblog.core.data.TimelineRepository
import dev.mycroblog.core.data.TimelineSync
import dev.mycroblog.core.model.PublicTimeline
import dev.mycroblog.core.model.ReaderContext
import dev.mycroblog.core.model.TimelineFailure
import dev.mycroblog.core.model.TimelineLoad
import dev.mycroblog.core.model.TimelinePosition
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * The state of a public timeline's screen ([timeline]) in the reader's context: the posts come from the store, and it
 * asks [TimelineSync] to load the newest posts when it opens and on pull to refresh, and older ones at the end of the
 * list. Choosing another server switches it to that server's timeline. [now] is the clock, for the rate limit.
 */
abstract class PublicTimelineViewModel(
    private val timeline: PublicTimeline,
    readers: ReaderRepository,
    private val repository: TimelineRepository,
    private val sync: TimelineSync,
    private val now: () -> Long = System::currentTimeMillis,
) : ViewModel() {
    private val state = MutableStateFlow(PublicTimelineUiState())
    val uiState: StateFlow<PublicTimelineUiState> = state.asStateFlow()

    private var context: ReaderContext? = null
    private var blockedUntil = 0L
    private var refreshJob: Job? = null
    private var olderJob: Job? = null

    init {
        viewModelScope.launch {
            readers.reader.map { it?.server }.distinctUntilChanged().collectLatest { server ->
                refreshJob?.cancel()
                olderJob?.cancel()
                blockedUntil = 0L
                state.value = PublicTimelineUiState(server = server)
                context = server?.let { ReaderContext(it.origin) }
                val current = context ?: return@collectLatest
                launch {
                    repository.posts(current, timeline).collect { posts -> state.update { it.copy(posts = posts) } }
                }
                launch {
                    repository.state(current, timeline).collect { stored ->
                        state.update { it.copy(loaded = stored.loaded, hasOlder = stored.hasOlder) }
                    }
                }
                val position = repository.position(current, timeline)
                state.update { it.copy(restore = position, positionReady = position == null) }
                refresh()
            }
        }
    }

    /** Loads the newest posts, as on open and on pull to refresh. */
    fun refresh() {
        val readerContext = context ?: return
        if (refreshJob?.isActive == true || isBlocked()) return
        state.update { it.copy(refreshing = true, refreshFailure = null) }
        refreshJob = viewModelScope.launch {
            val load = sync.refresh(readerContext, timeline)
            state.update { it.copy(refreshing = false, refreshFailure = load.failure()) }
        }
    }

    /** Loads the next older page, when the reader reaches the end of the list. */
    fun loadOlder() {
        val readerContext = context ?: return
        val current = state.value
        if (!current.hasOlder || current.olderFailure != null || olderJob?.isActive == true || isBlocked()) return
        state.update { it.copy(loadingOlder = true) }
        olderJob = viewModelScope.launch {
            val load = sync.loadOlder(readerContext, timeline)
            state.update { it.copy(loadingOlder = false, olderFailure = load.failure()) }
        }
    }

    /** Tries the older page that failed again. */
    fun retryOlder() {
        if (isBlocked()) return
        state.update { it.copy(olderFailure = null) }
        loadOlder()
    }

    /** The screen restored the saved position (or found its anchor gone); from now on the position is saved. */
    fun onPositionRestored() {
        state.update { it.copy(restore = null, positionReady = true) }
    }

    /** The reader scrolled: the post at the top is [anchorId], [offset] pixels past its top. */
    fun savePosition(anchorId: String, offset: Int) {
        val readerContext = context ?: return
        if (!state.value.positionReady) return
        viewModelScope.launch { repository.savePosition(readerContext, timeline, TimelinePosition(anchorId, offset)) }
    }

    fun toggleContent(postId: String) = state.update { it.copy(openContent = it.openContent.toggle(postId)) }

    fun toggleMedia(postId: String) = state.update { it.copy(shownMedia = it.shownMedia.toggle(postId)) }

    private fun isBlocked(): Boolean = now() < blockedUntil

    private fun TimelineLoad.failure(): TimelineFailure? {
        val failure = (this as? TimelineLoad.Failed)?.failure ?: return null
        if (failure is TimelineFailure.RateLimited) failure.retryAtMillis?.let(::blockUntil)
        return failure
    }

    /** Refuses requests until [retryAt], as the server's `Retry-After` asks. */
    private fun blockUntil(retryAt: Long) {
        blockedUntil = retryAt
        state.update { it.copy(rateLimited = true) }
        viewModelScope.launch {
            delay((retryAt - now()).coerceAtLeast(0))
            state.update { it.copy(rateLimited = false) }
        }
    }
}

private fun Set<String>.toggle(id: String): Set<String> = if (id in this) this - id else this + id

/** The local timeline's screen state. */
@HiltViewModel
class LocalTimelineViewModel @Inject constructor(
    readers: ReaderRepository,
    repository: TimelineRepository,
    sync: TimelineSync,
) : PublicTimelineViewModel(PublicTimeline.LOCAL, readers, repository, sync)

/** The federated timeline's screen state. */
@HiltViewModel
class FederatedTimelineViewModel @Inject constructor(
    readers: ReaderRepository,
    repository: TimelineRepository,
    sync: TimelineSync,
) : PublicTimelineViewModel(PublicTimeline.FEDERATED, readers, repository, sync)
