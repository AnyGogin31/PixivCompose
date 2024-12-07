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

import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@OptIn(ExperimentalEncodingApi::class)
class PKCEUtilTest {
    private fun isUrlSafe(base64String: String): Boolean {
        return !base64String.contains('=') &&
            !base64String.contains('+') &&
            !base64String.contains('/')
    }

    @Test
    fun `codeVerifier is correctly generated`() {
        val codeVerifier = PKCEUtil.codeVerifier

        assertNotNull(codeVerifier, "Code verifier should not be null")
        assertTrue(codeVerifier.isNotEmpty(), "Code verifier should not be empty")
        assertTrue(isUrlSafe(codeVerifier), "Code verifier should be URL-safe")
        assertEquals(43, codeVerifier.length, "Code verifier should have the expected length")
    }

    @Test
    fun `codeChallenge is derived correctly from codeVerifier`() {
        val codeVerifier = PKCEUtil.codeVerifier
        val codeChallenge = PKCEUtil.codeChallenge

        assertNotNull(codeChallenge, "Code challenge should not be null")
        assertTrue(codeChallenge.isNotEmpty(), "Code challenge should not be empty")
        assertTrue(isUrlSafe(codeChallenge), "Code challenge should be URL-safe")

        val expectedCodeChallenge = computeSHA256(codeVerifier.encodeToByteArray())
            .let { Base64.UrlSafe.encode(it).trimEnd('=') }

        assertEquals(
            expectedCodeChallenge,
            codeChallenge,
            "Code challenge should match the expected value",
        )
    }

    @Test
    fun `codeVerifier and codeChallenge remain consistent across accesses`() {
        val firstVerifier = PKCEUtil.codeVerifier
        val secondVerifier = PKCEUtil.codeVerifier
        val firstChallenge = PKCEUtil.codeChallenge
        val secondChallenge = PKCEUtil.codeChallenge

        assertEquals(
            firstVerifier,
            secondVerifier,
            "Code verifier should remain consistent across accesses",
        )
        assertEquals(
            firstChallenge,
            secondChallenge,
            "Code challenge should remain consistent across accesses",
        )
    }

    @Test
    fun `codeChallenge initializes codeVerifier if not already generated`() {
        val codeChallenge = PKCEUtil.codeChallenge

        assertNotNull(
            PKCEUtil.codeVerifier,
            "Code verifier should be generated when accessing code challenge",
        )
        assertTrue(PKCEUtil.codeVerifier.isNotEmpty(), "Code verifier should not be empty")

        val expectedCodeChallenge = computeSHA256(PKCEUtil.codeVerifier.encodeToByteArray())
            .let { Base64.UrlSafe.encode(it).trimEnd('=') }

        assertEquals(
            expectedCodeChallenge,
            codeChallenge,
            "Code challenge should match the expected value",
        )
    }
}
