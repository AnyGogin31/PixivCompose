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

package neilt.mobile.pixiv.data.remote.services.home

import neilt.mobile.pixiv.data.remote.common.Authorization
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

// Cut from the original application. There might be discrepancies.
interface HomeService {

    @Authorization
    @GET("/v1/illust/recommended?filter=for_android")
    suspend fun fetchRecommendedIllustrations(
        @Query("include_ranking_illusts") includeRankingIllustrations: Boolean,
        @Query("include_privacy_policy") includePrivacyPolicy: Boolean,
    )

    @Authorization
    @GET("/v1/manga/recommended?filter=for_android")
    suspend fun fetchRecommendedManga(
        @Query("include_ranking_illusts") includeRankingIllustrations: Boolean,
        @Query("include_privacy_policy") includePrivacyPolicy: Boolean,
    )

    @Authorization
    @FormUrlEncoded
    @POST("v1/privacy-policy/agreement")
    suspend fun submitPrivacyPolicyAgreement(
        @Field("agreement") agreement: String?,
        @Field("version") version: String?,
    )

    @Authorization
    @FormUrlEncoded
    @POST("/v1/novel/recommended")
    suspend fun submitRecommendedNovels(
        @Field("include_ranking_novels") includeRankingNovels: Boolean,
        @Field("include_privacy_policy") includePrivacyPolicy: Boolean,
        @Field("read_novel_ids[]") readNovelIds: List<Long?>?,
        @Field("view_novel_ids[]") viewNovelIds: List<Long?>?,
        @Field("read_novel_datetimes[]") readNovelTimestamps: List<String?>?,
        @Field("view_novel_datetimes[]") viewNovelTimestamps: List<String?>?,
    )
}
