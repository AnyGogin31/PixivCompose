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

import neilt.mobile.pixiv.data.provider.AccountManagerProvider
import neilt.mobile.pixiv.data.provider.TimeProvider
import neilt.mobile.pixiv.data.remote.requests.auth.AuthorizationRequest
import neilt.mobile.pixiv.data.remote.requests.auth.UpdateTokenRequest
import neilt.mobile.pixiv.data.remote.requests.auth.toFieldMap
import neilt.mobile.pixiv.data.remote.services.auth.AuthService
import neilt.mobile.pixiv.domain.models.user.UserModel
import neilt.mobile.pixiv.domain.provider.PKCEProvider
import neilt.mobile.pixiv.domain.repositories.auth.AuthRepository

class AuthRepositoryImpl(
    private val authService: AuthService,
    private val accountManagerProvider: AccountManagerProvider,
    private val timeProvider: TimeProvider,
    private val pkceProvider: PKCEProvider,
) : AuthRepository {
    private companion object {
        const val CLIENT_ID = "MOBrBDS8blbauoSck0ZfDbtuzpyT"
        const val CLIENT_SECRET = "lsACyCD94FhDUtGTXi3QzcFE2uU1hqtDaKeqrdwj"
        const val AUTH_GRANT_TYPE = "authorization_code"
        const val REFRESH_GRANT_TYPE = "refresh_token"
        const val CALL_BACK = "https://app-api.pixiv.net/web/v1/users/auth/pixiv/callback"

        const val ACCOUNT_TYPE = "neilt.mobile.pixiv"

        const val MAX_USER_COUNT = 3
    }

    override suspend fun getAllUsers(): List<UserModel> {
        return accountManagerProvider.getAccounts(ACCOUNT_TYPE).map { account ->
            UserModel(
                id = account.additionalData["user_id"] ?: "",
                accessToken = account.authToken ?: "",
                refreshToken = account.additionalData["refresh_token"] ?: "",
                tokenExpiresAt = account.additionalData["expires_at"]?.toLongOrNull() ?: 0L,
                name = account.name,
                account = account.additionalData["user_account"] ?: "",
                mailAddress = account.additionalData["user_mail_address"] ?: "",
            )
        }
    }

    override suspend fun getActiveUser(): UserModel? {
        return getAllUsers().firstOrNull()
    }

    override suspend fun setActiveUser(userId: String): Result<Unit> {
        val accounts = accountManagerProvider.getAccounts(ACCOUNT_TYPE)
        if (accounts.none { it.additionalData["user_id"] == userId }) {
            return Result.failure(Exception("User not found."))
        }

        accounts.forEach { account ->
            val isActive = (account.additionalData["user_id"] == userId)
            accountManagerProvider.updateAdditionalData(
                accountName = account.name,
                accountType = ACCOUNT_TYPE,
                key = "is_active",
                value = isActive.toString(),
            )
        }

        return Result.success(Unit)
    }

    override suspend fun authorizeUser(code: String): Result<Unit> {
        val accounts = accountManagerProvider.getAccounts(ACCOUNT_TYPE)
        if (accounts.size >= MAX_USER_COUNT) {
            pkceProvider.clearCodeVerifier()
            return Result.failure(Exception("Maximum number of users reached."))
        }

        val request = AuthorizationRequest(
            clientId = CLIENT_ID,
            clientSecret = CLIENT_SECRET,
            grantType = AUTH_GRANT_TYPE,
            codeAuthorization = code,
            codeVerifier = pkceProvider.getCodeVerifier(),
            redirectUri = CALL_BACK,
            includePolicy = true,
        )

        val response = authService.requestForAuthorization(request.toFieldMap())

        accountManagerProvider.addAccount(
            accountName = response.user.name,
            accountType = ACCOUNT_TYPE,
            accessToken = response.accessToken,
            refreshToken = response.refreshToken,
            expiresAt = timeProvider.currentTimeMillis() + response.expiresIn * 1000L,
            additionalData = mapOf(
                "user_id" to response.user.id,
                "user_account" to response.user.account,
                "user_mail_address" to response.user.mailAddress,
            ),
        )
        pkceProvider.clearCodeVerifier()

        return Result.success(Unit)
    }

    override suspend fun refreshActiveUserTokenIfNeeded(): Result<Unit> {
        val activeUser = getActiveUser() ?: return Result.failure(Exception("No active user."))
        val currentTime = timeProvider.currentTimeMillis()

        if (activeUser.tokenExpiresAt > currentTime) {
            return Result.success(Unit)
        }

        val request = UpdateTokenRequest(
            clientId = CLIENT_ID,
            clientSecret = CLIENT_SECRET,
            grantType = REFRESH_GRANT_TYPE,
            refreshToken = activeUser.refreshToken,
            includePolicy = true,
        )

        val response = authService.newRefreshToken(request.toFieldMap())

        accountManagerProvider.updateAuthToken(
            accountName = activeUser.name,
            accountType = ACCOUNT_TYPE,
            newToken = response.accessToken,
        )
        accountManagerProvider.updateAdditionalData(
            accountName = activeUser.name,
            accountType = ACCOUNT_TYPE,
            key = "refresh_token",
            value = response.refreshToken,
        )
        accountManagerProvider.updateAdditionalData(
            accountName = activeUser.name,
            accountType = ACCOUNT_TYPE,
            key = "expires_at",
            value = (currentTime + response.expiresIn * 1000L).toString(),
        )

        return Result.success(Unit)
    }
}
