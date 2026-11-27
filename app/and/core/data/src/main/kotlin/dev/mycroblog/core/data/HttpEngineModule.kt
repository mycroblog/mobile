package dev.mycroblog.core.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import javax.inject.Singleton

/**
 * The engine of every HttpClient of the app: OkHttp, one for the process so that its connections are shared. A test
 * replaces this module (`@TestInstallIn(replaces = [HttpEngineModule::class])`) to give another engine.
 */
@Module
@InstallIn(SingletonComponent::class)
object HttpEngineModule {
    @Provides
    @Singleton
    fun httpClientEngine(): HttpClientEngine = OkHttp.create()
}
