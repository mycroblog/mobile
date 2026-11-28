package dev.mycroblog.core.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room3.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.mycroblog.core.data.store.StoreDatabase
import javax.inject.Qualifier
import javax.inject.Singleton

/** The preferences of the reader: the chosen server and the timelines it offers. */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ReaderPreferences

/**
 * The store and the preferences, one of each for the process (a second DataStore of the same file throws). A test
 * replaces this module, or builds [StoreDatabase] in memory with `Room.inMemoryDatabaseBuilder`.
 */
@Module
@InstallIn(SingletonComponent::class)
object StoreModule {
    @Provides
    @Singleton
    fun storeDatabase(@ApplicationContext context: Context): StoreDatabase =
        Room.databaseBuilder<StoreDatabase>(context, "store.db")
            .setDriver(BundledSQLiteDriver())
            // The store is a cache (ADR-0008 §1): a new schema starts it again rather than migrating it.
            .fallbackToDestructiveMigration(dropAllTables = true)
            .build()

    @Provides
    @Singleton
    @ReaderPreferences
    fun readerPreferences(@ApplicationContext context: Context): DataStore<Preferences> =
        PreferenceDataStoreFactory.create { context.preferencesDataStoreFile("reader") }
}
