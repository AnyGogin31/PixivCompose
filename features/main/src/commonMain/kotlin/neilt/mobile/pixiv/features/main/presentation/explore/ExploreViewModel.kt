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

package neilt.mobile.pixiv.features.main.presentation.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import neilt.mobile.pixiv.core.state.ErrorState
import neilt.mobile.pixiv.core.state.LoadedState
import neilt.mobile.pixiv.core.state.LoadingState
import neilt.mobile.pixiv.core.state.ViewState
import neilt.mobile.pixiv.domain.models.requests.SearchIllustrationsRequest
import neilt.mobile.pixiv.domain.repositories.search.SearchRepository

internal class ExploreViewModel(
    private val searchRepository: SearchRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow<ViewState>(Empty)
    val uiState: StateFlow<ViewState> = _uiState

    fun searchIllustrations(keyword: String) {
        if (keyword.isBlank()) {
            _uiState.value = Empty
            return
        }

        viewModelScope.launch {
            _uiState.value = LoadingState

            try {
                val results = searchRepository.getSearchIllustrations(
                    SearchIllustrationsRequest(
                        keyword = keyword,
                        sortOrder = null,
                        searchTarget = null,
                        aiType = null,
                        minBookmarks = null,
                        maxBookmarks = null,
                        startDate = null,
                        endDate = null,
                    ),
                )

                _uiState.value =
                    if (results.isEmpty()) {
                        Empty
                    } else {
                        LoadedState(data = results)
                    }
            } catch (e: Exception) {
                _uiState.value = ErrorState(
                    message = e.localizedMessage ?: "Error searching illustrations",
                )
            }
        }
    }
}
