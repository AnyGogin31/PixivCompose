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

package neilt.mobile.pixiv.data.mapper.profile

import neilt.mobile.pixiv.data.remote.responses.profile.ProfileImageUrlsResponse
import neilt.mobile.pixiv.data.remote.responses.profile.ProfilePublicityResponse
import neilt.mobile.pixiv.data.remote.responses.profile.ProfileResponse
import neilt.mobile.pixiv.data.remote.responses.profile.ProfileUserResponse
import neilt.mobile.pixiv.data.remote.responses.profile.UserDetailResponse
import neilt.mobile.pixiv.data.remote.responses.profile.WorkspaceResponse
import neilt.mobile.pixiv.domain.models.profile.Profile
import neilt.mobile.pixiv.domain.models.profile.ProfileImageUrls
import neilt.mobile.pixiv.domain.models.profile.ProfilePublicity
import neilt.mobile.pixiv.domain.models.profile.ProfileUser
import neilt.mobile.pixiv.domain.models.profile.UserDetail
import neilt.mobile.pixiv.domain.models.profile.Workspace

fun UserDetailResponse.toModel() = UserDetail(
    user = user.toModel(),
    profile = profile.toModel(),
    profilePublicity = profilePublicity.toModel(),
    workspace = workspace.toModel(),
)

fun ProfileUserResponse.toModel() = ProfileUser(
    id = id,
    name = name,
    account = account,
    profileImageUrls = profileImageUrls.toModel(),
    comment = comment,
    isFollowed = isFollowed,
    isAccessBlockingUser = isAccessBlockingUser,
)

fun ProfileImageUrlsResponse.toModel() = ProfileImageUrls(
    medium = medium
)

fun ProfileResponse.toModel() = Profile(
    webpage = webpage,
    gender = gender,
    birth = birth,
    birthDay = birthDay,
    birthYear = birthYear,
    region = region,
    addressId = addressId,
    countryCode = countryCode,
    job = job,
    jobId = jobId,
    totalFollowUsers = totalFollowUsers,
    totalMyPixivUsers = totalMyPixivUsers,
    totalIllusts = totalIllusts,
    totalManga = totalManga,
    totalNovels = totalNovels,
    totalIllustSeries = totalIllustSeries,
    totalNovelSeries = totalNovelSeries,
    backgroundImageUrl = backgroundImageUrl,
    twitterAccount = twitterAccount,
    twitterUrl = twitterUrl,
    isPremium = isPremium,
    isUsingCustomProfileImage = isUsingCustomProfileImage,
)

fun ProfilePublicityResponse.toModel() = ProfilePublicity(
    gender = gender,
    region = region,
    birthDay = birthDay,
    birthYear = birthYear,
    job = job
)

fun WorkspaceResponse.toModel() = Workspace(
    pc = pc,
    monitor = monitor,
    tool = tool,
    scanner = scanner,
    tablet = tablet,
    mouse = mouse,
    printer = printer,
    desktop = desktop,
    music = music,
    desk = desk,
    chair = chair,
    comment = comment
)
