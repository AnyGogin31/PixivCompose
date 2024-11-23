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

package neilt.mobile.pixiv.data.remote.common

import android.os.Build
import okhttp3.Interceptor
import okhttp3.Response
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class PixivHeaderInterceptor : Interceptor {

    private companion object {
        const val HASH_SECRET = "28c1fdd170a5204386cb1313c7077b34f83e4aaf4aa829ce78c231e05b0bae2c"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val (clientTime, clientHash) = generateHeaders()

        val requestWithHeaders = chain.request()
            .newBuilder()
            .addHeader("User-Agent", "PixivAndroidApp/6.66.1 (Android ${Build.VERSION.RELEASE})") // In the future, we could boldly reach out to Pixiv and discuss setting up a unique user agent to prevent requests from being blocked. It's mostly for aesthetic purposes.
            .addHeader("X-Client-Time", clientTime)
            .addHeader("X-Client-Hash", clientHash)
            .build()

        return chain.proceed(requestWithHeaders)
    }

    private fun generateHeaders(): Pair<String, String> {
        val currentTime = getCurrentTime()
        val hashInput = "$currentTime$HASH_SECRET"
        val clientHash = hashInput.md5ToHex()

        return Pair(currentTime, clientHash)
    }

    private fun getCurrentTime(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        return dateFormat.format(System.currentTimeMillis())
    }

    private fun String.md5ToHex(): String {
        val md = MessageDigest.getInstance("MD5")
        val digest = md.digest(this.toByteArray())
        return digest.joinToString("") { "%02x".format(it) }
    }
}
