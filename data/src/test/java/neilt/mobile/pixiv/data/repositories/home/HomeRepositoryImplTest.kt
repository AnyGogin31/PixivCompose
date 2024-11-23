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
import neilt.mobile.pixiv.data.remote.services.home.HomeService
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify

class HomeRepositoryImplTest {

    private lateinit var repository: HomeRepositoryImpl
    private val homeService: HomeService = mock()

    @Before
    fun setup() {
        repository = HomeRepositoryImpl(homeService)
    }

    @Test
    fun `getRecommendedIllustrations calls service with correct parameters`() = runTest {
        repository.getRecommendedIllustrations(includeRankingIllustrations = true, includePrivacyPolicy = true)
        verify(homeService).fetchRecommendedIllustrations(
            includeRankingIllustrations = true,
            includePrivacyPolicy = true
        )
    }

    @Test
    fun `getRecommendedManga calls service with correct parameters`() = runTest {
        repository.getRecommendedManga(includeRankingIllustrations = false, includePrivacyPolicy = false)
        verify(homeService).fetchRecommendedManga(
            includeRankingIllustrations = false,
            includePrivacyPolicy = false
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

        repository.submitRecommendedNovels(
            includeRankingNovels = true,
            includePrivacyPolicy = false,
            readNovelIds = readNovelIds,
            viewNovelIds = viewNovelIds,
            readNovelTimestamps = readTimestamps,
            viewNovelTimestamps = viewTimestamps
        )
        verify(homeService).submitRecommendedNovels(
            includeRankingNovels = true,
            includePrivacyPolicy = false,
            readNovelIds = readNovelIds,
            viewNovelIds = viewNovelIds,
            readNovelTimestamps = readTimestamps,
            viewNovelTimestamps = viewTimestamps
        )
    }
}
