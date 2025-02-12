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

package neilt.mobile.pixiv.data.sources.home

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import neilt.mobile.pixiv.data.mapper.home.toModel
import neilt.mobile.pixiv.data.remote.requests.home.toFieldMap
import neilt.mobile.pixiv.data.remote.services.home.HomeService
import neilt.mobile.pixiv.domain.models.home.Illustration
import neilt.mobile.pixiv.domain.models.requests.RecommendedNovelsRequest

class HomeRemoteDataSource(
    private val homeService: HomeService,
) {
    suspend fun getRecommendedIllustrations(
        includeRankingIllustrations: Boolean = false,
        includePrivacyPolicy: Boolean = false,
        offset: Int = 0,
    ): List<Illustration> {
        return withContext(Dispatchers.IO) {
            homeService.fetchRecommendedIllustrations(
                includeRankingIllustrations,
                includePrivacyPolicy,
                offset,
            ).illustrations.map { it.toModel() }
        }
    }

    suspend fun getRecommendedManga(
        includeRankingIllustrations: Boolean = false,
        includePrivacyPolicy: Boolean = false,
        offset: Int = 0,
    ): List<Illustration> {
        return withContext(Dispatchers.IO) {
            homeService.fetchRecommendedManga(
                includeRankingIllustrations,
                includePrivacyPolicy,
                offset,
            ).illustrations.map { it.toModel() }
        }
    }

    suspend fun submitPrivacyPolicyAgreement(
        agreement: String?,
        version: String?,
    ) {
        return withContext(Dispatchers.IO) {
            homeService.submitPrivacyPolicyAgreement(
                agreement,
                version,
            )
        }
    }

    suspend fun submitRecommendedNovels(request: RecommendedNovelsRequest) {
        return withContext(Dispatchers.IO) {
            homeService.submitRecommendedNovels(request.toFieldMap())
        }
    }
}
