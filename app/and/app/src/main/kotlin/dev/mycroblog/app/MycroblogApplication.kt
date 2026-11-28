package dev.mycroblog.app

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import coil3.gif.AnimatedImageDecoder
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

/**
 * The application: the root of Hilt's components (ADR-0010 §5), WorkManager's configuration with Hilt's workers, and
 * the image loader of every image, which decodes animated GIF and WebP (custom emoji) too.
 */
@HiltAndroidApp
class MycroblogApplication :
    Application(),
    Configuration.Provider,
    SingletonImageLoader.Factory {
    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder().setWorkerFactory(workerFactory).build()

    override fun newImageLoader(context: PlatformContext): ImageLoader = ImageLoader.Builder(context)
        .components { add(AnimatedImageDecoder.Factory()) }
        .build()
}
