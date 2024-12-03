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

package neilt.mobile.pixiv.data.remote.responses.profile

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProfileResponse(
    @Json(name = "webpage") val webpage: String?,
    @Json(name = "gender") val gender: Int,
    @Json(name = "birth") val birth: String,
    @Json(name = "birth_day") val birthDay: String,
    @Json(name = "birth_year") val birthYear: Int,
    @Json(name = "region") val region: String,
    @Json(name = "address_id") val addressId: Int,
    @Json(name = "country_code") val countryCode: String,
    @Json(name = "job") val job: String,
    @Json(name = "job_id") val jobId: Int,
    @Json(name = "total_follow_users") val totalFollowUsers: Int,
    @Json(name = "total_mypixiv_users") val totalMyPixivUsers: Int,
    @Json(name = "total_illusts") val totalIllusts: Int,
    @Json(name = "total_manga") val totalManga: Int,
    @Json(name = "total_novels") val totalNovels: Int,
    @Json(name = "total_illust_series") val totalIllustSeries: Int,
    @Json(name = "total_novel_series") val totalNovelSeries: Int,
    @Json(name = "background_image_url") val backgroundImageUrl: String?,
    @Json(name = "twitter_account") val twitterAccount: String?,
    @Json(name = "twitter_url") val twitterUrl: String?,
    @Json(name = "is_premium") val isPremium: Boolean,
    @Json(name = "is_using_custom_profile_image") val isUsingCustomProfileImage: Boolean
)
