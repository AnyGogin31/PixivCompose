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

package neilt.mobile.pixiv.features.main.presentation.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import neilt.mobile.pixiv.core.state.whenState
import neilt.mobile.pixiv.desingsystem.components.views.ErrorView
import neilt.mobile.pixiv.desingsystem.components.views.LoadingView
import neilt.mobile.pixiv.domain.models.profile.Profile
import neilt.mobile.pixiv.domain.models.profile.UserDetail
import neilt.mobile.pixiv.resources.Res
import neilt.mobile.pixiv.resources.profile_birth_date
import neilt.mobile.pixiv.resources.profile_followers
import neilt.mobile.pixiv.resources.profile_image_description
import neilt.mobile.pixiv.resources.profile_job
import neilt.mobile.pixiv.resources.profile_region
import neilt.mobile.pixiv.resources.twitter_handle
import neilt.mobile.pixiv.resources.website_button
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun ProfileView(
    viewModel: ProfileViewModel = koinViewModel(),
) {
    val state by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center,
    ) {
        state.whenState<UserDetail>(
            onLoading = { LoadingView() },
            onError = { ErrorView(message = it) },
            onLoaded = { ProfileContent(userDetail = it) },
        )
    }
}

@Composable
private fun ProfileContent(userDetail: UserDetail) {
    val user = userDetail.user
    val profile = userDetail.profile

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = user.profileImageUrls,
                contentDescription = stringResource(Res.string.profile_image_description),
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape),
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = user.name,
                    style = MaterialTheme.typography.headlineMedium,
                )
                Text(
                    text = "@${user.account}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = user.comment,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 16.dp),
        )

        HorizontalDivider(
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            thickness = 1.dp,
        )

        Spacer(modifier = Modifier.height(16.dp))

        ProfileInfoSection(profile)

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            profile.twitterAccount?.let {
                TextButton(onClick = { /* Open Twitter */ }) {
                    Text(text = stringResource(Res.string.twitter_handle, it))
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            profile.webpage?.let {
                TextButton(onClick = { /* Open webpage */ }) {
                    Text(text = stringResource(Res.string.website_button))
                }
            }
        }
    }
}

@Composable
fun ProfileInfoSection(profile: Profile) {
    Column {
        profile.birthDay?.let {
            ProfileInfoItem(
                label = stringResource(Res.string.profile_birth_date),
                value = it,
            )
        }
        profile.region?.let {
            ProfileInfoItem(
                label = stringResource(Res.string.profile_region),
                value = it,
            )
        }
        profile.job?.let {
            ProfileInfoItem(
                label = stringResource(Res.string.profile_job),
                value = it,
            )
        }
        ProfileInfoItem(
            label = stringResource(Res.string.profile_followers),
            value = profile.totalFollowUsers.toString(),
        )
    }
}

@Composable
fun ProfileInfoItem(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "$label:",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f),
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(2f),
        )
    }
}
