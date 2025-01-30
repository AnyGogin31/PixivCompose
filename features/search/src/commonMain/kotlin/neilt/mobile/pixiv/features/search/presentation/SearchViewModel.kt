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

package neilt.mobile.pixiv.features.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import neilt.mobile.pixiv.domain.repositories.auth.AuthRepository
import neilt.mobile.pixiv.domain.repositories.profile.ProfileRepository
import neilt.mobile.pixiv.domain.repositories.search.SearchRepository

internal class SearchViewModel(
    private val searchRepository: SearchRepository,
    private val authRepository: AuthRepository,
    private val profileRepository: ProfileRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchViewState())
    val uiState: StateFlow<SearchViewState> = _uiState.asStateFlow()

    init {
        loadProfileImageUrls()
    }

    fun onQueryChange(query: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val tags = withContext(Dispatchers.IO) {
                    searchRepository.getSearchPredictionTags(query)
                }
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        tags = tags,
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Error loading tags",
                    )
                }
            }
        }
    }

    private fun loadProfileImageUrls() {
        viewModelScope.launch {
            val userId = authRepository.getActiveUser()?.id?.toIntOrNull() ?: return@launch
            val profileImageUrls = profileRepository.getUserDetail(userId)
            _uiState.update { it.copy(profileImageUrls = profileImageUrls.user.profileImageUrls) }
        }
    }
}
