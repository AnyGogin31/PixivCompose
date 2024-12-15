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

package neilt.mobile.pixiv.features.main.presentation.manga

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalFocusManager
import neilt.mobile.pixiv.desingsystem.components.search.SearchBehavior
import neilt.mobile.pixiv.desingsystem.components.search.SearchManager
import neilt.mobile.pixiv.desingsystem.components.views.EmptyView
import neilt.mobile.pixiv.desingsystem.components.views.ErrorView
import neilt.mobile.pixiv.desingsystem.components.views.LoadingView
import neilt.mobile.pixiv.domain.models.home.Illustration
import neilt.mobile.pixiv.features.main.presentation.home.IllustrationsGallery
import neilt.mobile.pixiv.features.main.presentation.home.TagsPredictionList
import neilt.mobile.pixiv.features.main.presentation.home.whenStateExtended
import neilt.mobile.pixiv.resources.Res
import neilt.mobile.pixiv.resources.no_manga_found
import neilt.mobile.pixiv.resources.search_manga_placeholder
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun MangaView(
    viewModel: MangaViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()

    val predictionTags by viewModel.predictionTags.collectAsState(emptyList())

    val focusManager = LocalFocusManager.current
    val clearFocusAndCollapse: () -> Unit = {
        focusManager.clearFocus()
    }

    val illustrationsSearchBehavior = object : SearchBehavior {
        override val onSearch: (String) -> Unit = {
            viewModel.searchManga(it)
            clearFocusAndCollapse()
            onQueryChange("")
        }
        override val onQueryChange: (String) -> Unit = viewModel::fetchTagsPrediction
        override val clearFocusAndCollapse: () -> Unit = clearFocusAndCollapse
        override val isExpanded: Boolean = predictionTags.isNotEmpty()
        override val placeholder: @Composable () -> Unit = {
            Text(text = stringResource(Res.string.search_manga_placeholder))
        }
        override val content: @Composable ColumnScope.() -> Unit = {
            TagsPredictionList(
                tags = predictionTags,
                onTagSelected = {
                    viewModel.searchManga(it)
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
        onEmpty = { EmptyView(message = stringResource(Res.string.no_manga_found)) },
        onError = { ErrorView(message = it) },
        onLoaded = {
            IllustrationsGallery(
                initialItems = it,
                onIllustrationSelected = viewModel::navigateToMangaDetails,
                loadMoreItems = viewModel::loadMoreManga,
            )
        },
    )
}
