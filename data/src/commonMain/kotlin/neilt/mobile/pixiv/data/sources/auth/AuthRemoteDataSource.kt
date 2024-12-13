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

package neilt.mobile.pixiv.data.sources.auth

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import neilt.mobile.pixiv.data.remote.responses.auth.AuthResponse
import neilt.mobile.pixiv.data.remote.services.auth.AuthService

class AuthRemoteDataSource(
    private val authService: AuthService,
) {
    private companion object {
        const val CLIENT_ID = "MOBrBDS8blbauoSck0ZfDbtuzpyT"
        const val CLIENT_SECRET = "lsACyCD94FhDUtGTXi3QzcFE2uU1hqtDaKeqrdwj"

        const val GRANT_TYPE_AUTH = "authorization_code"
        const val GRANT_TYPE_REFRESH = "refresh_token"

        const val REDIRECT_URL = "https://app-api.pixiv.net/web/v1/users/auth/pixiv/callback"
    }

    suspend fun authorizeUser(
        codeAuthorization: String,
        codeVerifier: String,
    ): AuthResponse {
        return withContext(Dispatchers.IO) {
            authService.requestForAuthorization(
                mapOf(
                    "client_id" to CLIENT_ID,
                    "client_secret" to CLIENT_SECRET,
                    "grant_type" to GRANT_TYPE_AUTH,
                    "code" to codeAuthorization,
                    "code_verifier" to codeVerifier,
                    "redirect_uri" to REDIRECT_URL,
                    "include_policy" to false,
                ),
            )
        }
    }

    suspend fun refreshToken(refreshToken: String): AuthResponse {
        return withContext(Dispatchers.IO) {
            authService.requestForAuthorization(
                mapOf(
                    "client_id" to CLIENT_ID,
                    "client_secret" to CLIENT_SECRET,
                    "grant_type" to GRANT_TYPE_REFRESH,
                    "refresh_token" to refreshToken,
                    "include_policy" to false,
                ),
            )
        }
    }
}
