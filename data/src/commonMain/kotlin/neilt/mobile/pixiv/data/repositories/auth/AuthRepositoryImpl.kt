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

import neilt.mobile.pixiv.data.local.db.PixivDatabase
import neilt.mobile.pixiv.data.mapper.user.toModel
import neilt.mobile.pixiv.data.provider.TimeProvider
import neilt.mobile.pixiv.data.remote.requests.auth.AuthorizationRequest
import neilt.mobile.pixiv.data.remote.requests.auth.UpdateTokenRequest
import neilt.mobile.pixiv.data.remote.requests.auth.toFieldMap
import neilt.mobile.pixiv.data.remote.services.auth.AuthService
import neilt.mobile.pixiv.domain.models.user.UserModel
import neilt.mobile.pixiv.domain.repositories.auth.AuthRepository
import neilt.mobile.pixiv.domain.utils.PKCEUtil

class AuthRepositoryImpl(
    private val authService: AuthService,
    private val database: PixivDatabase,
    private val timeProvider: TimeProvider,
) : AuthRepository {
    private companion object {
        const val CLIENT_ID = "MOBrBDS8blbauoSck0ZfDbtuzpyT"
        const val CLIENT_SECRET = "lsACyCD94FhDUtGTXi3QzcFE2uU1hqtDaKeqrdwj"
        const val AUTH_GRANT_TYPE = "authorization_code"
        const val REFRESH_GRANT_TYPE = "refresh_token"
        const val CALL_BACK = "https://app-api.pixiv.net/web/v1/users/auth/pixiv/callback"

        const val MAX_USER_COUNT = 3
    }

    override suspend fun getAllUsers(): List<UserModel> =
        database.pixivDatabaseQueries.getAllUsers().executeAsList().map { it.toModel() }

    override suspend fun getActiveUser() =
        database.pixivDatabaseQueries.getActiveUser().executeAsOneOrNull()?.toModel()

    override suspend fun setActiveUser(userId: String): Result<Unit> {
        val user = database.pixivDatabaseQueries.getAllUsers().executeAsList()
            .find { it.user_id == userId }
        if (user == null) {
            return Result.failure(Exception("User not found."))
        }

        database.pixivDatabaseQueries.deactivateAllUsers()
        database.pixivDatabaseQueries.activateUser(userId)

        return Result.success(Unit)
    }

    override suspend fun authorizeUser(code: String): Result<Unit> {
        val userCount = database.pixivDatabaseQueries.getUserCount().executeAsOne()
        if (userCount >= MAX_USER_COUNT) {
            return Result.failure(Exception("Maximum number of users reached."))
        }

        val request = AuthorizationRequest(
            clientId = CLIENT_ID,
            clientSecret = CLIENT_SECRET,
            grantType = AUTH_GRANT_TYPE,
            codeAuthorization = code,
            codeVerifier = PKCEUtil.codeVerifier,
            redirectUri = CALL_BACK,
            includePolicy = true,
        )

        val response = authService.requestForAuthorization(request.toFieldMap())

        database.pixivDatabaseQueries.deactivateAllUsers()
        database.pixivDatabaseQueries.insertUser(
            user_id = response.user.id,
            is_active = 1,
            access_token = response.accessToken,
            refresh_token = response.refreshToken,
            token_expires_at = timeProvider.currentTimeMillis() + response.expiresIn * 1000L,
            user_name = response.user.name,
            user_account = response.user.account,
            user_mail_address = response.user.mailAddress,
        )

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

        database.pixivDatabaseQueries.updateUser(
            user_id = activeUser.id,
            access_token = response.accessToken,
            refresh_token = response.refreshToken,
            token_expires_at = timeProvider.currentTimeMillis() + response.expiresIn * 1000L,
        )

        return Result.success(Unit)
    }
}
