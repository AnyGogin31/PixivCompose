package neilt.mobile.pixiv.features.search.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import neilt.mobile.core.navigation.Destination
import neilt.mobile.pixiv.features.search.presentation.explore.ExploreType
import neilt.mobile.pixiv.features.search.presentation.explore.ExploreView

@Serializable
data object PixivSearchSection : Destination {
    @Serializable
    data class ExploreScreen(val exploreType: ExploreType, val query: String?) : Destination
}

fun NavGraphBuilder.addPixivSearchSection() {
    navigation<PixivSearchSection>(
        startDestination = PixivSearchSection.ExploreScreen::class,
    ) {
        composable<PixivSearchSection.ExploreScreen> {
            val args = it.toRoute<PixivSearchSection.ExploreScreen>()
            ExploreView(
                exploreType = args.exploreType,
                query = args.query,
            )
        }
    }
}
