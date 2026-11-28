package dev.mycroblog.core.designsystem.post

import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import dev.mycroblog.core.model.Blurhash
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/** The size a BlurHash is decoded at: it holds a few colors, which the image stretches smoothly. */
private const val BLURHASH_SIZE = 32

/** The picture of [hash], decoded off the main thread; null while it decodes, or when there is no valid hash. */
@Composable
internal fun rememberBlurhashPainter(hash: String?): Painter? {
    val painter by produceState<Painter?>(initialValue = null, hash) {
        value = hash?.let {
            withContext(Dispatchers.Default) {
                Blurhash.decode(it, BLURHASH_SIZE, BLURHASH_SIZE)?.let { pixels ->
                    val bitmap = Bitmap.createBitmap(pixels, BLURHASH_SIZE, BLURHASH_SIZE, Bitmap.Config.ARGB_8888)
                    BitmapPainter(bitmap.asImageBitmap())
                }
            }
        }
    }
    return painter
}
