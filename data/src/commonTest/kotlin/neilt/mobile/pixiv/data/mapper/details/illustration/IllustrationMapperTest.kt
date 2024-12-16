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

package neilt.mobile.pixiv.data.mapper.details.illustration

import neilt.mobile.pixiv.data.remote.responses.common.ImageUrlsResponse
import neilt.mobile.pixiv.data.remote.responses.details.illustration.IllustrationDetailsResponse
import neilt.mobile.pixiv.data.remote.responses.details.illustration.TagResponse
import neilt.mobile.pixiv.data.remote.responses.details.illustration.UserResponse
import neilt.mobile.pixiv.data.remote.responses.profile.ProfileImageUrlsResponse
import kotlin.test.Test
import kotlin.test.assertEquals

class IllustrationMapperTest {
    @Test
    fun `IllustrationDetailsResponse toModel maps correctly`() {
        val response = IllustrationDetailsResponse(
            id = 123,
            title = "Sample Title",
            imageUrl = ImageUrlsResponse(
                squareMediumUrl = "http://example.com/square.jpg",
                mediumUrl = "http://example.com/medium.jpg",
                largeUrl = "http://example.com/large.jpg",
            ),
            caption = "Sample Caption",
            user = UserResponse(
                id = 456,
                name = "John Doe",
                profileImageUrl = ProfileImageUrlsResponse(
                    medium = "http://example.com/profile_medium.jpg",
                ),
            ),
            tags = listOf(
                TagResponse(name = "tag1", translatedName = "Tag 1"),
                TagResponse(name = "tag2", translatedName = null),
            ),
            metaSinglePage = ImageUrlsResponse(
                squareMediumUrl = null,
                mediumUrl = null,
                largeUrl = null,
            ),
            metaPages = emptyList(),
            views = 1000,
            bookmarks = 500,
        )

        val model = response.toModel()

        // Main properties
        assertEquals(response.id, model.id)
        assertEquals(response.title, model.title)
        assertEquals(response.caption, model.caption)
        assertEquals(response.views, model.views)
        assertEquals(response.bookmarks, model.bookmarks)

        // Nested objects
        assertEquals(response.imageUrl.mediumUrl, model.imageUrl.mediumUrl)
        assertEquals(response.user.id, model.user.id)
        assertEquals(response.user.name, model.user.name)
        assertEquals(response.user.profileImageUrl.medium, model.user.profileImageUrl.medium)

        // Tags
        assertEquals(response.tags.size, model.tags.size)
        assertEquals(response.tags[0].name, model.tags[0].name)
        assertEquals(response.tags[0].translatedName, model.tags[0].translatedName)
    }

    @Test
    fun `UserResponse toModel maps correctly`() {
        val response = UserResponse(
            id = 456,
            name = "John Doe",
            profileImageUrl = ProfileImageUrlsResponse(
                medium = "http://example.com/profile_medium.jpg",
            ),
        )

        val model = response.toModel()

        assertEquals(response.id, model.id)
        assertEquals(response.name, model.name)
        assertEquals(response.profileImageUrl.medium, model.profileImageUrl.medium)
    }

    @Test
    fun `TagResponse toModel maps correctly`() {
        val response = TagResponse(name = "tag1", translatedName = "Tag 1")

        val model = response.toModel()

        assertEquals(response.name, model.name)
        assertEquals(response.translatedName, model.translatedName)
    }
}
