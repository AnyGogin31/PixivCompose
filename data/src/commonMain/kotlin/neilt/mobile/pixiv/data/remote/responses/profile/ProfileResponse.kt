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

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileResponse(
    @SerialName("webpage") val webpage: String?,
    @SerialName("gender") val gender: Int,
    @SerialName("birth") val birth: String,
    @SerialName("birth_day") val birthDay: String,
    @SerialName("birth_year") val birthYear: Int,
    @SerialName("region") val region: String,
    @SerialName("address_id") val addressId: Int,
    @SerialName("country_code") val countryCode: String,
    @SerialName("job") val job: String,
    @SerialName("job_id") val jobId: Int,
    @SerialName("total_follow_users") val totalFollowUsers: Int,
    @SerialName("total_mypixiv_users") val totalMyPixivUsers: Int,
    @SerialName("total_illusts") val totalIllusts: Int,
    @SerialName("total_manga") val totalManga: Int,
    @SerialName("total_novels") val totalNovels: Int,
    @SerialName("total_illust_series") val totalIllustSeries: Int,
    @SerialName("total_novel_series") val totalNovelSeries: Int,
    @SerialName("background_image_url") val backgroundImageUrl: String?,
    @SerialName("twitter_account") val twitterAccount: String?,
    @SerialName("twitter_url") val twitterUrl: String?,
    @SerialName("is_premium") val isPremium: Boolean,
    @SerialName("is_using_custom_profile_image") val isUsingCustomProfileImage: Boolean,
)
