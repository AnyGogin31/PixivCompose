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

package neilt.mobile.pixiv.ui.screens.explore

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.network.NetworkHeaders
import coil3.network.httpHeaders
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.request.transformations
import kotlinx.coroutines.launch
import neilt.mobile.pixiv.domain.models.home.Illustration
import org.koin.androidx.compose.koinViewModel

@Composable
fun ExploreScreen(
    viewModel: ExploreViewModel = koinViewModel(),
) {
    val state by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    var searchText by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        OutlinedTextField(
            value = searchText,
            onValueChange = { newText ->
                searchText = newText
            },
            label = { Text("Search") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    coroutineScope.launch {
                        viewModel.searchIllustrations(searchText.text)
                    }
                },
            ),
        )

        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(CenterHorizontally))
        } else {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                modifier = Modifier.padding(8.dp),
            ) {
                items(state.illustrations) { illustration ->
                    IllustrationItem(illustration = illustration)
                }
            }
        }
    }
}

@Composable
private fun IllustrationItem(
    modifier: Modifier = Modifier,
    illustration: Illustration,
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(illustration.imageUrls.mediumUrl)
            .httpHeaders(
                NetworkHeaders.Builder()
                    .set("referer", "https://app-api.pixiv.net/")
                    .build(),
            )
            .crossfade(true)
            .transformations()
            .build(),
        contentDescription = illustration.title,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth(),
    )
}
