package kr.kimrasng.app.music_player.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.LruCache
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

object ImageCache {
    private val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()
    private val cacheSize = maxMemory / 4

    private val memoryCache = object : LruCache<String, Bitmap>(cacheSize) {
        override fun sizeOf(key: String, bitmap: Bitmap): Int {
            return bitmap.byteCount / 1024
        }
    }

    fun get(url: String): Bitmap? = memoryCache.get(url)

    fun put(url: String, bitmap: Bitmap) {
        memoryCache.put(url, bitmap)
    }
}

@Composable
fun WebpImageFromUrl(
    url: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale,
    alignment: Alignment
) {
    var bitmap by remember(url) { mutableStateOf(ImageCache.get(url)) }

    LaunchedEffect(url) {
        if (bitmap == null) {
            val cached = ImageCache.get(url)
            if (cached != null) {
                bitmap = cached
            } else {
                val loaded = loadWebpBitmap(url)
                if (loaded != null) {
                    ImageCache.put(url, loaded)
                    bitmap = loaded
                }
            }
        }
    }

    if (bitmap != null) {
        Image(
            bitmap = bitmap!!.asImageBitmap(),
            contentDescription = null,
            modifier = modifier,
            contentScale = contentScale,
            alignment = alignment
        )
    } else {
        Box(modifier = modifier)
    }
}

suspend fun loadWebpBitmap(url: String): Bitmap? = withContext(Dispatchers.IO) {
    try {
        val bytes = URL(url).openStream().use { it.readBytes() }

        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
        }
        BitmapFactory.decodeByteArray(bytes, 0, bytes.size, options)

        options.inSampleSize = calculateInSampleSize(options, 500, 500)
        options.inJustDecodeBounds = false

        BitmapFactory.decodeByteArray(bytes, 0, bytes.size, options)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
    val (height: Int, width: Int) = options.run { outHeight to outWidth }
    var inSampleSize = 1

    if (height > reqHeight || width > reqWidth) {
        val halfHeight: Int = height / 2
        val halfWidth: Int = width / 2

        while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
            inSampleSize *= 2
        }
    }
    return inSampleSize
}
