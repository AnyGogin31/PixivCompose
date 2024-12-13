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

import neilt.mobile.pixiv.data.provider.TimeProvider
import neilt.mobile.pixiv.data.sources.auth.AuthLocalDataSource
import neilt.mobile.pixiv.data.sources.auth.AuthRemoteDataSource
import neilt.mobile.pixiv.domain.models.user.UserModel
import neilt.mobile.pixiv.domain.provider.PKCEProvider
import neilt.mobile.pixiv.domain.repositories.auth.AuthRepository

class AuthRepositoryImpl(
    private val authLocalDataSource: AuthLocalDataSource,
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val timeProvider: TimeProvider,
    private val pkceProvider: PKCEProvider,
) : AuthRepository {
    private companion object {
        const val MAX_USER_COUNT = 3
    }

    override suspend fun getAllUsers(): List<UserModel> {
        return authLocalDataSource.getAllUsers()
    }

    override suspend fun getActiveUser(): UserModel? {
        return authLocalDataSource.getActiveUser()
    }

    override suspend fun setActiveUser(userId: String): Result<Unit> {
        return authLocalDataSource.setActiveUser(userId)
    }

    override suspend fun authorizeUser(code: String): Result<Unit> {
        return try {
            val accounts = getAllUsers()
            if (accounts.size >= MAX_USER_COUNT) {
                return Result.failure(Exception("Maximum number of users reached."))
            }

            val response = authRemoteDataSource.authorizeUser(
                codeAuthorization = code,
                codeVerifier = pkceProvider.getCodeVerifier(),
            )

            authLocalDataSource.addUser(
                userName = response.user.name,
                accessToken = response.accessToken,
                refreshToken = response.refreshToken,
                expiresAt = response.expiresIn,
                additionalData = mapOf(
                    "user_id" to response.user.id,
                    "user_account" to response.user.account,
                    "user_mail_address" to response.user.mailAddress,
                ),
            )
        } finally {
            pkceProvider.clearCodeVerifier()
        }
    }

    override suspend fun refreshActiveUserTokenIfNeeded(): Result<Unit> {
        val activeUser = getActiveUser() ?: return Result.failure(Exception("No active user."))
        val currentTime = timeProvider.currentTimeMillis()

        if (activeUser.tokenExpiresAt > currentTime) {
            return Result.success(Unit)
        }

        val response = authRemoteDataSource.refreshToken(activeUser.refreshToken)

        return authLocalDataSource.updateToken(
            userName = activeUser.name,
            accessToken = response.accessToken,
            refreshToken = response.refreshToken,
            expiresAt = response.expiresIn,
        )
    }
}
