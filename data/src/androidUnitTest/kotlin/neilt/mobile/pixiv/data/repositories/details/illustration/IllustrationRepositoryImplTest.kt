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

package neilt.mobile.pixiv.data.repositories.details.illustration

import kotlinx.coroutines.test.runTest
import neilt.mobile.pixiv.data.mapper.details.illustration.toModel
import neilt.mobile.pixiv.data.remote.responses.common.ImageUrlsResponse
import neilt.mobile.pixiv.data.remote.responses.details.illustration.IllustrationDetailsResponse
import neilt.mobile.pixiv.data.remote.responses.details.illustration.IllustrationDetailsRootResponse
import neilt.mobile.pixiv.data.remote.responses.details.illustration.TagResponse
import neilt.mobile.pixiv.data.remote.responses.details.illustration.UserResponse
import neilt.mobile.pixiv.data.remote.responses.profile.ProfileImageUrlsResponse
import neilt.mobile.pixiv.data.repositories.illustration.IllustrationRepositoryImpl
import neilt.mobile.pixiv.data.sources.illustration.IllustrationLocalDataSource
import neilt.mobile.pixiv.data.sources.illustration.IllustrationRemoteDataSource
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class IllustrationRepositoryImplTest {
    private lateinit var repository: IllustrationRepositoryImpl
    private val illustrationRemoteDataSource: IllustrationRemoteDataSource = mock()
    private val illustrationLocalDataSource: IllustrationLocalDataSource = mock()

    @BeforeTest
    fun setup() {
        repository = IllustrationRepositoryImpl(
            illustrationLocalDataSource = illustrationLocalDataSource,
            illustrationRemoteDataSource = illustrationRemoteDataSource,
        )
    }

    @Test
    fun `getIllustration maps response to IllustrationDetails correctly`() = runTest {
        val illustrationId = 101

        val mockResponse = IllustrationDetailsRootResponse(
            illustrationDetails = IllustrationDetailsResponse(
                id = illustrationId,
                title = "Test Illustration",
                imageUrl = ImageUrlsResponse(
                    squareMediumUrl = "https://example.com/square.jpg",
                    mediumUrl = "https://example.com/medium.jpg",
                    largeUrl = "https://example.com/large.jpg",
                ),
                caption = "This is a test illustration.",
                user = UserResponse(
                    id = 1,
                    name = "Test User",
                    profileImageUrl = ProfileImageUrlsResponse(
                        medium = "https://example.com/profile.jpg",
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
            ),
        )
        `when`(illustrationRemoteDataSource.getIllustration(illustrationId)).thenReturn(mockResponse.illustrationDetails.toModel())

        val result = repository.getIllustration(illustrationId)

        assertEquals(illustrationId, result.id)
        assertEquals("Test Illustration", result.title)
        assertEquals("https://example.com/medium.jpg", result.imageUrl.mediumUrl)
        assertEquals("This is a test illustration.", result.caption)
        assertEquals("Test User", result.user.name)
        assertEquals("tag1", result.tags[0].name)
        assertEquals("Tag 1", result.tags[0].translatedName)
        assertEquals(1000, result.views)
        assertEquals(500, result.bookmarks)

        verify(illustrationRemoteDataSource).getIllustration(illustrationId)
    }

    @Test
    fun `downloadIllustration saves image to local data source`() = runTest {
        val url = "https://example.com/image.jpg"
        val fileName = "image.jpg"
        val imageData = byteArrayOf(1, 2, 3, 4, 5)

        `when`(illustrationRemoteDataSource.getIllustrationFile(url)).thenReturn(imageData)

        repository.downloadIllustration(url, fileName)

        verify(illustrationRemoteDataSource).getIllustrationFile(url)
        verify(illustrationLocalDataSource).saveImageSafely(imageData, fileName)
    }
}
