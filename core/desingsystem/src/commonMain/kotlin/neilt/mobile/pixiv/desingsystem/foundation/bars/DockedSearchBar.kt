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

package neilt.mobile.pixiv.desingsystem.foundation.bars

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@OptIn(FlowPreview::class, ExperimentalMaterial3Api::class)
@Composable
fun DockedSearchBar(
    onQueryChange: (query: String) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    var searchQuery by remember { mutableStateOf("") }
    val searchQueryFlow = remember { MutableStateFlow("") }

    var isExpanded by remember { mutableStateOf(false) }
    val onExpandedChange = fun(expanded: Boolean) {
        isExpanded = expanded
    }

    LaunchedEffect(key1 = Unit) {
        searchQueryFlow
            .debounce(500L)
            .onEach { query: String ->
                onQueryChange(query)
            }
            .launchIn(this)
    }

    DockedSearchBar(
        inputField = {
            SearchBarDefaults.InputField(
                query = searchQuery,
                onQueryChange = { query: String ->
                    searchQuery = query
                    searchQueryFlow.value = query
                },
                onSearch = { _: String ->
                    isExpanded = false
                },
                expanded = isExpanded,
                onExpandedChange = onExpandedChange,
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    if (isExpanded) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null, // TODO
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .clickable {
                                    isExpanded = false
                                    searchQuery = ""
                                    searchQueryFlow.value = ""
                                },
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null, // TODO
                            modifier = Modifier.padding(start = 16.dp),
                        )
                    }
                },
            )
        },
        expanded = isExpanded,
        onExpandedChange = onExpandedChange,
        modifier = modifier,
        content = content,
    )
}
