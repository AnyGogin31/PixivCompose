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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import neilt.mobile.pixiv.core.state.whenState
import neilt.mobile.pixiv.desingsystem.components.views.ErrorView
import neilt.mobile.pixiv.desingsystem.components.views.LoadingView
import neilt.mobile.pixiv.domain.models.home.Illustration
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun HomeView(
    viewModel: HomeViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()

    state.whenState<List<Illustration>>(
        onLoading = { LoadingView() },
        onError = { ErrorView(message = it) },
        onLoaded = {
            IllustrationGrid(
                illustrations = it,
                onIllustrationClick = viewModel::navigateToIllustrationDetails,
            )
        },
    )
}

@Composable
private fun IllustrationGrid(
    illustrations: List<Illustration>,
    onIllustrationClick: (Int) -> Unit,
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = Modifier.padding(8.dp),
    ) {
        items(illustrations) { illustration ->
            IllustrationItem(
                illustration = illustration,
                onClick = onIllustrationClick,
            )
        }
    }
}

@Composable
private fun IllustrationItem(
    modifier: Modifier = Modifier,
    illustration: Illustration,
    onClick: (Int) -> Unit,
) {
    AsyncImage(
        model = illustration.imageUrls,
        contentDescription = illustration.title,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth()
            .clickable { onClick(illustration.id) },
    )
}
