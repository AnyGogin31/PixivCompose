/*
 * MIT License
 *
 * Copyright (c) 2024 AnyGogin31
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package neilt.mobile.pixiv.features.settings.presentation.cache

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.ImageLoader
import coil3.compose.LocalPlatformContext
import neilt.mobile.pixiv.resources.Res
import neilt.mobile.pixiv.resources.clear_cache_button
import neilt.mobile.pixiv.resources.coil_cache_size_title
import org.jetbrains.compose.resources.stringResource
import kotlin.math.log10
import kotlin.math.pow

@Composable
internal fun CacheView() {
    val imageLoader = ImageLoader(LocalPlatformContext.current)
    var cacheSize by remember { mutableLongStateOf(0L) }

    val updateCacheSize = fun() {
        cacheSize = imageLoader.memoryCache?.size ?: 0L
        cacheSize += imageLoader.diskCache?.size ?: 0L
    }

    LaunchedEffect(Unit) {
        updateCacheSize()
    }

    val clearCache = fun() {
        imageLoader.memoryCache?.clear()
        imageLoader.diskCache?.clear()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(Res.string.coil_cache_size_title),
            style = MaterialTheme.typography.headlineMedium,
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Text(
            text = formatFileSize(cacheSize),
            style = MaterialTheme.typography.bodyLarge,
        )
        Spacer(modifier = Modifier.padding(16.dp))
        Button(
            onClick = {
                clearCache()
                updateCacheSize()
            },
        ) {
            Text(text = stringResource(Res.string.clear_cache_button))
        }
    }
}

private fun formatFileSize(size: Long): String {
    if (size <= 0) return "0 B"
    val units = arrayOf("B", "KB", "MB", "GB", "TB")
    val digitGroups = (log10(size.toDouble()) / log10(1024.0)).toInt()
    val formattedSize = size / 1024.0.pow(digitGroups.toDouble())

    // String format not working
    val formattedString = buildString {
        val wholePart = formattedSize.toInt()
        val decimalPart = ((formattedSize - wholePart) * 10).toInt()
        append(wholePart)
        if (decimalPart > 0) {
            append('.')
            append(decimalPart)
        }
        append(' ')
        append(units[digitGroups])
    }

    return formattedString
}
