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

// TODO: Rework after complete code migration

package neilt.mobile.pixiv.features.details.presentation.illustration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowWidthSizeClass
import coil3.compose.AsyncImage
import neilt.mobile.pixiv.desingsystem.components.views.ErrorView
import neilt.mobile.pixiv.desingsystem.foundation.suite.NavigationSuiteScope
import neilt.mobile.pixiv.domain.models.details.illustration.IllustrationDetails
import neilt.mobile.pixiv.domain.models.home.ImageUrls
import neilt.mobile.pixiv.resources.Res
import neilt.mobile.pixiv.resources.author_avatar
import neilt.mobile.pixiv.resources.views_and_bookmarks
import org.jetbrains.compose.resources.stringResource

@Composable
fun IllustrationView(
    uiState: IllustrationViewState,
    navigationSuiteScope: NavigationSuiteScope,
    onCloseClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    IllustrationViewSinglePane(
        uiState = uiState,
        navigationSuiteScope = navigationSuiteScope,
        onCloseClick = onCloseClick,
        modifier = modifier,
    )
}

@Composable
private fun IllustrationViewSinglePane(
    uiState: IllustrationViewState,
    navigationSuiteScope: NavigationSuiteScope,
    onCloseClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val windowWidthSizeClass: WindowWidthSizeClass = navigationSuiteScope
        .windowSizeClass
        .windowWidthSizeClass

    Column(
        modifier = modifier,
    ) {
        IllustrationViewTopBar(
            onCloseClick = onCloseClick,
        )

        when {
            uiState.isLoading -> {
                ShimmerIllustrationDetails(
                    windowWidthSizeClass = windowWidthSizeClass,
                )
            }

            uiState.errorMessage != null -> ErrorView(uiState.errorMessage)

            uiState.illustration != null -> {
                IllustrationDetails(
                    illustrationDetails = uiState.illustration,
                    windowWidthSizeClass = windowWidthSizeClass,
                )
            }
        }
    }
}

@Composable
private fun IllustrationViewTopBar(
    onCloseClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        contentAlignment = Alignment.TopEnd,
    ) {
        IconButton(onClick = onCloseClick) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = null, // TODO
            )
        }
    }
}

@Composable
private fun IllustrationDetails(
    illustrationDetails: IllustrationDetails,
    windowWidthSizeClass: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
) {
    val gridColumns by rememberGridCells(windowWidthSizeClass)

    val (imageUrls, isSinglePage) = illustrationDetails.extractImageUrls()

    LazyVerticalGrid(
        columns = gridColumns,
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(
            count = imageUrls.size,
            key = { index: Int -> index },
            span = { _: Int ->
                GridItemSpan(
                    currentLineSpan = if (isSinglePage) maxLineSpan else 1,
                )
            },
            contentType = { _: Int ->
                DetailsItemContentType.ILLUSTRATION_IMAGES
            },
        ) { index: Int ->
            AsyncImage(
                model = imageUrls[index],
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                contentScale = ContentScale.Crop,
            )
        }

        item(
            span = {
                GridItemSpan(
                    currentLineSpan = maxLineSpan,
                )
            },
            contentType = {
                DetailsItemContentType.SPACER
            },
        ) {
            Spacer(
                modifier = Modifier.height(16.dp),
            )
        }

        item(
            span = {
                GridItemSpan(
                    currentLineSpan = maxLineSpan,
                )
            },
            contentType = {
                DetailsItemContentType.AUTHOR
            },
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape),
                ) {
                    AsyncImage(
                        model = illustrationDetails.user.profileImageUrl,
                        contentDescription = stringResource(Res.string.author_avatar),
                        modifier = Modifier.clip(CircleShape),
                        contentScale = ContentScale.Crop,
                    )
                }
                Spacer(
                    modifier = Modifier.width(8.dp),
                )

                Text(
                    text = illustrationDetails.user.name,
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }

        item(
            span = {
                GridItemSpan(
                    currentLineSpan = maxLineSpan,
                )
            },
            contentType = {
                DetailsItemContentType.SPACER
            },
        ) {
            Spacer(
                modifier = Modifier.height(16.dp),
            )
        }

        item(
            span = {
                GridItemSpan(
                    currentLineSpan = maxLineSpan,
                )
            },
            contentType = {
                DetailsItemContentType.POPULARITY
            },
        ) {
            Text(
                text = stringResource(
                    Res.string.views_and_bookmarks,
                    illustrationDetails.views,
                    illustrationDetails.bookmarks,
                ),
                modifier = Modifier.padding(horizontal = 16.dp),
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
private fun ShimmerIllustrationDetails(
    windowWidthSizeClass: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
) {
    val gridColumns by rememberGridCells(windowWidthSizeClass)

    TODO()
}

@Composable
private fun rememberGridCells(windowWidthSizeClass: WindowWidthSizeClass): State<GridCells> {
    return remember(windowWidthSizeClass) {
        derivedStateOf {
            when (windowWidthSizeClass) {
                WindowWidthSizeClass.COMPACT -> GridCells.Fixed(1)
                WindowWidthSizeClass.MEDIUM -> GridCells.Fixed(2)
                WindowWidthSizeClass.EXPANDED -> GridCells.Fixed(2)

                else -> GridCells.Fixed(2)
            }
        }
    }
}

private fun IllustrationDetails.extractImageUrls(): Pair<List<ImageUrls>, Boolean> {
    val images = metaPages.ifEmpty { listOf(metaSinglePage) }
    val isSinglePage = images.size < 2

    return Pair(images, isSinglePage)
}

private enum class DetailsItemContentType {
    ILLUSTRATION_IMAGES,
    SPACER,
    AUTHOR,
    POPULARITY,
}
