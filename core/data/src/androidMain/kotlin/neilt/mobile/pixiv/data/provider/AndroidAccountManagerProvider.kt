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

package neilt.mobile.pixiv.data.provider

import android.accounts.Account
import android.accounts.AccountManager
import android.content.Context

class AndroidAccountManagerProvider(context: Context) : AccountManagerProvider {
    private val accountManager: AccountManager = AccountManager.get(context)

    override fun addAccount(
        accountName: String,
        accountType: String,
        accessToken: String,
        refreshToken: String,
        expiresAt: Long,
        additionalData: Map<String, String>,
    ): Boolean {
        val account = Account(accountName, accountType)
        val added = accountManager.addAccountExplicitly(account, null, null)
        if (added) {
            accountManager.setAuthToken(account, accountType, accessToken)
            accountManager.setUserData(account, "refresh_token", refreshToken)
            accountManager.setUserData(account, "expires_at", expiresAt.toString())
            additionalData.forEach { (key, value) ->
                accountManager.setUserData(account, key, value)
            }
        }

        return added
    }

    override fun getAccounts(accountType: String): List<AccountData> {
        return accountManager.getAccountsByType(accountType).map { account ->
            val authToken = accountManager.peekAuthToken(account, accountType)
            val additionalData = getUserDataKeys().associateWith { key ->
                accountManager.getUserData(account, key).orEmpty()
            }
            AccountData(
                name = account.name,
                type = account.type,
                authToken = authToken,
                additionalData = additionalData,
            )
        }
    }

    override fun getAuthToken(accountName: String, accountType: String): String? {
        val account = accountManager.accounts.find { it.name == accountName && it.type == accountType }
            ?: return null
        return accountManager.peekAuthToken(account, accountType)
    }

    override fun updateAuthToken(accountName: String, accountType: String, newToken: String) {
        val account = accountManager.accounts.find { it.name == accountName && it.type == accountType }
            ?: return
        accountManager.setAuthToken(account, accountType, newToken)
    }

    override fun updateAdditionalData(
        accountName: String,
        accountType: String,
        key: String,
        value: String,
    ) {
        val account = accountManager.accounts.find { it.name == accountName && it.type == accountType }
            ?: return
        accountManager.setUserData(account, key, value)
    }

    private fun getUserDataKeys(): List<String> {
        return listOf("refresh_token", "expires_at", "user_account", "user_mail_address", "user_id")
    }
}
