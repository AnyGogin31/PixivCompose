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

import kotlinx.serialization.json.Json
import java.io.File

class DesktopAccountManagerProvider : AccountManagerProvider {
    private val storageFile: File = File("accounts.json")
    private val json = Json { prettyPrint = true }

    private fun loadAccounts(): AccountsList {
        return if (storageFile.exists() && storageFile.length() > 0) {
            try {
                json.decodeFromString(AccountsList.serializer(), storageFile.readText())
            } catch (e: Exception) {
                AccountsList()
            }
        } else {
            AccountsList()
        }
    }

    private fun saveAccounts(accounts: AccountsList) {
        storageFile.writeText(json.encodeToString(AccountsList.serializer(), accounts))
    }

    override fun addAccount(
        accountName: String,
        accountType: String,
        accessToken: String,
        refreshToken: String,
        expiresAt: Long,
        additionalData: Map<String, String>,
    ): Boolean {
        val accountsList = loadAccounts()
        val account = AccountData(
            accountName,
            accountType,
            accessToken,
            additionalData.toMutableMap().apply {
                this["refresh_token"] = refreshToken
                this["expires_at"] = expiresAt.toString()
            },
        )
        accountsList.accounts.add(account)
        saveAccounts(accountsList)
        return true
    }

    override fun getAccounts(accountType: String): List<AccountData> {
        val accountsList = loadAccounts()
        return accountsList.accounts.filter { it.type == accountType }
    }

    override fun getAuthToken(accountName: String, accountType: String): String? {
        val accountsList = loadAccounts()
        return accountsList.accounts.find { it.name == accountName && it.type == accountType }?.authToken
    }

    override fun updateAuthToken(accountName: String, accountType: String, newToken: String) {
        val accountsList = loadAccounts()
        val account = accountsList.accounts.find { it.name == accountName && it.type == accountType }
        account?.let {
            accountsList.accounts.remove(account)
            val updatedAccount = account.copy(authToken = newToken)
            accountsList.accounts.add(updatedAccount)
            saveAccounts(accountsList)
        }
    }

    override fun updateAdditionalData(
        accountName: String,
        accountType: String,
        key: String,
        value: String,
    ) {
        val accountsList = loadAccounts()
        val account = accountsList.accounts.find { it.name == accountName && it.type == accountType }
        account?.let {
            accountsList.accounts.remove(account)
            val updatedAccount = account.copy(
                additionalData = account.additionalData.toMutableMap().apply {
                    this[key] = value
                },
            )
            accountsList.accounts.add(updatedAccount)
            saveAccounts(accountsList)
        }
    }
}
