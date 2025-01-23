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

package neilt.mobile.pixiv.desingsystem.components.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@Composable
fun <T> InfiniteScrollLazyVerticalGrid(
    modifier: Modifier = Modifier,
    columns: GridCells,
    gridState: LazyGridState = rememberLazyGridState(),
    keySelector: (T) -> Any,
    content: @Composable (T) -> Unit,
    loadingIndicator: @Composable () -> Unit,
    preloadOffset: Int = 5,
    initialItems: List<T>,
    loadMoreItems: suspend (currentItems: List<T>) -> List<T>,
) {
    var items by remember { mutableStateOf(initialItems) }
    var isLoading by remember { mutableStateOf(false) }

    // Observe the scroll state and trigger `loadMore` when the end is near
    LaunchedEffect(key1 = gridState) {
        snapshotFlow { gridState.layoutInfo }
            .map { layoutInfo ->
                val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()
                val totalItemsCount = layoutInfo.totalItemsCount
                val viewportHeight = layoutInfo.viewportEndOffset - layoutInfo.viewportStartOffset
                val contentHeight = lastVisibleItem?.offset?.y?.plus(lastVisibleItem.size.height) ?: 0

                lastVisibleItem != null &&
                    (lastVisibleItem.index >= totalItemsCount - preloadOffset || contentHeight <= viewportHeight)
            }
            .distinctUntilChanged()
            .collect { shouldLoadMore ->
                if (shouldLoadMore && !isLoading) {
                    isLoading = true
                    val newItems = loadMoreItems(items)
                    items = items + newItems
                    isLoading = false
                }
            }
    }

    // Render the LazyVerticalGrid with infinite scroll
    LazyVerticalGrid(
        columns = columns,
        modifier = modifier,
        state = gridState,
    ) {
        // Add grid items
        items(items = items, key = keySelector) { item: T ->
            content(item)
        }
        // Add loading indicator at the end when loading
        if (isLoading) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    loadingIndicator()
                }
            }
        }
    }
}
