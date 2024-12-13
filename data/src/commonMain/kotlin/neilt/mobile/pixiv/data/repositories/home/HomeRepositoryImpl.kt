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

import neilt.mobile.pixiv.data.sources.home.HomeRemoteDataSource
import neilt.mobile.pixiv.domain.models.home.Illustration
import neilt.mobile.pixiv.domain.models.requests.RecommendedNovelsRequest
import neilt.mobile.pixiv.domain.repositories.home.HomeRepository

class HomeRepositoryImpl(
    private val homeRemoteDataSource: HomeRemoteDataSource,
) : HomeRepository {
    override suspend fun getRecommendedIllustrations(
        includeRankingIllustrations: Boolean,
        includePrivacyPolicy: Boolean,
        offset: Int,
    ): List<Illustration> {
        return homeRemoteDataSource.getRecommendedIllustrations(
            includeRankingIllustrations,
            includePrivacyPolicy,
            offset,
        )
    }

    override suspend fun getRecommendedManga(
        includeRankingIllustrations: Boolean,
        includePrivacyPolicy: Boolean,
    ) {
        return homeRemoteDataSource.getRecommendedManga(
            includeRankingIllustrations,
            includePrivacyPolicy,
        )
    }

    override suspend fun submitPrivacyPolicyAgreement(
        agreement: String?,
        version: String?,
    ) {
        return homeRemoteDataSource.submitPrivacyPolicyAgreement(
            agreement,
            version,
        )
    }

    override suspend fun submitRecommendedNovels(request: RecommendedNovelsRequest) {
        return homeRemoteDataSource.submitRecommendedNovels(request)
    }
}
