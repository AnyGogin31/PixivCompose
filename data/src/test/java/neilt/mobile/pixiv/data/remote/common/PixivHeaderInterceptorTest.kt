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
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.mockito.kotlin.argThat

class PixivHeaderInterceptorTest {

    private lateinit var interceptor: PixivHeaderInterceptor

    @Before
    fun setup() {
        interceptor = PixivHeaderInterceptor()
    }

    @Test
    fun `request contains all required headers`() {
        val mockRequest = Request.Builder()
            .url("http://example.com")
            .build()

        val mockChain = mock(Interceptor.Chain::class.java).apply {
            `when`(request()).thenReturn(mockRequest)
            `when`(proceed(any())).thenReturn(createMockResponse(mockRequest))
        }

        interceptor.intercept(mockChain)

        verify(mockChain).proceed(argThat {
            header("User-Agent") == "PixivAndroidApp/6.66.1 (Android ${Build.VERSION.RELEASE})" &&
                    header("X-Client-Time") != null &&
                    header("X-Client-Hash") != null
        })
    }

    private fun createMockResponse(request: Request): Response {
        return Response.Builder()
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .code(200)
            .message("OK")
            .build()
    }
}
