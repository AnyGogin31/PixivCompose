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

package neilt.mobile.pixiv.data.mapper.details.illustration

import neilt.mobile.pixiv.data.mapper.home.toModel
import neilt.mobile.pixiv.data.mapper.profile.toModel
import neilt.mobile.pixiv.data.remote.responses.details.illustration.IllustrationDetailsResponse
import neilt.mobile.pixiv.data.remote.responses.details.illustration.TagResponse
import neilt.mobile.pixiv.data.remote.responses.details.illustration.UserResponse
import neilt.mobile.pixiv.domain.models.details.illustration.IllustrationDetails
import neilt.mobile.pixiv.domain.models.details.illustration.Tag
import neilt.mobile.pixiv.domain.models.details.illustration.User

fun IllustrationDetailsResponse.toModel() = IllustrationDetails(
    id = id,
    title = title,
    imageUrl = imageUrl.toModel(),
    caption = caption,
    user = user.toModel(),
    tags = tags.map { it.toModel() },
    metaSinglePage = metaSinglePage.toModel(),
    metaPages = metaPages.map { it.imageUrls.toModel() },
    views = views,
    bookmarks = bookmarks,
)

fun UserResponse.toModel() = User(
    id = id,
    name = name,
    profileImageUrl = profileImageUrl.toModel(),
)

fun TagResponse.toModel() = Tag(
    name = name,
    translatedName = translatedName,
)
