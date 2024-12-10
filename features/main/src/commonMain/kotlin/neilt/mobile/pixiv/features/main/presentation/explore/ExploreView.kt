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

package neilt.mobile.pixiv.features.main.presentation.explore

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.flow.StateFlow
import neilt.mobile.pixiv.core.state.ViewState
import neilt.mobile.pixiv.desingsystem.components.views.EmptyView
import neilt.mobile.pixiv.desingsystem.components.views.ErrorView
import neilt.mobile.pixiv.desingsystem.components.views.LoadingView
import neilt.mobile.pixiv.domain.models.details.illustration.Tag
import neilt.mobile.pixiv.domain.models.home.Illustration
import neilt.mobile.pixiv.features.main.presentation.home.IllustrationsGallery
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun ExploreView(
    viewModel: ExploreViewModel = koinViewModel(),
) {
    val state by viewModel.uiState.collectAsState()

    ExploreViewContent(
        uiState = state,
        onSearch = viewModel::searchIllustrations,
        onIllustrationSelected = viewModel::navigateToIllustrationDetails,
        predictionTagsFlow = viewModel.predictionTags,
        onQueryChange = viewModel::fetchTagsPrediction,
    )
}

@Composable
private fun ExploreViewContent(
    uiState: ViewState,
    onSearch: (String) -> Unit,
    onIllustrationSelected: (Int) -> Unit,
    predictionTagsFlow: StateFlow<List<Tag>>,
    onQueryChange: (String) -> Unit,
) {
    val predictionTags by predictionTagsFlow.collectAsState(emptyList())

    val focusManager = LocalFocusManager.current
    val clearFocusAndCollapse: () -> Unit = {
        focusManager.clearFocus()
    }

    Column {
        SearchInputView(
            modifier = Modifier.zIndex(1f),
            onSearch = onSearch,
            onQueryChange = onQueryChange,
            clearFocusAndCollapse = clearFocusAndCollapse,
            isExpanded = predictionTags.isNotEmpty(),
            content = {
                if (predictionTags.isNotEmpty()) {
                    TagsPredictionList(
                        tags = predictionTags,
                        onTagSelected = { tag ->
                            clearFocusAndCollapse()
                            onQueryChange("")
                            onSearch(tag)
                        },
                    )
                }
            },
        )

        uiState.whenStateExtended<List<Illustration>>(
            onLoading = { LoadingView() },
            onEmpty = { EmptyView(message = "No illustrations found") },
            onError = { ErrorView(message = it) },
            onLoaded = {
                IllustrationsGallery(
                    illustrations = it,
                    onIllustrationSelected = onIllustrationSelected,
                )
            },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchInputView(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit,
    onQueryChange: (String) -> Unit,
    clearFocusAndCollapse: () -> Unit,
    isExpanded: Boolean,
    content: @Composable ColumnScope.() -> Unit,
) {
    val isKeyboardVisible by keyboardAsState()

    var query by rememberSaveable { mutableStateOf("") }

    val onExecuteSearch = fun(input: String) {
        clearFocusAndCollapse()
        onSearch(input)
    }

    LaunchedEffect(isKeyboardVisible) {
        if (!isKeyboardVisible) {
            clearFocusAndCollapse()
        }
    }

    DockedSearchBar(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 24.dp,
                vertical = 8.dp,
            ),
        inputField = {
            SearchBarDefaults.InputField(
                query = query,
                onQueryChange = {
                    query = it
                    onQueryChange(it)
                },
                onSearch = onExecuteSearch,
                expanded = isExpanded,
                onExpandedChange = {},
                placeholder = { Text("Search illustrations...") },
            )
        },
        expanded = isExpanded,
        onExpandedChange = {},
        content = content,
    )
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

@Composable
private fun keyboardAsState(): State<Boolean> {
    val isImeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0
    return rememberUpdatedState(isImeVisible)
}
