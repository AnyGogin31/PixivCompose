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

package neilt.mobile.pixiv.domain.utils

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import java.security.MessageDigest
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class PKCEUtilTest {
    private fun isUrlSafe(base64String: String): Boolean {
        return !base64String.contains('=') &&
            !base64String.contains('+') &&
            !base64String.contains('/')
    }

    @Test
    fun `codeVerifier is correctly generated`() {
        val codeVerifier = PKCEUtil.codeVerifier

        assertNotNull("Code verifier should not be null", codeVerifier)
        assertTrue("Code verifier should not be empty", codeVerifier.isNotEmpty())
        assertTrue("Code verifier should be URL-safe", isUrlSafe(codeVerifier))
        assertEquals(
            "Code verifier should have the expected length",
            43,
            codeVerifier.length,
        )
    }

    @OptIn(ExperimentalEncodingApi::class)
    @Test
    fun `codeChallenge is derived correctly from codeVerifier`() {
        val codeVerifier = PKCEUtil.codeVerifier
        val codeChallenge = PKCEUtil.codeChallenge

        assertNotNull("Code challenge should not be null", codeChallenge)
        assertTrue("Code challenge should not be empty", codeChallenge.isNotEmpty())
        assertTrue("Code challenge should be URL-safe", isUrlSafe(codeChallenge))

        val expectedCodeChallenge = MessageDigest.getInstance("SHA-256")
            .digest(codeVerifier.toByteArray(Charsets.US_ASCII))
            .let { Base64.UrlSafe.encode(it).trimEnd('=') }

        assertEquals(
            "Code challenge should match the expected value",
            expectedCodeChallenge,
            codeChallenge,
        )
    }

    @Test
    fun `codeVerifier and codeChallenge remain consistent across accesses`() {
        val firstVerifier = PKCEUtil.codeVerifier
        val secondVerifier = PKCEUtil.codeVerifier
        val firstChallenge = PKCEUtil.codeChallenge
        val secondChallenge = PKCEUtil.codeChallenge

        assertEquals(
            "Code verifier should remain consistent across accesses",
            firstVerifier,
            secondVerifier,
        )
        assertEquals(
            "Code challenge should remain consistent across accesses",
            firstChallenge,
            secondChallenge,
        )
    }

    @OptIn(ExperimentalEncodingApi::class)
    @Test
    fun `codeChallenge initializes codeVerifier if not already generated`() {
        val codeChallenge = PKCEUtil.codeChallenge

        assertNotNull(
            "Code verifier should be generated when accessing code challenge",
            PKCEUtil.codeVerifier,
        )
        assertTrue("Code verifier should not be empty", PKCEUtil.codeVerifier.isNotEmpty())

        val expectedCodeChallenge = MessageDigest.getInstance("SHA-256")
            .digest(PKCEUtil.codeVerifier.toByteArray(Charsets.US_ASCII))
            .let { Base64.UrlSafe.encode(it).trimEnd('=') }

        assertEquals(
            "Code challenge should match the expected value",
            expectedCodeChallenge,
            codeChallenge,
        )
    }
}
