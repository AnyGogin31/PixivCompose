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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowWidthSizeClass
import coil3.compose.AsyncImage
import neilt.mobile.pixiv.desingsystem.components.views.ErrorView
import neilt.mobile.pixiv.desingsystem.foundation.suite.NavigationSuiteScope
import neilt.mobile.pixiv.domain.models.details.illustration.IllustrationDetails
import neilt.mobile.pixiv.domain.models.home.ImageUrls

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
    val (imageUrls, isSinglePage) = illustrationDetails.extractImageUrls()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
    ) {
        items(
            count = imageUrls.size,
            key = { index: Int -> index },
            span = { _: Int ->
                GridItemSpan(
                    currentLineSpan = if (isSinglePage) maxLineSpan else 1,
                )
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
    }

    TODO()
}

@Composable
private fun ShimmerIllustrationDetails(
    windowWidthSizeClass: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
) {
    TODO()
}

private fun IllustrationDetails.extractImageUrls(): Pair<List<ImageUrls>, Boolean> {
    val images = metaPages.ifEmpty { listOf(metaSinglePage) }
    val isSinglePage = images.size < 2

    return Pair(images, isSinglePage)
}
