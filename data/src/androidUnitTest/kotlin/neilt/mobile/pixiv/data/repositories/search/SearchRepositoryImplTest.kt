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

package neilt.mobile.pixiv.data.repositories.search

import kotlinx.coroutines.test.runTest
import neilt.mobile.pixiv.data.mapper.home.toModel
import neilt.mobile.pixiv.data.remote.responses.common.ImageUrlsResponse
import neilt.mobile.pixiv.data.remote.responses.home.IllustrationResponse
import neilt.mobile.pixiv.data.remote.responses.search.IllustrationSearchResponse
import neilt.mobile.pixiv.data.sources.search.SearchRemoteDataSource
import neilt.mobile.pixiv.domain.models.requests.SearchIllustrationsRequest
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.kotlin.verify
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SearchRepositoryImplTest {
    private lateinit var repository: SearchRepositoryImpl
    private val searchRemoteDataSource: SearchRemoteDataSource = mock()

    @BeforeTest
    fun setup() {
        repository = SearchRepositoryImpl(searchRemoteDataSource)
    }

    @Test
    fun `getSearchIllustrations maps response to Illustration list`() = runTest {
        val request = SearchIllustrationsRequest(
            keyword = "test",
            sortOrder = "date_desc",
            searchTarget = "partial_match_for_tags",
            aiType = 0,
            minBookmarks = 100,
            maxBookmarks = 500,
            startDate = "2023-01-01",
            endDate = "2023-12-31",
        )

        val mockResponse = IllustrationSearchResponse(
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
            nextUrl = null,
            searchSpanLimit = 1,
            showUi = false,
        )
        `when`(searchRemoteDataSource.getSearchIllustrations(request.keyword)).thenReturn(mockResponse.illustrations.map { it.toModel() })

        val result = repository.getSearchIllustrations(request)

        assertEquals(2, result.size)
        assertEquals("Illustration 1", result[0].title)
        assertEquals("medium1", result[0].imageUrls.mediumUrl)
        assertEquals("Illustration 2", result[1].title)
        assertEquals("large2", result[1].imageUrls.largeUrl)

        verify(searchRemoteDataSource).getSearchIllustrations(request.keyword)
    }

    @Test
    fun `getSearchIllustrations filters out empty or null query parameters`() = runTest {
        val request = SearchIllustrationsRequest(
            keyword = "test",
            sortOrder = null,
            searchTarget = null,
            aiType = null,
            minBookmarks = null,
            maxBookmarks = null,
            startDate = null,
            endDate = null,
        )

        val mockResponse = IllustrationSearchResponse(
            illustrations = emptyList(),
            nextUrl = null,
            searchSpanLimit = 1,
            showUi = false,
        )
        `when`(searchRemoteDataSource.getSearchIllustrations(request.keyword)).thenReturn(mockResponse.illustrations.map { it.toModel() })

        val result = repository.getSearchIllustrations(request)

        assertTrue(result.isEmpty())

        verify(searchRemoteDataSource).getSearchIllustrations(request.keyword)
    }
}
