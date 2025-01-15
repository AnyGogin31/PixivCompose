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

import neilt.mobile.pixiv.domain.provider.PKCEProvider.Companion.CLIENT_ANDROID
import neilt.mobile.pixiv.domain.provider.PKCEProvider.Companion.CODE_VERIFIER_LENGTH
import neilt.mobile.pixiv.domain.provider.PKCEProvider.Companion.PROVISIONAL_ACCOUNT_BASE_URL
import java.security.MessageDigest
import java.security.SecureRandom
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class AndroidPKCEProvider : PKCEProvider {
    private var codeVerifier: String? = null

    override fun getCodeVerifier(): String =
        codeVerifier ?: generateRandomString().also { codeVerifier = it }

    override fun getCodeChallenge(
        @PKCEProvider.ChallengeMethod method: Int,
    ): String =
        getCodeVerifier().let { verifier ->
            val challenge = when (method) {
                PKCEProvider.CHALLENGE_METHOD_S256 -> generateSHA256Base64(verifier)
                PKCEProvider.CHALLENGE_METHOD_PLAIN -> verifier
                else -> throw IllegalArgumentException("Unsupported code challenge method")
            }
            challenge
        }

    override fun getProvisionalAccountUrl(
        @PKCEProvider.ChallengeMethod method: Int,
    ): String {
        val challenge = getCodeChallenge(method)
        val methodName = when (method) {
            PKCEProvider.CHALLENGE_METHOD_S256 -> "S256"
            PKCEProvider.CHALLENGE_METHOD_PLAIN -> "plain"
            else -> throw IllegalArgumentException("Unsupported code challenge method")
        }
        return PROVISIONAL_ACCOUNT_BASE_URL +
            "?code_challenge=$challenge" +
            "&code_challenge_method=$methodName" +
            "&client=$CLIENT_ANDROID"
    }

    override fun clearCodeVerifier() {
        codeVerifier = null
    }

    @OptIn(ExperimentalEncodingApi::class)
    private fun generateRandomString(): String {
        val secureRandom = SecureRandom.getInstance("SHA1PRNG")
        val bytes = ByteArray(CODE_VERIFIER_LENGTH).apply {
            secureRandom.nextBytes(this)
        }
        return Base64.UrlSafe.encode(bytes).trimEnd('=')
    }

    @OptIn(ExperimentalEncodingApi::class)
    private fun generateSHA256Base64(input: String): String {
        val digest = MessageDigest
            .getInstance("SHA-256")
            .digest(input.encodeToByteArray())
        return Base64.UrlSafe.encode(digest).trimEnd('=')
    }
}
