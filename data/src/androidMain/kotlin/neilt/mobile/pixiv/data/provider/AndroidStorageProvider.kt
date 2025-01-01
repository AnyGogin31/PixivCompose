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
import android.os.Environment
import android.provider.MediaStore
import java.io.File
import java.io.IOException

class AndroidStorageProvider(private val context: Context) : StorageProvider {
    private companion object {
        private const val MAX_FILENAME_LENGTH = 200
        private const val DEFAULT_IMAGE_EXTENSION = ".jpg"
    }

    override fun uploadImage(image: ByteArray, fileName: String) {
        if (!isExternalStorageWritable()) {
            throw IOException("External storage is not writable.")
        }

        val normalizedImageName = normalizeFileName(fileName)
        val picturesDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val newImageFile = File(picturesDirectory, normalizedImageName)

        try {
            newImageFile.outputStream().use { fileOutputStream ->
                fileOutputStream.write(image)
            }
            scanMedia(newImageFile)
        } catch (ioException: IOException) {
            throw IOException("Failed to save image file: ${ioException.message}", ioException)
        }
    }

    private fun scanMedia(imageFile: File) {
        val values = ContentValues().apply {
            put(MediaStore.Images.Media.DATA, imageFile.absolutePath)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        }
        context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
    }

    private fun normalizeFileName(fileName: String): String {
        var normalizedFileName = fileName.replace(Regex("[^a-zA-Z0-9._-]"), "_").trim()

        val dotIndex = normalizedFileName.lastIndexOf('.')
        val nameWithoutExtension = if (dotIndex != -1) normalizedFileName.substring(0, dotIndex) else normalizedFileName
        val extension = if (dotIndex != -1) normalizedFileName.substring(dotIndex) else DEFAULT_IMAGE_EXTENSION

        normalizedFileName = nameWithoutExtension.take(MAX_FILENAME_LENGTH) + extension

        return normalizedFileName
    }

    private fun isExternalStorageWritable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }
}
