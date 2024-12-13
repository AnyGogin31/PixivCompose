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
import neilt.mobile.pixiv.data.mapper.home.toModel
import neilt.mobile.pixiv.data.remote.responses.common.ImageUrlsResponse
import neilt.mobile.pixiv.data.remote.responses.home.IllustrationResponse
import neilt.mobile.pixiv.data.remote.responses.home.RecommendedIllustrationsResponse
import neilt.mobile.pixiv.data.sources.home.HomeRemoteDataSource
import neilt.mobile.pixiv.domain.models.requests.RecommendedNovelsRequest
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.kotlin.verify
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class HomeRepositoryImplTest {
    private lateinit var repository: HomeRepositoryImpl
    private val homeRemoteDataSource: HomeRemoteDataSource = mock()

    @BeforeTest
    fun setup() {
        repository = HomeRepositoryImpl(
            homeRemoteDataSource = homeRemoteDataSource,
        )
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
            homeRemoteDataSource.getRecommendedIllustrations(
                includeRankingIllustrations = true,
                includePrivacyPolicy = true,
                offset = 0,
            ),
        ).thenReturn(mockResponse.illustrations.map { it.toModel() })

        val result = repository.getRecommendedIllustrations(
            includeRankingIllustrations = true,
            includePrivacyPolicy = true,
        )

        assertEquals(2, result.size)
        assertEquals("Illustration 1", result[0].title)
        assertEquals("medium1", result[0].imageUrls.mediumUrl)
        assertEquals("Illustration 2", result[1].title)
        assertEquals("large2", result[1].imageUrls.largeUrl)

        verify(homeRemoteDataSource).getRecommendedIllustrations(
            includeRankingIllustrations = true,
            includePrivacyPolicy = true,
            offset = 0,
        )
    }

    @Test
    fun `getRecommendedManga calls service and returns nothing if no data`() = runTest {
        `when`(
            homeRemoteDataSource.getRecommendedManga(
                includeRankingIllustrations = false,
                includePrivacyPolicy = false,
            ),
        ).thenReturn(Unit)

        repository.getRecommendedManga(
            includeRankingIllustrations = false,
            includePrivacyPolicy = false,
        )

        verify(homeRemoteDataSource).getRecommendedManga(
            includeRankingIllustrations = false,
            includePrivacyPolicy = false,
        )
    }

    @Test
    fun `submitPrivacyPolicyAgreement calls service with correct parameters`() = runTest {
        val agreement = "accepted"
        val version = "1.0"

        repository.submitPrivacyPolicyAgreement(agreement, version)
        verify(homeRemoteDataSource).submitPrivacyPolicyAgreement(agreement, version)
    }

    @Test
    fun `submitRecommendedNovels calls service with correct parameters`() = runTest {
        val request = RecommendedNovelsRequest(
            includeRankingNovels = true,
            includePrivacyPolicy = false,
            readNovelIds = listOf(1L, 2L, 3L),
            viewNovelIds = listOf(4L, 5L),
            readNovelTimestamps = listOf("2023-11-01T12:00:00Z"),
            viewNovelTimestamps = listOf("2023-11-02T15:00:00Z"),
        )

        repository.submitRecommendedNovels(request)
        verify(homeRemoteDataSource).submitRecommendedNovels(request)
    }
}
