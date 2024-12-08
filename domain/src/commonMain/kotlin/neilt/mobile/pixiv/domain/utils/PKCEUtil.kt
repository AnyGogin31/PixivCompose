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

/**
 * Utility object for generating PKCE (Proof Key for Code Exchange) components.
 * Provides secure methods for generating code verifiers and their corresponding code challenges.
 */
object PKCEUtil {
    /** Default length for the code verifier in bytes. */
    private const val CODE_VERIFIER_LENGTH = 32

    /** Instance of [SecureRandomProvider] for cryptographically strong random number generation. */
    private val secureRandom: SecureRandomProvider by lazy { SecureRandomProvider() }

    /** Lazily initialized PKCE code verifier. */
    val codeVerifier: String by lazy { generateRandomString() }

    /** Lazily initialized PKCE code challenge derived from the code verifier. */
    val codeChallenge: String by lazy { generateSHA256Base64(codeVerifier) }

    /**
     * Generates a random URL-safe string of the specified length.
     * This is used as the PKCE code verifier.
     *
     * @return A Base64 URL-safe string without padding.
     */
    @OptIn(ExperimentalEncodingApi::class)
    private fun generateRandomString(): String {
        val bytes = ByteArray(CODE_VERIFIER_LENGTH).apply {
            secureRandom.generateSecureRandomBytes(this)
        }
        return Base64.UrlSafe.encode(bytes).trimEnd('=')
    }

    /**
     * Generates a SHA-256 hash of the input string and encodes it in Base64 URL-safe format.
     * This is used to create the PKCE code challenge.
     *
     * @param input The input string to hash.
     * @return The Base64-encoded SHA-256 hash.
     */
    @OptIn(ExperimentalEncodingApi::class)
    private fun generateSHA256Base64(input: String): String {
        val digest = computeSHA256(input.encodeToByteArray())
        return Base64.UrlSafe.encode(digest).trimEnd('=')
    }
}

/**
 * Platform-specific provider for cryptographically secure random number generation.
 */
internal expect class SecureRandomProvider() {
    /**
     * Fills the given byte array with cryptographically secure random bytes.
     *
     * @param bytes The byte array to fill.
     */
    internal fun generateSecureRandomBytes(bytes: ByteArray)
}

/**
 * Computes the SHA-256 hash of the given input byte array.
 *
 * @param input The byte array to hash.
 * @return The computed SHA-256 hash.
 */
internal expect fun computeSHA256(input: ByteArray): ByteArray
