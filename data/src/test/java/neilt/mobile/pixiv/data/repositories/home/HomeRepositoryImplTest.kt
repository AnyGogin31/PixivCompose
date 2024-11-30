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

package neilt.mobile.pixiv.data.repositories.home

import kotlinx.coroutines.test.runTest
import neilt.mobile.pixiv.data.remote.requests.home.toFieldMap
import neilt.mobile.pixiv.data.remote.responses.common.ImageUrlsResponse
import neilt.mobile.pixiv.data.remote.responses.home.IllustrationResponse
import neilt.mobile.pixiv.data.remote.responses.home.RecommendedIllustrationsResponse
import neilt.mobile.pixiv.data.remote.services.home.HomeService
import neilt.mobile.pixiv.domain.models.requests.RecommendedNovelsRequest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.kotlin.verify
import kotlin.test.assertEquals

class HomeRepositoryImplTest {
    private lateinit var repository: HomeRepositoryImpl
    private val homeService: HomeService = mock()

    @Before
    fun setup() {
        repository = HomeRepositoryImpl(homeService)
    }

    @Test
    fun `getRecommendedIllustrations calls service with correct parameters`() = runTest {
        val mockResponse = RecommendedIllustrationsResponse(
            illustrations = listOf(
                IllustrationResponse(
                    id = 1,
                    title = "Illustration 1",
                    type = "type1",
                    imageUrls = ImageUrlsResponse(
                        squareMediumUrl = "square1",
                        mediumUrl = "medium1",
                        largeUrl = "large1",
                    ),
                ),
                IllustrationResponse(
                    id = 2,
                    title = "Illustration 2",
                    type = "type2",
                    imageUrls = ImageUrlsResponse(
                        squareMediumUrl = "square2",
                        mediumUrl = "medium2",
                        largeUrl = "large2",
                    ),
                ),
            ),
            contestExists = false,
            nextUrl = null,
        )

        `when`(
            homeService.fetchRecommendedIllustrations(
                includeRankingIllustrations = true,
                includePrivacyPolicy = true,
            ),
        ).thenReturn(mockResponse)

        val result = repository.getRecommendedIllustrations(
            includeRankingIllustrations = true,
            includePrivacyPolicy = true,
        )

        assertEquals(2, result.size)
        assertEquals("Illustration 1", result[0].title)
        assertEquals("medium1", result[0].imageUrls.mediumUrl)
        assertEquals("Illustration 2", result[1].title)
        assertEquals("large2", result[1].imageUrls.largeUrl)

        verify(homeService).fetchRecommendedIllustrations(
            includeRankingIllustrations = true,
            includePrivacyPolicy = true,
        )
    }

    @Test
    fun `getRecommendedManga calls service with correct parameters`() = runTest {
        repository.getRecommendedManga(
            includeRankingIllustrations = false,
            includePrivacyPolicy = false,
        )
        verify(homeService).fetchRecommendedManga(
            includeRankingIllustrations = false,
            includePrivacyPolicy = false,
        )
    }

    @Test
    fun `submitPrivacyPolicyAgreement calls service with correct parameters`() = runTest {
        val agreement = "accepted"
        val version = "1.0"

        repository.submitPrivacyPolicyAgreement(agreement, version)
        verify(homeService).submitPrivacyPolicyAgreement(agreement, version)
    }

    @Test
    fun `submitRecommendedNovels calls service with correct parameters`() = runTest {
        val readNovelIds = listOf(1L, 2L, 3L)
        val viewNovelIds = listOf(4L, 5L)
        val readTimestamps = listOf("2023-11-01T12:00:00Z")
        val viewTimestamps = listOf("2023-11-02T15:00:00Z")

        val request = RecommendedNovelsRequest(
            includeRankingNovels = true,
            includePrivacyPolicy = false,
            readNovelIds = readNovelIds,
            viewNovelIds = viewNovelIds,
            readNovelTimestamps = readTimestamps,
            viewNovelTimestamps = viewTimestamps,
        )

        repository.submitRecommendedNovels(request)
        verify(homeService).submitRecommendedNovels(request.toFieldMap())
    }
}
