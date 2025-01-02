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

package neilt.mobile.pixiv.data.provider

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import java.io.IOException

class AndroidStorageProvider(private val context: Context) : StorageProvider {
    private companion object {
        private const val MAX_FILENAME_LENGTH = 200
        private const val DEFAULT_IMAGE_EXTENSION = ".jpg"
    }

    override fun uploadImage(image: ByteArray, fileName: String) {
        val normalizedImageName = normalizeFileName(fileName)
        val imageCollection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        } else {
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }

        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, normalizedImageName)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.Images.Media.IS_PENDING, 1)
                put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            }
        }

        val resolver = context.contentResolver
        val imageUri = resolver.insert(imageCollection, contentValues)
            ?: throw IOException("Failed to create media store record.")

        try {
            resolver.openOutputStream(imageUri)?.use { outputStream ->
                outputStream.write(image)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                contentValues.clear()
                contentValues.put(MediaStore.Images.Media.IS_PENDING, 0)
                resolver.update(imageUri, contentValues, null, null)
            }
        } catch (ioException: IOException) {
            resolver.delete(imageUri, null, null)
            throw IOException("Failed to save image file: ${ioException.message}", ioException)
        }
    }

    private fun normalizeFileName(fileName: String): String {
        var normalizedFileName = fileName.replace(Regex("[^a-zA-Z0-9._-]"), "_").trim()

        val dotIndex = normalizedFileName.lastIndexOf('.')
        val nameWithoutExtension = if (dotIndex != -1) normalizedFileName.substring(0, dotIndex) else normalizedFileName
        val extension = if (dotIndex != -1) normalizedFileName.substring(dotIndex) else DEFAULT_IMAGE_EXTENSION

        normalizedFileName = nameWithoutExtension.take(MAX_FILENAME_LENGTH) + extension

        return normalizedFileName
    }
}
