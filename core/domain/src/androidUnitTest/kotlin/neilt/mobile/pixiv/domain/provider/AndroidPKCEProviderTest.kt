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

package neilt.mobile.pixiv.domain.provider

import java.security.MessageDigest
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@OptIn(ExperimentalEncodingApi::class)
class AndroidPKCEProviderTest {
    private lateinit var provider: AndroidPKCEProvider

    private fun isUrlSafe(base64String: String): Boolean {
        return !base64String.contains('=') &&
            !base64String.contains('+') &&
            !base64String.contains('/')
    }

    private fun computeSHA256(input: ByteArray): ByteArray {
        return MessageDigest.getInstance("SHA-256").digest(input)
    }

    @BeforeTest
    fun setup() {
        provider = AndroidPKCEProvider()
    }

    @Test
    fun `getCodeVerifier generates a valid code verifier`() {
        val codeVerifier = provider.getCodeVerifier()

        assertNotNull(codeVerifier, "Code verifier should not be null")
        assertTrue(codeVerifier.isNotEmpty(), "Code verifier should not be empty")
        assertTrue(
            codeVerifier.length in 43..44,
            "Code verifier length should be within the expected range",
        )
        assertTrue(
            isUrlSafe(codeVerifier),
            "Code verifier should be URL-safe",
        )
    }

    @Test
    fun `getCodeChallenge generates correct code challenge for S256 method`() {
        val codeVerifier = provider.getCodeVerifier()
        val codeChallenge = provider.getCodeChallenge(PKCEProvider.CHALLENGE_METHOD_S256)

        val expectedCodeChallenge = computeSHA256(codeVerifier.encodeToByteArray())
            .let { Base64.UrlSafe.encode(it).trimEnd('=') }

        assertNotNull(codeChallenge, "Code challenge should not be null")
        assertTrue(codeChallenge.isNotEmpty(), "Code challenge should not be empty")
        assertTrue(
            isUrlSafe(codeChallenge),
            "Code challenge should be URL-safe",
        )
        assertEquals(
            expectedCodeChallenge,
            codeChallenge,
            "Code challenge should match the expected value",
        )
    }

    @Test
    fun `getCodeChallenge returns plain code verifier for plain method`() {
        val codeVerifier = provider.getCodeVerifier()
        val codeChallenge = provider.getCodeChallenge(PKCEProvider.CHALLENGE_METHOD_PLAIN)

        assertEquals(
            codeVerifier,
            codeChallenge,
            "Code challenge should match code verifier for plain method",
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `getCodeChallenge throws exception for unsupported method`() {
        provider.getCodeChallenge(-1)
    }

    @Test
    fun `getProvisionalAccountUrl generates correct URL`() {
        val challengeMethod = PKCEProvider.CHALLENGE_METHOD_S256
        val codeVerifier = provider.getCodeVerifier()
        val expectedChallenge = computeSHA256(codeVerifier.encodeToByteArray())
            .let { Base64.UrlSafe.encode(it).trimEnd('=') }

        val url = provider.getProvisionalAccountUrl(challengeMethod)
        val expectedUrl = PKCEProvider.PROVISIONAL_ACCOUNT_BASE_URL +
            "?code_challenge=$expectedChallenge" +
            "&code_challenge_method=S256" +
            "&client=${PKCEProvider.CLIENT_ANDROID}"

        assertEquals(
            expectedUrl,
            url,
            "Generated URL should match expected format",
        )
    }

    @Test
    fun `clearCodeVerifier resets the code verifier`() {
        val initialVerifier = provider.getCodeVerifier()
        provider.clearCodeVerifier()

        val newVerifier = provider.getCodeVerifier()

        assertNotNull(newVerifier, "New code verifier should not be null")
        assertTrue(newVerifier.isNotEmpty(), "New code verifier should not be empty")
        assertNotEquals(
            initialVerifier,
            newVerifier,
            "New code verifier should be different after clearing",
        )
    }
}
