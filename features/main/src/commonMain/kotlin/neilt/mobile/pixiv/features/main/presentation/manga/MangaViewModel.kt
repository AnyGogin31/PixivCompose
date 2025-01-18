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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import neilt.mobile.pixiv.domain.models.home.Illustration
import neilt.mobile.pixiv.domain.repositories.home.HomeRepository

internal class MangaViewModel(
    private val homeRepository: HomeRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(MangaViewState())
    val uiState: StateFlow<MangaViewState> = _uiState.asStateFlow()

    init {
        loadIllustrations()
    }

    private fun loadIllustrations() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val illustrations = withContext(Dispatchers.IO) {
                    homeRepository.getRecommendedManga(
                        includeRankingIllustrations = false,
                        includePrivacyPolicy = false,
                    )
                }
                _uiState.update { it.copy(isLoading = false, illustrations = illustrations) }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Error loading illustrations",
                    )
                }
            }
        }
    }

    suspend fun loadMoreIllustrations(offset: Int): List<Illustration> {
        return withContext(Dispatchers.IO) {
            homeRepository.getRecommendedManga(
                includeRankingIllustrations = false,
                includePrivacyPolicy = false,
                offset = offset,
            )
        }
    }

    fun onIllustrationClick(illustration: Illustration) {
        _uiState.update { it.copy(selectedIllustration = illustration) }
    }
}
