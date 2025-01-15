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

import io.ktor.http.HeadersBuilder
import neilt.mobile.pixiv.domain.repositories.auth.TokenProvider
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull

class AuthorizationInterceptorTest {
    private lateinit var tokenProvider: TokenProvider

    @BeforeTest
    fun setup() {
        tokenProvider = mock(TokenProvider::class.java)
    }

    @Test
    fun `does not add Authorization header if AuthorizationRequired is false`() {
        val headers = HeadersBuilder().apply {
            append(AUTHORIZATION_REQUIRED_HEADER, "false")
        }

        headers.addAuthorizationInterceptor(tokenProvider)

        assertNull(headers["Authorization"], "Authorization header should not be added")
    }

    @Test
    fun `adds Authorization header if AuthorizationRequired is true`() {
        val token = "sample_token"
        `when`(tokenProvider.getToken()).thenReturn(token)

        val headers = HeadersBuilder().apply {
            append(AUTHORIZATION_REQUIRED_HEADER, "true")
        }

        headers.addAuthorizationInterceptor(tokenProvider)

        assertEquals("Bearer $token", headers["Authorization"], "Authorization header should be added correctly")
    }

    @Test
    fun `throws exception if token is missing`() {
        `when`(tokenProvider.getToken()).thenReturn(null)

        val headers = HeadersBuilder().apply {
            append(AUTHORIZATION_REQUIRED_HEADER, "true")
        }

        val exception = assertFailsWith<IllegalStateException> {
            headers.addAuthorizationInterceptor(tokenProvider)
        }

        assertEquals(
            "Authorization failed: Missing token for authenticated request",
            exception.message,
            "Exception message should match",
        )
    }
}
