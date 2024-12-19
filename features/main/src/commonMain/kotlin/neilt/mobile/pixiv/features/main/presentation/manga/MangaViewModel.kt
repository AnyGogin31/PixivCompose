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

package neilt.mobile.pixiv.features.main.presentation.manga

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import neilt.mobile.core.navigation.Navigator
import neilt.mobile.pixiv.core.state.ErrorState
import neilt.mobile.pixiv.core.state.LoadedState
import neilt.mobile.pixiv.core.state.LoadingState
import neilt.mobile.pixiv.core.state.ViewState
import neilt.mobile.pixiv.domain.models.details.illustration.Tag
import neilt.mobile.pixiv.domain.models.home.Illustration
import neilt.mobile.pixiv.domain.models.requests.SearchIllustrationsRequest
import neilt.mobile.pixiv.domain.repositories.home.HomeRepository
import neilt.mobile.pixiv.domain.repositories.search.SearchRepository
import neilt.mobile.pixiv.features.details.presentation.PixivDetailsSection
import neilt.mobile.pixiv.features.main.presentation.home.Empty

internal class MangaViewModel(
    private val homeRepository: HomeRepository,
    private val searchRepository: SearchRepository,
    private val navigator: Navigator,
) : ViewModel() {
    private val _state = MutableStateFlow<ViewState>(Empty)
    val state: StateFlow<ViewState> = _state.asStateFlow()

    init {
        loadManga()
    }

    private fun loadManga() {
        viewModelScope.launch {
            _state.value = LoadingState
            try {
                val manga = withContext(Dispatchers.IO) {
                    homeRepository.getRecommendedManga(
                        includeRankingIllustrations = false,
                        includePrivacyPolicy = false,
                    )
                }
                _state.value = LoadedState(data = manga)
            } catch (e: Exception) {
                _state.value = ErrorState(
                    message = e.message ?: "Error loading manga",
                )
            }
        }
    }

    suspend fun loadMoreManga(offset: Int, keyword: String? = null): List<Illustration> {
        return withContext(Dispatchers.IO) {
            if (keyword.isNullOrEmpty()) {
                homeRepository.getRecommendedManga(
                    includeRankingIllustrations = false,
                    includePrivacyPolicy = false,
                    offset = offset,
                )
            } else {
                searchRepository.getSearchManga(
                    SearchIllustrationsRequest(
                        keyword = keyword,
                        sortOrder = null,
                        searchTarget = null,
                        aiType = null,
                        minBookmarks = null,
                        maxBookmarks = null,
                        startDate = null,
                        endDate = null,
                        offset = offset,
                    ),
                )
            }
        }
    }

    fun navigateToMangaDetails(illustrationId: Int) {
        viewModelScope.launch {
            navigator.navigateTo(
                PixivDetailsSection.IllustrationDetailsScreen(illustrationId),
            )
        }
    }

    fun searchManga(keyword: String? = null) {
        viewModelScope.launch {
            _state.value = LoadingState

            try {
                val result = loadMoreManga(0, keyword)

                _state.value =
                    if (result.isEmpty()) {
                        Empty
                    } else {
                        LoadedState(data = result)
                    }
            } catch (e: Exception) {
                _state.value = ErrorState(
                    message = e.message ?: "Error searching manga",
                )
            }
        }
    }

    private val _predictionTags = MutableStateFlow<List<Tag>>(emptyList())
    val predictionTags: StateFlow<List<Tag>> = _predictionTags

    fun fetchTagsPrediction(query: String) {
        if (query.isBlank()) {
            _predictionTags.value = emptyList()
            return
        }

        viewModelScope.launch {
            try {
                _predictionTags.value = searchRepository.getSearchPredictionTags(query)
            } catch (e: Exception) {
                _predictionTags.value = emptyList()
            }
        }
    }
}
