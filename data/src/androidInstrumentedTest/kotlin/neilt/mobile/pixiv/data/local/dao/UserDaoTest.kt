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

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import neilt.mobile.pixiv.data.local.db.PixivDatabase
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserDaoTest {
    private lateinit var database: PixivDatabase
    private val queries get() = database.pixivDatabaseQueries

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val driver = AndroidSqliteDriver(PixivDatabase.Schema, context, "PixivDatabase.db")
        database = PixivDatabase(driver)

        queries.transaction {
            val users = queries.getAllUsers().executeAsList()
            users.forEach {
                queries.deleteUser(it.user_id)
            }
        }
    }

    @Test
    fun insertUser_and_getUserCount() {
        queries.insertUser(
            user_id = "123",
            user_name = "John Doe",
            user_account = "john_doe",
            user_mail_address = "john@example.com",
            access_token = "access_123",
            refresh_token = "refresh_123",
            token_expires_at = System.currentTimeMillis() + 3600 * 1000,
            is_active = 0,
        )

        val count = queries.getUserCount().executeAsOne()
        assertEquals(1, count)
    }

    @Test
    fun getAllUsers_returnsAllInsertedUsers() {
        queries.insertUser(
            user_id = "1",
            user_name = "User1",
            user_account = "user1",
            user_mail_address = "user1@mail.com",
            access_token = "token1",
            refresh_token = "refresh1",
            token_expires_at = System.currentTimeMillis() + 3600 * 1000,
            is_active = 0,
        )

        queries.insertUser(
            user_id = "2",
            user_name = "User2",
            user_account = "user2",
            user_mail_address = "user2@mail.com",
            access_token = "token2",
            refresh_token = "refresh2",
            token_expires_at = System.currentTimeMillis() + 7200 * 1000,
            is_active = 1,
        )

        val retrievedUsers = queries.getAllUsers().executeAsList()
        assertEquals(2, retrievedUsers.size)
        assertTrue(retrievedUsers.any { it.user_id == "1" })
        assertTrue(retrievedUsers.any { it.user_id == "2" })
    }

    @Test
    fun getActiveUser_returnsCorrectUser() {
        queries.insertUser(
            user_id = "1",
            user_name = "User1",
            user_account = "user1",
            user_mail_address = "user1@mail.com",
            access_token = "token1",
            refresh_token = "refresh1",
            token_expires_at = System.currentTimeMillis() + 3600 * 1000,
            is_active = 0,
        )

        queries.insertUser(
            user_id = "2",
            user_name = "User2",
            user_account = "user2",
            user_mail_address = "user2@mail.com",
            access_token = "token2",
            refresh_token = "refresh2",
            token_expires_at = System.currentTimeMillis() + 7200 * 1000,
            is_active = 1,
        )

        val activeUser = queries.getActiveUser().executeAsOneOrNull()
        assertNotNull(activeUser)
        assertEquals("2", activeUser?.user_id)
        assertTrue(activeUser?.is_active == 1L)
    }

    @Test
    fun deactivateAllUsers_updatesAllToInactive() {
        queries.insertUser(
            user_id = "1",
            user_name = "User1",
            user_account = "user1",
            user_mail_address = "user1@mail.com",
            access_token = "token1",
            refresh_token = "refresh1",
            token_expires_at = System.currentTimeMillis() + 3600 * 1000,
            is_active = 1,
        )

        queries.insertUser(
            user_id = "2",
            user_name = "User2",
            user_account = "user2",
            user_mail_address = "user2@mail.com",
            access_token = "token2",
            refresh_token = "refresh2",
            token_expires_at = System.currentTimeMillis() + 7200 * 1000,
            is_active = 1,
        )

        queries.deactivateAllUsers()
        val activeUser = queries.getActiveUser().executeAsOneOrNull()
        assertNull(activeUser)

        val allUsers = queries.getAllUsers().executeAsList()
        allUsers.forEach { assertFalse(it.is_active == 1L) }
    }

    @Test
    fun activateUser_setsUserToActive() {
        queries.insertUser(
            user_id = "1",
            user_name = "User1",
            user_account = "user1",
            user_mail_address = "user1@mail.com",
            access_token = "token1",
            refresh_token = "refresh1",
            token_expires_at = System.currentTimeMillis() + 3600 * 1000,
            is_active = 0,
        )

        queries.insertUser(
            user_id = "2",
            user_name = "User2",
            user_account = "user2",
            user_mail_address = "user2@mail.com",
            access_token = "token2",
            refresh_token = "refresh2",
            token_expires_at = System.currentTimeMillis() + 7200 * 1000,
            is_active = 0,
        )

        queries.activateUser("1")
        val activeUser = queries.getActiveUser().executeAsOneOrNull()
        assertNotNull(activeUser)
        assertEquals("1", activeUser?.user_id)
        assertTrue(activeUser?.is_active == 1L)
    }
}
