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

package neilt.mobile.pixiv.features.search.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import neilt.mobile.pixiv.desingsystem.components.views.ErrorView
import neilt.mobile.pixiv.desingsystem.foundation.bars.DockedSearchBar
import neilt.mobile.pixiv.desingsystem.foundation.fake.ShimmerBox
import neilt.mobile.pixiv.desingsystem.foundation.fake.ShimmerLazyColumn
import neilt.mobile.pixiv.domain.models.details.illustration.Tag

@Composable
fun SearchView(
    uiState: SearchViewState,
    onQueryChange: (query: String) -> Unit,
    onTagClick: (tag: Tag) -> Unit,
    modifier: Modifier = Modifier,
) {
    var isExpanded by remember { mutableStateOf(false) }
    val onExpandedChange = fun(expanded: Boolean) {
        isExpanded = expanded
    }

    var searchQuery by remember { mutableStateOf("") }
    val onSearchQueryChange = fun(query: String) {
        searchQuery = query
    }

    DockedSearchBar(
        onQueryChange = onQueryChange,
        searchQuery = searchQuery,
        onSearchQueryChange = onSearchQueryChange,
        isExpanded = isExpanded,
        onExpandedChange = onExpandedChange,
        modifier = modifier,
    ) {
        when {
            uiState.isLoading -> ShimmerAutoCompleteList()

            uiState.errorMessage != null -> ErrorView(uiState.errorMessage)

            uiState.tags != null -> {
                AutoCompleteList(
                    tags = uiState.tags,
                    onTagClick = { tag: Tag ->
                        onTagClick(tag)
                        onSearchQueryChange(tag.name)
                        onExpandedChange(false)
                    },
                )
            }
        }
    }
}

@Composable
private fun AutoCompleteList(
    tags: List<Tag>,
    onTagClick: (tag: Tag) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
    ) {
        items(
            items = tags,
            key = { tag: Tag ->
                tag.name
            },
        ) { tag: Tag ->
            Text(
                text = buildString {
                    append(tag.name)
                    tag.translatedName?.let { append(" ($it)") }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onTagClick(tag)
                    },
            )
        }
    }
}

@Composable
private fun ShimmerAutoCompleteList(
    modifier: Modifier = Modifier,
) {
    ShimmerLazyColumn(
        itemCount = 8,
        modifier = modifier,
    ) {
        ShimmerBox(
            modifier = Modifier
                .height(20.dp)
                .fillMaxWidth(),
        )
    }
}
