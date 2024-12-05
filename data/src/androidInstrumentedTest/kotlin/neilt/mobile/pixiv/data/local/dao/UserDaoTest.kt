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

package neilt.mobile.pixiv.data.local.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import neilt.mobile.pixiv.data.local.db.PixivDatabase
import neilt.mobile.pixiv.data.local.entities.user.UserEntity
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserDaoTest {
    private lateinit var database: PixivDatabase
    private lateinit var userDao: UserDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            PixivDatabase::class.java,
        ).allowMainThreadQueries().build()
        userDao = database.userDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertUser_and_getUserCount() = runBlocking {
        val user = UserEntity(
            userId = "123",
            userName = "John Doe",
            userAccount = "john_doe",
            userMailAddress = "john@example.com",
            accessToken = "access_123",
            refreshToken = "refresh_123",
            tokenExpiresAt = System.currentTimeMillis() + 3600 * 1000,
            isActive = false,
        )

        userDao.insertUser(user)
        val count = userDao.getUserCount()

        assertEquals(1, count)
    }

    @Test
    fun getAllUsers_returnsAllInsertedUsers() = runBlocking {
        val users = listOf(
            UserEntity(
                userId = "1",
                userName = "User1",
                userAccount = "user1",
                userMailAddress = "user1@mail.com",
                accessToken = "token1",
                refreshToken = "refresh1",
                tokenExpiresAt = System.currentTimeMillis() + 3600 * 1000,
                isActive = false,
            ),
            UserEntity(
                userId = "2",
                userName = "User2",
                userAccount = "user2",
                userMailAddress = "user2@mail.com",
                accessToken = "token2",
                refreshToken = "refresh2",
                tokenExpiresAt = System.currentTimeMillis() + 7200 * 1000,
                isActive = false,
            ),
        )

        users.forEach { userDao.insertUser(it) }
        val retrievedUsers = userDao.getAllUsers()

        assertEquals(2, retrievedUsers.size)
        assertTrue("User 1 should exist in the database", retrievedUsers.any { it.userId == "1" })
        assertTrue("User 2 should exist in the database", retrievedUsers.any { it.userId == "2" })
    }

    @Test
    fun getActiveUser_returnsCorrectUser() = runBlocking {
        val users = listOf(
            UserEntity(
                userId = "1",
                userName = "User1",
                userAccount = "user1",
                userMailAddress = "user1@mail.com",
                accessToken = "token1",
                refreshToken = "refresh1",
                tokenExpiresAt = System.currentTimeMillis() + 3600 * 1000,
                isActive = false,
            ),
            UserEntity(
                userId = "2",
                userName = "User2",
                userAccount = "user2",
                userMailAddress = "user2@mail.com",
                accessToken = "token2",
                refreshToken = "refresh2",
                tokenExpiresAt = System.currentTimeMillis() + 7200 * 1000,
                isActive = true,
            ),
        )

        users.forEach { userDao.insertUser(it) }
        val activeUser = userDao.getActiveUser()

        assertNotNull(activeUser)
        assertEquals("2", activeUser?.userId)
        assertTrue("Active user should have isActive = true", activeUser?.isActive == true)
    }

    @Test
    fun deactivateAllUsers_updatesAllToInactive() = runBlocking {
        val users = listOf(
            UserEntity(
                userId = "1",
                userName = "User1",
                userAccount = "user1",
                userMailAddress = "user1@mail.com",
                accessToken = "token1",
                refreshToken = "refresh1",
                tokenExpiresAt = System.currentTimeMillis() + 3600 * 1000,
                isActive = true,
            ),
            UserEntity(
                userId = "2",
                userName = "User2",
                userAccount = "user2",
                userMailAddress = "user2@mail.com",
                accessToken = "token2",
                refreshToken = "refresh2",
                tokenExpiresAt = System.currentTimeMillis() + 7200 * 1000,
                isActive = true,
            ),
        )

        users.forEach { userDao.insertUser(it) }
        userDao.deactivateAllUsers()
        val activeUser = userDao.getActiveUser()

        assertNull(activeUser)
        val allUsers = userDao.getAllUsers()
        allUsers.forEach {
            assertFalse("All users should be deactivated", it.isActive)
        }
    }

    @Test
    fun activateUser_setsUserToActive() = runBlocking {
        val users = listOf(
            UserEntity(
                userId = "1",
                userName = "User1",
                userAccount = "user1",
                userMailAddress = "user1@mail.com",
                accessToken = "token1",
                refreshToken = "refresh1",
                tokenExpiresAt = System.currentTimeMillis() + 3600 * 1000,
                isActive = false,
            ),
            UserEntity(
                userId = "2",
                userName = "User2",
                userAccount = "user2",
                userMailAddress = "user2@mail.com",
                accessToken = "token2",
                refreshToken = "refresh2",
                tokenExpiresAt = System.currentTimeMillis() + 7200 * 1000,
                isActive = false,
            ),
        )

        users.forEach { userDao.insertUser(it) }
        userDao.activateUser("1")
        val activeUser = userDao.getActiveUser()

        assertNotNull(activeUser)
        assertEquals("1", activeUser?.userId)
        assertTrue("User 1 should be active", activeUser?.isActive == true)
    }

    @Test
    fun deleteUser_removesSpecificUser() = runBlocking {
        val user = UserEntity(
            userId = "123",
            userName = "John Doe",
            userAccount = "john_doe",
            userMailAddress = "john@example.com",
            accessToken = "access_123",
            refreshToken = "refresh_123",
            tokenExpiresAt = System.currentTimeMillis() + 3600 * 1000,
            isActive = false,
        )

        userDao.insertUser(user)
        userDao.deleteUser(user)
        val count = userDao.getUserCount()

        assertEquals(0, count)
    }

    @Test
    fun updateUser_updatesUserTokensCorrectly() = runBlocking {
        val user = UserEntity(
            userId = "123",
            userName = "John Doe",
            userAccount = "john_doe",
            userMailAddress = "john@example.com",
            accessToken = "old_access_token",
            refreshToken = "old_refresh_token",
            tokenExpiresAt = System.currentTimeMillis() + 3600 * 1000,
            isActive = false,
        )
        userDao.insertUser(user)

        val newAccessToken = "new_access_token"
        val newRefreshToken = "new_refresh_token"
        val newExpiresAt = System.currentTimeMillis() + 7200 * 1000
        userDao.updateUser("123", newAccessToken, newRefreshToken, newExpiresAt)

        val updatedUser = userDao.getAllUsers().find { it.userId == "123" }

        assertNotNull(updatedUser)
        assertEquals(newAccessToken, updatedUser?.accessToken)
        assertEquals(newRefreshToken, updatedUser?.refreshToken)
        assertEquals(newExpiresAt, updatedUser?.tokenExpiresAt)
    }

    @Test
    fun updateUser_doesNotChangeOtherFields() = runBlocking {
        val user = UserEntity(
            userId = "123",
            userName = "John Doe",
            userAccount = "john_doe",
            userMailAddress = "john@example.com",
            accessToken = "old_access_token",
            refreshToken = "old_refresh_token",
            tokenExpiresAt = System.currentTimeMillis() + 3600 * 1000,
            isActive = false,
        )
        userDao.insertUser(user)

        val newAccessToken = "new_access_token"
        val newRefreshToken = "new_refresh_token"
        val newExpiresAt = System.currentTimeMillis() + 7200 * 1000
        userDao.updateUser("123", newAccessToken, newRefreshToken, newExpiresAt)

        val updatedUser = userDao.getAllUsers().find { it.userId == "123" }

        assertNotNull(updatedUser)
        assertEquals("John Doe", updatedUser?.userName)
        assertEquals("john_doe", updatedUser?.userAccount)
        assertEquals("john@example.com", updatedUser?.userMailAddress)
        assertEquals(false, updatedUser?.isActive)
    }
}
