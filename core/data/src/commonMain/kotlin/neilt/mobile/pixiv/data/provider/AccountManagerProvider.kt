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

import kotlinx.serialization.Serializable

interface AccountManagerProvider {
    fun addAccount(
        accountName: String,
        accountType: String,
        accessToken: String,
        refreshToken: String,
        expiresAt: Long,
        additionalData: Map<String, String> = emptyMap(),
    ): Boolean

    fun getAccounts(accountType: String): List<AccountData>

    fun getAuthToken(accountName: String, accountType: String): String?

    fun updateAuthToken(
        accountName: String,
        accountType: String,
        newToken: String,
    )

    fun updateAdditionalData(
        accountName: String,
        accountType: String,
        key: String,
        value: String,
    )
}

@Serializable
data class AccountData(
    val name: String,
    val type: String,
    val authToken: String?,
    val additionalData: Map<String, String>,
)

@Serializable
data class AccountsList(
    val accounts: MutableList<AccountData> = mutableListOf(),
)
