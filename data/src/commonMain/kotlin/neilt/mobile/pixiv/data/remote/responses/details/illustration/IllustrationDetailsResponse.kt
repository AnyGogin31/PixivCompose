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

package neilt.mobile.pixiv.data.remote.responses.details.illustration

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import neilt.mobile.pixiv.data.remote.responses.common.ImageUrlsResponse

@JsonClass(generateAdapter = true)
data class IllustrationDetailsResponse(
    @Json(name = "id") val id: Int,
    @Json(name = "title") val title: String,
    @Json(name = "image_urls") val imageUrl: ImageUrlsResponse,
    @Json(name = "caption") val caption: String,
    @Json(name = "user") val user: UserResponse,
    @Json(name = "tags") val tags: List<TagResponse>,
    @Json(name = "total_view") val views: Int,
    @Json(name = "total_bookmarks") val bookmarks: Int,
)

@JsonClass(generateAdapter = true)
data class IllustrationDetailsRootResponse(
    @Json(name = "illust") val illustrationDetails: IllustrationDetailsResponse,
)
