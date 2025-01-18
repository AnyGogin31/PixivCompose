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

package neilt.mobile.pixiv.features.main.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowWidthSizeClass
import coil3.compose.AsyncImage
import neilt.mobile.pixiv.core.sugar.replaceIfEquals
import neilt.mobile.pixiv.desingsystem.components.list.InfiniteScrollLazyVerticalGrid
import neilt.mobile.pixiv.desingsystem.components.views.ErrorView
import neilt.mobile.pixiv.desingsystem.foundation.fake.ShimmerBox
import neilt.mobile.pixiv.desingsystem.foundation.fake.ShimmerLazyVerticalGrid
import neilt.mobile.pixiv.desingsystem.foundation.pane.TwoPane
import neilt.mobile.pixiv.desingsystem.foundation.suite.NavigationContentType
import neilt.mobile.pixiv.desingsystem.foundation.suite.NavigationSuiteScope
import neilt.mobile.pixiv.domain.models.home.Illustration
import neilt.mobile.pixiv.features.details.presentation.illustration.IllustrationViewNavigation

@Composable
fun HomeView(
    uiState: HomeViewState,
    navigationSuiteScope: NavigationSuiteScope,
    onCloseClick: () -> Unit,
    onIllustrationClick: (illustration: Illustration) -> Unit,
    loadMoreIllustrations: suspend (offset: Int) -> List<Illustration>,
    modifier: Modifier = Modifier,
) {
    when (navigationSuiteScope.contentType) {
        NavigationContentType.DUAL_PANE -> {
            HomeViewDualPane(
                uiState = uiState,
                navigationSuiteScope = navigationSuiteScope,
                onCloseClick = onCloseClick,
                onIllustrationClick = onIllustrationClick,
                loadMoreIllustrations = loadMoreIllustrations,
                modifier = modifier,
            )
        }

        NavigationContentType.SINGLE_PANE -> {
            HomeViewSinglePane(
                uiState = uiState,
                navigationSuiteScope = navigationSuiteScope,
                onCloseClick = onCloseClick,
                onIllustrationClick = onIllustrationClick,
                loadMoreIllustrations = loadMoreIllustrations,
                modifier = modifier,
            )
        }
    }
}

@Composable
private fun HomeViewDualPane(
    uiState: HomeViewState,
    navigationSuiteScope: NavigationSuiteScope,
    onCloseClick: () -> Unit,
    onIllustrationClick: (illustration: Illustration) -> Unit,
    loadMoreIllustrations: suspend (offset: Int) -> List<Illustration>,
    modifier: Modifier = Modifier,
) {
    val windowWidthSizeClass: WindowWidthSizeClass = navigationSuiteScope
        .windowSizeClass
        .windowWidthSizeClass

    var splitFraction by remember { mutableFloatStateOf(1.0f) }

    TwoPane(
        firstContent = {
            when {
                uiState.isLoading -> {
                    ShimmerIllustartionGallery(
                        windowWidthSizeClass = windowWidthSizeClass,
                    )
                }

                uiState.errorMessage != null -> ErrorView(uiState.errorMessage)

                else -> {
                    IllustartionGallery(
                        windowWidthSizeClass = windowWidthSizeClass,
                        initialItems = uiState.illustrations,
                        loadMoreIllustrations = loadMoreIllustrations,
                        onIllustrationClick = onIllustrationClick,
                    )
                }
            }
        },
        secondContent = {
            when {
                uiState.selectedIllustration != null -> {
                    splitFraction = splitFraction.replaceIfEquals(0.5f, 0.5f)
                    IllustrationViewNavigation(
                        illustrationId = uiState.selectedIllustration.id,
                        onCloseClick = onCloseClick,
                    )
                }

                else -> {
                    splitFraction = splitFraction.replaceIfEquals(1.0f, 1.0f)
                }
            }
        },
        modifier = modifier,
        splitFraction = splitFraction,
    )
}

@Composable
private fun HomeViewSinglePane(
    uiState: HomeViewState,
    navigationSuiteScope: NavigationSuiteScope,
    onCloseClick: () -> Unit,
    onIllustrationClick: (illustration: Illustration) -> Unit,
    loadMoreIllustrations: suspend (offset: Int) -> List<Illustration>,
    modifier: Modifier = Modifier,
) {
    val windowWidthSizeClass: WindowWidthSizeClass = navigationSuiteScope
        .windowSizeClass
        .windowWidthSizeClass

    Box(
        modifier = modifier,
    ) {
        when {
            uiState.isLoading -> {
                ShimmerIllustartionGallery(
                    windowWidthSizeClass = windowWidthSizeClass,
                )
            }

            uiState.errorMessage != null -> ErrorView(uiState.errorMessage)

            uiState.selectedIllustration != null -> {
                IllustrationViewNavigation(
                    illustrationId = uiState.selectedIllustration.id,
                    onCloseClick = onCloseClick,
                )
            }

            else -> {
                IllustartionGallery(
                    windowWidthSizeClass = windowWidthSizeClass,
                    initialItems = uiState.illustrations,
                    loadMoreIllustrations = loadMoreIllustrations,
                    onIllustrationClick = onIllustrationClick,
                )
            }
        }
    }
}

// TODO: Rework after complete code migration
@Composable
private fun IllustartionGallery(
    windowWidthSizeClass: WindowWidthSizeClass,
    initialItems: List<Illustration>,
    loadMoreIllustrations: suspend (offset: Int) -> List<Illustration>,
    onIllustrationClick: (illustration: Illustration) -> Unit,
    modifier: Modifier = Modifier,
) {
    val gridColumns by rememberGridCells(windowWidthSizeClass)

    InfiniteScrollLazyVerticalGrid(
        columns = gridColumns,
        keySelector = { illustration: Illustration ->
            illustration.id
        },
        content = { illustration: Illustration ->
            IllustrationItem(
                illustration = illustration,
                onClick = {
                    onIllustrationClick(illustration)
                },
            )
        },
        loadingIndicator = {
            CircularProgressIndicator()
        },
        initialItems = initialItems,
        loadMoreItems = { currentItems: List<Illustration> ->
            loadMoreIllustrations(currentItems.size)
        },
        modifier = modifier,
    )
}

// TODO: Rework after complete code migration
@Composable
private fun IllustrationItem(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(4.dp),
    illustration: Illustration,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .padding(contentPadding)
            .fillMaxWidth()
            .aspectRatio(.75f),
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

@Composable
private fun ShimmerIllustartionGallery(
    windowWidthSizeClass: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
) {
    val gridColumns by rememberGridCells(windowWidthSizeClass)

    ShimmerLazyVerticalGrid(
        itemCount = 20,
        columns = gridColumns,
        modifier = modifier,
    ) {
        ShimmerBox(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(.75f),
        )
    }
}

@Composable
private fun rememberGridCells(windowWidthSizeClass: WindowWidthSizeClass): State<GridCells> {
    return remember(windowWidthSizeClass) {
        derivedStateOf {
            when (windowWidthSizeClass) {
                WindowWidthSizeClass.COMPACT -> GridCells.Fixed(2)
                WindowWidthSizeClass.MEDIUM -> GridCells.Fixed(4)
                WindowWidthSizeClass.EXPANDED -> GridCells.Fixed(4)

                else -> GridCells.Fixed(2)
            }
        }
    }
}
