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

import kotlinx.coroutines.test.runTest
import neilt.mobile.pixiv.domain.models.user.UserModel
import neilt.mobile.pixiv.domain.repositories.auth.AuthRepository
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class ActiveUserTokenProviderTest {
    private val authRepository: AuthRepository = mock()
    private lateinit var tokenProvider: ActiveUserTokenProvider

    @BeforeTest
    fun setup() {
        tokenProvider = ActiveUserTokenProvider(authRepository)
    }

    @Test
    fun `getToken returns access token when active user exists`() = runTest {
        val mockUser = UserModel(
            id = "id1",
            name = "name1",
            accessToken = "access_token",
            refreshToken = "refresh_token",
            tokenExpiresAt = System.currentTimeMillis() + 3600 * 1000,
            account = "account1",
            mailAddress = "address1",
        )
        `when`(authRepository.getActiveUser()).thenReturn(mockUser)
        `when`(authRepository.refreshActiveUserTokenIfNeeded()).thenReturn(Result.success(Unit))

        val token = tokenProvider.getToken()

        assertEquals("access_token", token)
        verify(authRepository).refreshActiveUserTokenIfNeeded()
        verify(authRepository).getActiveUser()
    }

    @Test
    fun `getToken returns null when no active user exists`() = runTest {
        `when`(authRepository.getActiveUser()).thenReturn(null)
        `when`(authRepository.refreshActiveUserTokenIfNeeded()).thenReturn(Result.success(Unit))

        val token = tokenProvider.getToken()

        assertNull(token)
        verify(authRepository).refreshActiveUserTokenIfNeeded()
        verify(authRepository).getActiveUser()
    }

    @Test
    fun `getToken handles failure in refreshActiveUserTokenIfNeeded`() = runTest {
        `when`(authRepository.refreshActiveUserTokenIfNeeded()).thenReturn(Result.failure(Exception("Refresh failed")))

        val token = tokenProvider.getToken()

        assertNull(token)
        verify(authRepository).refreshActiveUserTokenIfNeeded()
        verify(authRepository, never()).getActiveUser()
    }
}
