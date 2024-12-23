package neilt.mobile.pixiv.features.search.presentation.explore

import androidx.compose.runtime.Composable
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun ExploreView(
    exploreType: ExploreType,
    query: String?,
    viewModel: ExploreViewModel = koinViewModel(),
) {
}
