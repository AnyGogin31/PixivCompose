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

package neilt.mobile.pixiv.features.auth

import android.accounts.AbstractAccountAuthenticator
import android.accounts.AccountAuthenticatorResponse
import android.accounts.AccountManager
import android.content.Context
import androidx.core.bundle.Bundle

class PixivAccountAuthenticator(context: Context) : AbstractAccountAuthenticator(context) {
    override fun editProperties(
        response: AccountAuthenticatorResponse?,
        accountType: String?,
    ): Bundle {
        throw UnsupportedOperationException()
    }

    override fun addAccount(
        response: AccountAuthenticatorResponse?,
        accountType: String?,
        authTokenType: String?,
        requiredFeatures: Array<out String>?,
        options: Bundle?,
    ): Bundle {
        return Bundle()
    }

    override fun confirmCredentials(
        response: AccountAuthenticatorResponse?,
        account: android.accounts.Account?,
        options: Bundle?,
    ): Bundle? {
        return null
    }

    override fun getAuthToken(
        response: AccountAuthenticatorResponse?,
        account: android.accounts.Account?,
        authTokenType: String?,
        options: Bundle?,
    ): Bundle {
        throw UnsupportedOperationException()
    }

    override fun getAuthTokenLabel(authTokenType: String?): String {
        return "Access Token"
    }

    override fun updateCredentials(
        response: AccountAuthenticatorResponse?,
        account: android.accounts.Account?,
        authTokenType: String?,
        options: Bundle?,
    ): Bundle {
        throw UnsupportedOperationException()
    }

    override fun hasFeatures(
        response: AccountAuthenticatorResponse?,
        account: android.accounts.Account?,
        features: Array<out String>?,
    ): Bundle {
        return Bundle().apply {
            putBoolean(AccountManager.KEY_BOOLEAN_RESULT, false)
        }
    }
}
