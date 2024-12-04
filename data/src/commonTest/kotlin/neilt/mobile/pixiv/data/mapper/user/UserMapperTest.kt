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

import neilt.mobile.pixiv.data.local.entities.user.UserEntity
import neilt.mobile.pixiv.data.remote.responses.auth.AuthResponse
import neilt.mobile.pixiv.data.remote.responses.auth.UserResponse
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class UserMapperTest {
    @Test
    fun `UserEntity toModel maps correctly`() {
        val userEntity = UserEntity(
            userId = "123",
            userName = "John Doe",
            userAccount = "john_doe",
            userMailAddress = "john@example.com",
            accessToken = "access_123",
            refreshToken = "refresh_123",
            tokenExpiresAt = System.currentTimeMillis() + 3600 * 1000,
            isActive = true,
        )

        val userModel = userEntity.toModel()

        assertEquals("User ID should be mapped correctly", userEntity.userId, userModel.id)
        assertEquals("User name should be mapped correctly", userEntity.userName, userModel.name)
        assertEquals("User account should be mapped correctly", userEntity.userAccount, userModel.account)
        assertEquals("User mail address should be mapped correctly", userEntity.userMailAddress, userModel.mailAddress)
        assertEquals("Access token should be mapped correctly", userEntity.accessToken, userModel.accessToken)
    }

    @Test
    fun `AuthResponse toEntity maps correctly`() {
        val authResponse = AuthResponse(
            accessToken = "access_123",
            refreshToken = "refresh_123",
            user = UserResponse(
                id = "123",
                name = "John Doe",
                account = "john_doe",
                mailAddress = "john@example.com",
            ),
            expiresIn = 3600,
            tokenType = "bearer",
            scope = "",
        )

        val userEntity = authResponse.toEntity()

        assertTrue("Mapped entity should be active", userEntity.isActive)
        assertEquals("Access token should be mapped correctly", authResponse.accessToken, userEntity.accessToken)
        assertEquals("Refresh token should be mapped correctly", authResponse.refreshToken, userEntity.refreshToken)
        assertEquals("User ID should be mapped correctly", authResponse.user.id, userEntity.userId)
        assertEquals("User name should be mapped correctly", authResponse.user.name, userEntity.userName)
        assertEquals("User account should be mapped correctly", authResponse.user.account, userEntity.userAccount)
        assertEquals(
            "User mail address should be mapped correctly",
            authResponse.user.mailAddress,
            userEntity.userMailAddress,
        )
    }
}
