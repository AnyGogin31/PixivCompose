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

package neilt.mobile.pixiv.features.search.presentation.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import neilt.mobile.core.navigation.Navigator
import neilt.mobile.pixiv.domain.models.details.illustration.Tag
import neilt.mobile.pixiv.domain.repositories.search.SearchRepository
import neilt.mobile.pixiv.features.search.presentation.PixivSearchSection

internal class ExploreViewModel(
    private val searchRepository: SearchRepository,
    private val navigator: Navigator,
) : ViewModel() {
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

    fun search(exploreType: ExploreType, query: String) {
        viewModelScope.launch {
            navigator.navigateTo(
                PixivSearchSection.ResultScreen(
                    exploreType = exploreType,
                    keyword = query,
                ),
            )
        }
    }
}
