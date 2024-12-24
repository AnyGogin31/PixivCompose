package neilt.mobile.pixiv.features.search.presentation.result

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import neilt.mobile.pixiv.domain.models.home.Illustration
import neilt.mobile.pixiv.domain.models.requests.SearchIllustrationsRequest
import neilt.mobile.pixiv.domain.repositories.search.SearchRepository
import neilt.mobile.pixiv.features.search.presentation.explore.ExploreType

internal class ResultViewModel(
    private val searchRepository: SearchRepository,
) : ViewModel() {
    suspend fun loadIllustrations(
        offset: Int = 0,
        exploreType: ExploreType,
        keyword: String,
    ): List<Illustration> {
        val request = SearchIllustrationsRequest(
            keyword = keyword,
            sortOrder = null,
            searchTarget = null,
            aiType = null,
            minBookmarks = null,
            maxBookmarks = null,
            startDate = null,
            endDate = null,
            offset = offset,
        )

        return withContext(Dispatchers.IO) {
            when (exploreType) {
                ExploreType.ILLUSTRATION -> searchRepository.getSearchIllustrations(request)
                ExploreType.MANGA -> searchRepository.getSearchManga(request)
                ExploreType.NOVEL -> searchRepository.getSearchManga(request)
            }
        }
    }
}
