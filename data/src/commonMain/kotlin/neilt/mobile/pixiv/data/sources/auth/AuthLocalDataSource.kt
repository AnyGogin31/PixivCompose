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
import neilt.mobile.pixiv.data.provider.AccountManagerProvider
import neilt.mobile.pixiv.data.provider.TimeProvider
import neilt.mobile.pixiv.domain.models.user.UserModel

class AuthLocalDataSource(
    private val accountManagerProvider: AccountManagerProvider,
    private val timeProvider: TimeProvider,
) {
    private companion object {
        const val ACCOUNT_TYPE = "neilt.mobile.pixiv"
    }

    suspend fun getAllUsers(): List<UserModel> {
        return withContext(Dispatchers.IO) {
            accountManagerProvider.getAccounts(ACCOUNT_TYPE).map { account ->
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
    }

    suspend fun getActiveUser(): UserModel? {
        return getAllUsers().firstOrNull()
    }

    suspend fun setActiveUser(userId: String): Result<Unit> {
        return withContext(Dispatchers.IO) {
            val accounts = accountManagerProvider.getAccounts(ACCOUNT_TYPE)
            if (accounts.none { it.additionalData["user_id"] == userId }) {
                return@withContext Result.failure(Exception("User not found."))
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

            return@withContext Result.success(Unit)
        }
    }

    suspend fun addUser(
        userName: String,
        accessToken: String,
        refreshToken: String,
        expiresAt: Int,
        additionalData: Map<String, String> = emptyMap(),
    ): Result<Unit> {
        return withContext(Dispatchers.IO) {
            accountManagerProvider.addAccount(
                accountName = userName,
                accountType = ACCOUNT_TYPE,
                accessToken = accessToken,
                refreshToken = refreshToken,
                expiresAt = timeProvider.currentTimeMillis() + expiresAt * 1000L,
                additionalData = additionalData,
            )

            Result.success(Unit)
        }
    }

    suspend fun updateToken(
        userName: String,
        accessToken: String,
        refreshToken: String,
        expiresAt: Int,
    ): Result<Unit> {
        return withContext(Dispatchers.IO) {
            accountManagerProvider.updateAuthToken(
                accountName = userName,
                accountType = ACCOUNT_TYPE,
                newToken = accessToken,
            )
            accountManagerProvider.updateAdditionalData(
                accountName = userName,
                accountType = ACCOUNT_TYPE,
                key = "refresh_token",
                value = refreshToken,
            )
            accountManagerProvider.updateAdditionalData(
                accountName = userName,
                accountType = ACCOUNT_TYPE,
                key = "expires_at",
                value = (timeProvider.currentTimeMillis() + expiresAt * 1000L).toString(),
            )

            Result.success(Unit)
        }
    }
}
