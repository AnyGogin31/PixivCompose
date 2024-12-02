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

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import neilt.mobile.pixiv.data.local.dao.UserDao
import neilt.mobile.pixiv.data.local.entities.user.UserEntity
import neilt.mobile.pixiv.data.remote.responses.auth.AuthResponse
import neilt.mobile.pixiv.data.remote.responses.auth.UserResponse
import neilt.mobile.pixiv.data.remote.services.auth.AuthService
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.mockito.kotlin.never
import org.mockito.kotlin.verify

class AuthRepositoryImplTest {
    private lateinit var repository: AuthRepositoryImpl
    private val authService: AuthService = mock()
    private val userDao: UserDao = mock()
    private val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = AuthRepositoryImpl(authService, userDao)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getAllUsers returns mapped UserModel list`() = runTest {
        val mockEntities = listOf(
            UserEntity(
                isActive = false,
                accessToken = "token1",
                refreshToken = "refresh1",
                userId = "id1",
                userName = "name1",
                userAccount = "account1",
                userMailAddress = "mail1",
            ),
        )
        `when`(userDao.getAllUsers()).thenReturn(mockEntities)

        val result = repository.getAllUsers()
        assertEquals(1, result.size)
        assertEquals("id1", result[0].id)
        assertEquals("name1", result[0].name)
    }

    @Test
    fun `setActiveUser deactivates all and activates the specified user`() = runTest {
        val userId = "id1"
        val mockEntities = listOf(
            UserEntity(
                isActive = false,
                accessToken = "token1",
                refreshToken = "refresh1",
                userId = userId,
                userName = "name1",
                userAccount = "account1",
                userMailAddress = "mail1",
            ),
        )
        `when`(userDao.getAllUsers()).thenReturn(mockEntities)

        val result = repository.setActiveUser(userId)
        assertTrue(result.isSuccess)
        verify(userDao).deactivateAllUsers()
        verify(userDao).activateUser(userId)
    }

    @Test
    fun `authorizeUser inserts new user when user count is below limit`() = runTest {
        val code = "test_code"
        val mockResponse = AuthResponse(
            accessToken = "access_token",
            refreshToken = "refresh_token",
            user = UserResponse(
                id = "id1",
                name = "name1",
                account = "account1",
                mailAddress = "mail1",
            ),
            expiresIn = 3600,
            tokenType = "bearer",
            scope = "",
        )
        `when`(userDao.getUserCount()).thenReturn(2)
        `when`(authService.requestForAuthorization(any()))
            .thenReturn(mockResponse)

        val result = repository.authorizeUser(code)
        assertTrue(result.isSuccess)
        verify(userDao).deactivateAllUsers()
        verify(userDao).insertUser(any())
    }

    @Test
    fun `authorizeUser fails when maximum users are reached`() = runTest {
        `when`(userDao.getUserCount()).thenReturn(3)

        val result = repository.authorizeUser("test_code")
        assertTrue(result.isFailure)
        assertEquals("Maximum number of users reached.", result.exceptionOrNull()?.message)
        verify(userDao, never()).insertUser(any())
    }
}
