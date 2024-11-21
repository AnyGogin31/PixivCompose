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

package neilt.mobile.pixiv.data.repositories.auth

import neilt.mobile.pixiv.data.remote.services.auth.AuthService
import neilt.mobile.pixiv.domain.repositories.auth.AuthRepository
import neilt.mobile.pixiv.domain.utils.PKCEUtil

class AuthRepositoryImpl(
    private val authService: AuthService,
) : AuthRepository {

    private companion object {
        const val CLIENT_ID = "MOBrBDS8blbauoSck0ZfDbtuzpyT"
        const val CLIENT_SECRET = "lsACyCD94FhDUtGTXi3QzcFE2uU1hqtDaKeqrdwj"
        const val AUTH_GRANT_TYPE = "authorization_code"
        const val CALL_BACK = "https://app-api.pixiv.net/web/v1/users/auth/pixiv/callback"
    }

    override suspend fun getAccessToken(code: String) {
        authService.requestToken(
            clientId = CLIENT_ID,
            clientSecret = CLIENT_SECRET,
            grantType = AUTH_GRANT_TYPE,
            codeAuthorization = code,
            codeVerifier = PKCEUtil.codeVerifier,
            redirectUri = CALL_BACK,
            includePolicy = true
        )
    }
}
