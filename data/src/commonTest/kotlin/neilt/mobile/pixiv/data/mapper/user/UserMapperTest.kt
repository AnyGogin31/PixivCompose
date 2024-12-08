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

package neilt.mobile.pixiv.data.mapper.user

import neilt.mobile.pixiv.data.local.db.Users
import kotlin.test.Test
import kotlin.test.assertEquals

class UserMapperTest {
    @Test
    fun `UserEntity toModel maps correctly`() {
        val users = Users(
            user_id = "123",
            is_active = 1L,
            access_token = "access_123",
            refresh_token = "refresh_123",
            token_expires_at = 3600 * 1000,
            user_name = "John Doe",
            user_account = "john_doe",
            user_mail_address = "john@example.com",
        )

        val userModel = users.toModel()

        assertEquals(users.user_id, userModel.id, "User ID should be mapped correctly")
        assertEquals(users.user_name, userModel.name, "User name should be mapped correctly")
        assertEquals(users.user_account, userModel.account, "User account should be mapped correctly")
        assertEquals(users.user_mail_address, userModel.mailAddress, "User mail address should be mapped correctly")
        assertEquals(users.access_token, userModel.accessToken, "Access token should be mapped correctly")
        assertEquals(users.refresh_token, userModel.refreshToken, "Refresh token should be mapped correctly")
        assertEquals(users.token_expires_at, userModel.tokenExpiresAt, "Token expiration time should be mapped correctly")
    }
}
