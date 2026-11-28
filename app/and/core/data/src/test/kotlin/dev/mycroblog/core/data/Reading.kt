package dev.mycroblog.core.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.room3.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import dev.mycroblog.core.data.store.StoreDatabase
import io.ktor.client.engine.HttpClientEngine
import java.io.File
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.job

/** A store in memory, on the SQLite of the JVM, as a test of the data layer reads and writes it. */
fun inMemoryStore(): StoreDatabase =
    Room.inMemoryDatabaseBuilder<StoreDatabase>().setDriver(BundledSQLiteDriver()).build()

/**
 * The preferences of the reader in [directory], open until [close]: closing them and opening them again on the same
 * directory is a relaunch of the app.
 */
class ReaderPreferences(private val directory: File) {
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    val store: DataStore<Preferences> =
        PreferenceDataStoreFactory.create(scope = scope) { directory.resolve("reader.preferences_pb") }

    suspend fun close() = scope.coroutineContext.job.cancelAndJoin()
}

/** The reader, the sync and the timelines of one launch of the app, over [engine], [database] and [preferences]. */
class Launch(engine: HttpClientEngine, val database: StoreDatabase, val preferences: ReaderPreferences) {
    val readers = ReaderRepository(engine, preferences.store)
    val sync = TimelineSync(engine, database, readers)
    val timelines = TimelineRepository(database)
}
