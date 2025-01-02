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

package neilt.mobile.pixiv.features.details.presentation.illustration

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import neilt.mobile.pixiv.core.state.whenState
import neilt.mobile.pixiv.desingsystem.components.views.ErrorView
import neilt.mobile.pixiv.desingsystem.components.views.LoadingView
import neilt.mobile.pixiv.domain.models.details.illustration.IllustrationDetails
import neilt.mobile.pixiv.domain.models.home.Illustration
import neilt.mobile.pixiv.resources.Res
import neilt.mobile.pixiv.resources.author_avatar
import neilt.mobile.pixiv.resources.illustration_description
import neilt.mobile.pixiv.resources.views_and_bookmarks
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun IllustrationDetailsView(
    illustrationId: Int,
) {
//    val factory = rememberPermissionsControllerFactory()
//    val controller = remember(factory) {
//        factory.createPermissionsController()
//    }
//
//    BindEffect(controller)

    val viewModel: IllustrationDetailsViewModel = koinViewModel()

    val state by viewModel.state.collectAsState()
    val relatedItems = remember { mutableStateListOf<Illustration>() }

    LaunchedEffect(illustrationId) {
        viewModel.loadIllustration(illustrationId)
        relatedItems.addAll(viewModel.loadRelatedItems(illustrationId))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        state.whenState<IllustrationDetails>(
            onLoading = { LoadingView() },
            onError = { ErrorView(message = it) },
            onLoaded = {
                IllustrationDetailsContent(
                    illustration = it,
                    onIllustrationDownload = viewModel::downloadIllustration,
                    onProfileClick = viewModel::onProfileClick,
                    onTagClick = viewModel::onTagClick,
                    relatedItems = relatedItems,
                    onRelatedItemClick = viewModel::onRelatedItemClick,
                )
            },
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun IllustrationDetailsContent(
    illustration: IllustrationDetails,
    onIllustrationDownload: (url: String?) -> Unit,
    onProfileClick: (userId: Int) -> Unit = {},
    onTagClick: (text: String) -> Unit = {},
    relatedItems: List<Illustration>,
    onRelatedItemClick: (Int) -> Unit,
) {
    val isSinglePageEmpty = illustration.metaSinglePage.isEmpty()
    val imageUrls = if (isSinglePageEmpty) illustration.metaPages else listOf(illustration.metaSinglePage)

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 16.dp),
    ) {
        items(imageUrls) { image ->
            AsyncImage(
                model = image,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .combinedClickable(
                        onClick = { },
                        onLongClick = {
                            onIllustrationDownload(
                                image.originalImage ?: image.original,
                            )
                        },
                    ),
                contentScale = ContentScale.Crop,
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)),
                ) {
                    AsyncImage(
                        model = illustration.user.profileImageUrl,
                        contentDescription = stringResource(Res.string.author_avatar),
                        modifier = Modifier
                            .clip(CircleShape)
                            .clickable {
                                onProfileClick(illustration.user.id)
                            },
                        contentScale = ContentScale.Crop,
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = illustration.user.name, style = MaterialTheme.typography.titleMedium)
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Text(
                text = stringResource(
                    Res.string.views_and_bookmarks,
                    illustration.views,
                    illustration.bookmarks,
                ),
                modifier = Modifier.padding(horizontal = 16.dp),
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(horizontal = 16.dp),
            ) {
                items(illustration.tags) { tag ->
                    TagChip(tag.name, onTagClick)
                }
            }
        }

        item {
            Text(
                text = "Related Illustrations",
                modifier = Modifier.padding(horizontal = 16.dp),
                style = MaterialTheme.typography.titleLarge,
            )
        }

        items(relatedItems) {
            IllustrationItem(illustration = it) {
                onRelatedItemClick(it.id)
            }
        }
    }
}

@Composable
private fun TagChip(
    text: String,
    onClick: (text: String) -> Unit = {},
) {
    Box(
        modifier = Modifier
            .clickable {
                onClick(text)
            }
            .background(
                color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f),
                shape = RoundedCornerShape(16.dp),
            )
            .padding(horizontal = 12.dp, vertical = 6.dp),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.secondary,
        )
    }
}

@Composable
private fun IllustrationItem(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(4.dp),
    illustration: Illustration,
    onClick: () -> Unit,
) {
    val contentDescription = stringResource(Res.string.illustration_description, illustration.title)

    Card(
        modifier = modifier
            .padding(contentPadding)
            .fillMaxWidth()
            .aspectRatio(.75f)
            .semantics { this.contentDescription = contentDescription },
        onClick = onClick,
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            AsyncImage(
                model = illustration.imageUrls,
                contentDescription = illustration.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            )

            Text(
                text = illustration.title,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                style = MaterialTheme.typography.titleSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                softWrap = false,
            )
        }
    }
}
