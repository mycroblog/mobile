package dev.mycroblog.core.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import dev.mycroblog.core.api.client.InstanceClient
import dev.mycroblog.core.api.client.NetworkResult
import dev.mycroblog.core.api.models.InstanceAudience
import dev.mycroblog.core.model.PublicTimeline
import dev.mycroblog.core.model.Reader
import dev.mycroblog.core.model.ReaderContext
import dev.mycroblog.core.model.ServerAddress
import dev.mycroblog.core.model.TimelineAudience
import dev.mycroblog.core.model.offeredPublicTimelines
import io.ktor.client.engine.HttpClientEngine
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

/**
 * The server that a reader who has not signed in chose, remembered across launches, and the timelines it offers them
 * (ADR-0002 §6). Choosing another server switches the reader context.
 */
@Singleton
class ReaderRepository @Inject constructor(
    private val engine: HttpClientEngine,
    @param:ReaderPreferences private val preferences: DataStore<Preferences>,
) {
    /** The reader, or null before a server is chosen. */
    val reader: Flow<Reader?> = preferences.data
        .map { it.toReader() }
        .distinctUntilChanged()

    /** Remembers [address] as the reader's server, and reads the timelines it offers. */
    suspend fun choose(address: ServerAddress) {
        preferences.edit {
            if (it[ORIGIN] != address.origin) it.remove(TIMELINES)
            it[ORIGIN] = address.origin
        }
        refreshInstance()
    }

    /**
     * Reads the instance of the reader's server again and remembers the timelines it offers; false when it could not
     * be read, and the remembered ones stay.
     */
    suspend fun refreshInstance(): Boolean {
        val reader = reader.first() ?: return false
        val result = apiClient(engine, reader.server.origin).use { InstanceClient(it).getInstance() }
        if (result !is NetworkResult.Success) return false
        val instance = result.data
        val offered = offeredPublicTimelines(
            local = instance.timelines.local.toModel(),
            federated = instance.timelines.federated.toModel(),
            singleUser = instance.singleUser,
        )
        preferences.edit {
            // The server may have changed while the instance was read.
            if (it[ORIGIN] == reader.server.origin) it[TIMELINES] = offered.mapTo(HashSet()) { t -> t.apiName }
        }
        return true
    }

    /** Stops offering [timeline] in [context], as when it answered 404. */
    suspend fun hide(context: ReaderContext, timeline: PublicTimeline) {
        preferences.edit {
            if (it[ORIGIN] == context.origin) it[TIMELINES] = it[TIMELINES].orEmpty() - timeline.apiName
        }
    }

    private fun Preferences.toReader(): Reader? {
        val server = this[ORIGIN]?.let(ServerAddress::parse) ?: return null
        val names = this[TIMELINES].orEmpty()
        return Reader(server, PublicTimeline.entries.filter { it.apiName in names })
    }

    private companion object {
        val ORIGIN = stringPreferencesKey("server_origin")
        val TIMELINES = stringSetPreferencesKey("offered_timelines")
    }
}

private fun InstanceAudience.toModel(): TimelineAudience = when (this) {
    InstanceAudience.OFF -> TimelineAudience.OFF
    InstanceAudience.SIGNED_IN -> TimelineAudience.SIGNED_IN
    InstanceAudience.EVERYONE -> TimelineAudience.EVERYONE
    InstanceAudience.UNRECOGNIZED -> TimelineAudience.UNKNOWN
}
