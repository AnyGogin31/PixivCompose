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

package neilt.mobile.pixiv.data.mapper.profile

import neilt.mobile.pixiv.data.remote.responses.profile.ProfileImageUrlsResponse
import neilt.mobile.pixiv.data.remote.responses.profile.ProfilePublicityResponse
import neilt.mobile.pixiv.data.remote.responses.profile.ProfileResponse
import neilt.mobile.pixiv.data.remote.responses.profile.ProfileUserResponse
import neilt.mobile.pixiv.data.remote.responses.profile.UserDetailResponse
import neilt.mobile.pixiv.data.remote.responses.profile.WorkspaceResponse
import org.junit.Assert.assertEquals
import org.junit.Test

class ProfileMapperTest {
    @Test
    fun `UserDetailResponse toModel maps correctly`() {
        val response = UserDetailResponse(
            user = ProfileUserResponse(
                id = 123,
                name = "John Doe",
                account = "johndoe",
                profileImageUrls = ProfileImageUrlsResponse(medium = "http://example.com/image.jpg"),
                comment = "Sample comment",
                isFollowed = true,
                isAccessBlockingUser = false,
            ),
            profile = ProfileResponse(
                webpage = "http://example.com",
                gender = 1,
                birth = "1990-01-01",
                birthDay = "01-01",
                birthYear = 1990,
                region = "Region",
                addressId = 123,
                countryCode = "US",
                job = "Software Engineer",
                jobId = 42,
                totalFollowUsers = 100,
                totalMyPixivUsers = 50,
                totalIllusts = 10,
                totalManga = 5,
                totalNovels = 2,
                totalIllustSeries = 1,
                totalNovelSeries = 0,
                backgroundImageUrl = "http://example.com/bg.jpg",
                twitterAccount = "johndoe",
                twitterUrl = "http://twitter.com/johndoe",
                isPremium = true,
                isUsingCustomProfileImage = false,
            ),
            profilePublicity = ProfilePublicityResponse(
                gender = 0,
                region = 1,
                birthDay = 1,
                birthYear = 1,
                job = 0,
            ),
            workspace = WorkspaceResponse(
                pc = "PC",
                monitor = "Monitor",
                tool = "Tool",
                scanner = "Scanner",
                tablet = "Tablet",
                mouse = "Mouse",
                printer = "Printer",
                desktop = "Desktop",
                music = "Music",
                desk = "Desk",
                chair = "Chair",
                comment = "Workspace comment",
            ),
        )

        val model = response.toModel()

        // Assertions for user
        assertEquals(response.user.id, model.user.id)
        assertEquals(response.user.name, model.user.name)
        assertEquals(response.user.account, model.user.account)
        assertEquals(response.user.profileImageUrls.medium, model.user.profileImageUrls.medium)

        // Assertions for profile
        assertEquals(response.profile.webpage, model.profile.webpage)
        assertEquals(response.profile.gender, model.profile.gender)
        assertEquals(response.profile.birth, model.profile.birth)

        // Assertions for publicity
        assertEquals(response.profilePublicity.gender, model.profilePublicity.gender)

        // Assertions for workspace
        assertEquals(response.workspace.pc, model.workspace.pc)
        assertEquals(response.workspace.comment, model.workspace.comment)
    }

    @Test
    fun `ProfileUserResponse toModel maps correctly`() {
        val response = ProfileUserResponse(
            id = 123,
            name = "John Doe",
            account = "johndoe",
            profileImageUrls = ProfileImageUrlsResponse(medium = "http://example.com/image.jpg"),
            comment = "Sample comment",
            isFollowed = true,
            isAccessBlockingUser = false,
        )

        val model = response.toModel()

        assertEquals(response.id, model.id)
        assertEquals(response.name, model.name)
        assertEquals(response.account, model.account)
        assertEquals(response.profileImageUrls.medium, model.profileImageUrls.medium)
        assertEquals(response.comment, model.comment)
        assertEquals(response.isFollowed, model.isFollowed)
        assertEquals(response.isAccessBlockingUser, model.isAccessBlockingUser)
    }

    @Test
    fun `WorkspaceResponse toModel maps correctly`() {
        val response = WorkspaceResponse(
            pc = "PC",
            monitor = "Monitor",
            tool = "Tool",
            scanner = "Scanner",
            tablet = "Tablet",
            mouse = "Mouse",
            printer = "Printer",
            desktop = "Desktop",
            music = "Music",
            desk = "Desk",
            chair = "Chair",
            comment = "Workspace comment",
        )

        val model = response.toModel()

        assertEquals(response.pc, model.pc)
        assertEquals(response.monitor, model.monitor)
        assertEquals(response.tool, model.tool)
        assertEquals(response.scanner, model.scanner)
        assertEquals(response.tablet, model.tablet)
        assertEquals(response.mouse, model.mouse)
        assertEquals(response.printer, model.printer)
        assertEquals(response.desktop, model.desktop)
        assertEquals(response.music, model.music)
        assertEquals(response.desk, model.desk)
        assertEquals(response.chair, model.chair)
        assertEquals(response.comment, model.comment)
    }
}
