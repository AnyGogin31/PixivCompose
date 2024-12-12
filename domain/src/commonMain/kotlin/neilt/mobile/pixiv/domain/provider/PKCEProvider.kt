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

import androidx.annotation.IntDef

interface PKCEProvider {
    companion object {
        internal const val CODE_VERIFIER_LENGTH = 32
        internal const val PROVISIONAL_ACCOUNT_BASE_URL =
            "https://app-api.pixiv.net/web/v1/provisional-accounts/create"

        @ChallengeMethod
        const val CHALLENGE_METHOD_S256 = 0

        @ChallengeMethod
        const val CHALLENGE_METHOD_PLAIN = 1

        internal const val CLIENT_ANDROID = "pixiv-android"
    }

    @IntDef(CHALLENGE_METHOD_S256, CHALLENGE_METHOD_PLAIN)
    @Retention(AnnotationRetention.SOURCE)
    annotation class ChallengeMethod

    fun getCodeVerifier(): String
    fun getCodeChallenge(
        @ChallengeMethod method: Int = CHALLENGE_METHOD_S256,
    ): String
    fun getProvisionalAccountUrl(
        @ChallengeMethod method: Int = CHALLENGE_METHOD_S256,
    ): String

    fun clearCodeVerifier()
}