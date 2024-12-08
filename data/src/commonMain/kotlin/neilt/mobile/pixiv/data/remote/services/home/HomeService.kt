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

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.Parameters
import neilt.mobile.pixiv.data.remote.common.AUTHORIZATION_REQUIRED_HEADER
import neilt.mobile.pixiv.data.remote.responses.home.RecommendedIllustrationsResponse

// Cut from the original application. There might be discrepancies.
class HomeService(private val client: HttpClient) {
    suspend fun fetchRecommendedIllustrations(
        includeRankingIllustrations: Boolean,
        includePrivacyPolicy: Boolean,
    ): RecommendedIllustrationsResponse {
        return client.get("/v1/illust/recommended?filter=for_android") {
            headers.append(AUTHORIZATION_REQUIRED_HEADER, "true")
            parameter("include_ranking_illusts", includeRankingIllustrations)
            parameter("include_privacy_policy", includePrivacyPolicy)
        }.body()
    }

    suspend fun fetchRecommendedManga(
        includeRankingIllustrations: Boolean,
        includePrivacyPolicy: Boolean,
    ) {
        client.get("/v1/manga/recommended?filter=for_android") {
            headers.append(AUTHORIZATION_REQUIRED_HEADER, "true")
            parameter("include_ranking_illusts", includeRankingIllustrations)
            parameter("include_privacy_policy", includePrivacyPolicy)
        }
    }

    suspend fun submitPrivacyPolicyAgreement(
        agreement: String?,
        version: String?,
    ) {
        client.submitForm(
            url = "v1/privacy-policy/agreement",
            formParameters = Parameters.build {
                agreement?.let { append("agreement", it) }
                version?.let { append("version", it) }
            },
        ) {
            headers.append(AUTHORIZATION_REQUIRED_HEADER, "true")
        }
    }

    suspend fun submitRecommendedNovels(request: Map<String, Any?>) {
        client.submitForm(
            url = "/v1/novel/recommended",
            formParameters = Parameters.build {
                request.forEach { (key, value) ->
                    value?.toString()?.let { append(key, it) }
                }
            },
        ) {
            headers.append(AUTHORIZATION_REQUIRED_HEADER, "true")
        }
    }
}
