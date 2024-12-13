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

package neilt.mobile.pixiv.data.repositories.profile

import kotlinx.coroutines.test.runTest
import neilt.mobile.pixiv.data.mapper.profile.toModel
import neilt.mobile.pixiv.data.remote.responses.profile.ProfileImageUrlsResponse
import neilt.mobile.pixiv.data.remote.responses.profile.ProfilePublicityResponse
import neilt.mobile.pixiv.data.remote.responses.profile.ProfileResponse
import neilt.mobile.pixiv.data.remote.responses.profile.ProfileUserResponse
import neilt.mobile.pixiv.data.remote.responses.profile.UserDetailResponse
import neilt.mobile.pixiv.data.remote.responses.profile.WorkspaceResponse
import neilt.mobile.pixiv.data.sources.profile.ProfileRemoteDataSource
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ProfileRepositoryImplTest {
    private lateinit var repository: ProfileRepositoryImpl
    private val profileRemoteDataSource: ProfileRemoteDataSource = mock()

    @BeforeTest
    fun setup() {
        repository = ProfileRepositoryImpl(
            profileRemoteDataSource = profileRemoteDataSource,
        )
    }

    @Test
    fun `getUserDetail maps response to UserDetail`() = runTest {
        val userId = 123L

        val mockResponse = UserDetailResponse(
            user = ProfileUserResponse(
                id = userId,
                name = "Test User",
                account = "test_account",
                profileImageUrls = ProfileImageUrlsResponse(
                    medium = "medium_image_url",
                ),
                comment = "This is a test user.",
                isFollowed = true,
                isAccessBlockingUser = false,
            ),
            profile = ProfileResponse(
                webpage = "https://testuser.com",
                gender = 1,
                birth = "1990-01-01",
                birthDay = "01-01",
                birthYear = 1990,
                region = "Test Region",
                addressId = 1,
                countryCode = "TR",
                job = "Developer",
                jobId = 101,
                totalFollowUsers = 100,
                totalMyPixivUsers = 5,
                totalIllusts = 50,
                totalManga = 20,
                totalNovels = 15,
                totalIllustSeries = 10,
                totalNovelSeries = 5,
                backgroundImageUrl = "https://background.image.url",
                twitterAccount = "testuser",
                twitterUrl = "https://twitter.com/testuser",
                isPremium = true,
                isUsingCustomProfileImage = false,
            ),
            profilePublicity = ProfilePublicityResponse(
                gender = 0,
                region = 1,
                birthDay = 1,
                birthYear = 1,
                job = 1,
            ),
            workspace = WorkspaceResponse(
                pc = "High-end PC",
                monitor = "4K Monitor",
                tool = "Graphics Tool",
                scanner = "Pro Scanner",
                tablet = "Wacom Tablet",
                mouse = "Gaming Mouse",
                printer = "Office Printer",
                desktop = "Clean Desk",
                music = "Classical",
                desk = "Wooden Desk",
                chair = "Ergonomic Chair",
                comment = "Perfect workspace for creativity.",
            ),
        )
        `when`(profileRemoteDataSource.getUserDetail(userId.toInt())).thenReturn(mockResponse.toModel())

        val result = repository.getUserDetail(userId.toInt())

        assertEquals(userId, result.user.id)
        assertEquals("Test User", result.user.name)
        assertEquals("medium_image_url", result.user.profileImageUrls.medium)
        assertEquals("This is a test user.", result.user.comment)
        assertTrue(result.user.isFollowed)

        verify(profileRemoteDataSource).getUserDetail(userId.toInt())
    }
}
