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

import java.security.MessageDigest
import java.security.SecureRandom
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

object PKCEUtil {

    // Default length for the code verifier
    private const val CODE_VERIFIER_LENGTH = 32

    // SecureRandom instance for cryptographically strong randomness
    private val secureRandom: SecureRandom by lazy { SecureRandom.getInstance("SHA1PRNG") }

    // Lazily initialized code verifier
    val codeVerifier: String by lazy { generateRandomString() }

    // Lazily initialized code challenge
    val codeChallenge: String by lazy { generateSHA256Base64(codeVerifier) }

    /**
     * Generates a random URL-safe string of the given length.
     * Used for creating the code verifier.
     *
     * @return A Base64 URL-safe string.
     */
    @OptIn(ExperimentalEncodingApi::class)
    private fun generateRandomString(): String {
        // Generate random bytes and encode to URL-safe Base64 without padding
        val bytes = ByteArray(CODE_VERIFIER_LENGTH).apply {
            secureRandom.nextBytes(this)
        }
        return Base64.UrlSafe.encode(bytes).trimEnd('=')
    }

    /**
     * Generates a SHA-256 hash of the input string and encodes it in Base64 URL-safe format.
     * Used for creating the code challenge.
     *
     * @param input The input string to hash.
     * @return The Base64-encoded SHA-256 hash.
     */
    @OptIn(ExperimentalEncodingApi::class)
    private fun generateSHA256Base64(input: String): String {
        // Compute SHA-256 hash of the code verifier
        val digest = MessageDigest
            .getInstance("SHA-256")
            .digest(input.toByteArray(Charsets.US_ASCII))

        // Encode the hash to URL-safe Base64 without padding
        return Base64.UrlSafe.encode(digest).trimEnd('=')
    }
}
