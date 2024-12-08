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

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import neilt.mobile.pixiv.core.state.whenState
import neilt.mobile.pixiv.desingsystem.components.views.ErrorView
import neilt.mobile.pixiv.desingsystem.components.views.LoadingView
import neilt.mobile.pixiv.domain.models.home.Illustration
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun HomeView(
    viewModel: HomeViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()

    state.whenState<List<Illustration>>(
        onLoading = { LoadingView() },
        onError = { ErrorView(message = it) },
        onLoaded = {
            IllustrationsGallery(
                illustrations = it,
                onIllustrationSelected = viewModel::navigateToIllustrationDetails,
            )
        },
    )
}

@Composable
internal fun IllustrationsGallery(
    modifier: Modifier = Modifier,
    illustrations: List<Illustration>,
    onIllustrationSelected: (Int) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.fillMaxSize(),
        state = rememberLazyGridState(),
        contentPadding = PaddingValues(8.dp),
        reverseLayout = false,
    ) {
        items(
            items = illustrations,
            key = { it.id },
        ) { item ->
            IllustrationItem(
                illustration = item,
                onClick = {
                    onIllustrationSelected(item.id)
                },
            )
        }
    }
}

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
            .aspectRatio(.75f)
            .semantics { contentDescription = "Illustration: ${illustration.title}" },
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
