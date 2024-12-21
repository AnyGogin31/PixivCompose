package neilt.mobile.pixiv.features.search.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import kotlinx.serialization.Serializable
import neilt.mobile.core.navigation.Destination
import neilt.mobile.pixiv.features.search.presentation.explore.ExploreView

@Serializable
data object PixivSearchSection : Destination {
    @Serializable
    data object ExploreScreen : Destination
}

fun NavGraphBuilder.addPixivSearchSection() {
    navigation<PixivSearchSection>(
        startDestination = PixivSearchSection.ExploreScreen,
    ) {
        composable<PixivSearchSection.ExploreScreen> { ExploreView() }
    }
}
