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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import neilt.mobile.pixiv.desingsystem.components.list.InfiniteScrollLazyVerticalGrid
import neilt.mobile.pixiv.desingsystem.components.search.SearchBehavior
import neilt.mobile.pixiv.desingsystem.components.search.SearchManager
import neilt.mobile.pixiv.desingsystem.components.views.EmptyView
import neilt.mobile.pixiv.desingsystem.components.views.ErrorView
import neilt.mobile.pixiv.desingsystem.components.views.LoadingView
import neilt.mobile.pixiv.domain.models.details.illustration.Tag
import neilt.mobile.pixiv.domain.models.home.Illustration
import neilt.mobile.pixiv.resources.Res
import neilt.mobile.pixiv.resources.illustration_description
import neilt.mobile.pixiv.resources.no_illustrations_found
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun HomeView(
    viewModel: HomeViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()

    val predictionTags by viewModel.predictionTags.collectAsState(emptyList())

    val focusManager = LocalFocusManager.current
    val clearFocusAndCollapse: () -> Unit = {
        focusManager.clearFocus()
    }

    val illustrationsSearchBehavior = object : SearchBehavior {
        override val onSearch: (String) -> Unit = {
            viewModel.searchIllustrations(it)
            clearFocusAndCollapse()
            onQueryChange("")
        }
        override val onQueryChange: (String) -> Unit = viewModel::fetchTagsPrediction
        override val clearFocusAndCollapse: () -> Unit = clearFocusAndCollapse
        override val isExpanded: Boolean = predictionTags.isNotEmpty()
        override val content: @Composable ColumnScope.() -> Unit = {
            TagsPredictionList(
                tags = predictionTags,
                onTagSelected = {
                    viewModel.searchIllustrations(it)
                    clearFocusAndCollapse()
                    onQueryChange("")
                },
            )
        }
    }

    val searchManager: SearchManager = koinInject()
    searchManager.updateSearchBehavior(illustrationsSearchBehavior)

    state.whenStateExtended<List<Illustration>>(
        onLoading = { LoadingView() },
        onEmpty = { EmptyView(message = stringResource(Res.string.no_illustrations_found)) },
        onError = { ErrorView(message = it) },
        onLoaded = {
            IllustrationsGallery(
                initialItems = it,
                onIllustrationSelected = viewModel::navigateToIllustrationDetails,
                loadMoreItems = viewModel::loadMoreIllustrations,
            )
        },
    )
}

@Composable
internal fun IllustrationsGallery(
    modifier: Modifier = Modifier,
    initialItems: List<Illustration>,
    onIllustrationSelected: (Int) -> Unit,
    loadMoreItems: suspend (offset: Int) -> List<Illustration>,
) {
    InfiniteScrollLazyVerticalGrid(
        modifier = modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
        initialItems = initialItems,
        keySelector = { it.id },
        content = { item ->
            IllustrationItem(
                illustration = item,
                onClick = {
                    onIllustrationSelected(item.id)
                },
            )
        },
        loadingIndicator = {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(48.dp)
                    .padding(16.dp),
            )
        },
        loadMoreItems = { loadMoreItems(it.size) },
    )
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

@Composable
private fun TagsPredictionList(
    tags: List<Tag>,
    onTagSelected: (String) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
    ) {
        items(tags) { tag ->
            Text(
                text = buildString {
                    append(tag.name)
                    tag.translatedName?.let { append(" ($it)") }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onTagSelected(tag.name) }
                    .padding(16.dp),
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}
