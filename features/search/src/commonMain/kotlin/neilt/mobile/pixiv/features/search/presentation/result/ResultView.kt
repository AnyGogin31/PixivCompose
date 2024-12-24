package neilt.mobile.pixiv.features.search.presentation.result

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import neilt.mobile.pixiv.desingsystem.components.list.InfiniteScrollLazyVerticalGrid
import neilt.mobile.pixiv.domain.models.home.Illustration
import neilt.mobile.pixiv.features.search.presentation.explore.ExploreType
import neilt.mobile.pixiv.resources.Res
import neilt.mobile.pixiv.resources.illustration_description
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun ResultView(
    exploreType: ExploreType,
    keyword: String,
    viewModel: ResultViewModel = koinViewModel(),
) {
    val initialItems = mutableStateListOf<Illustration>()

    LaunchedEffect(keyword) {
        initialItems += viewModel.loadIllustrations(0, exploreType, keyword)
    }

    IllustrationsGallery(
        initialItems = initialItems,
        onIllustrationSelected = {},
        loadMoreItems = {
            viewModel.loadIllustrations(it, exploreType, keyword)
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
