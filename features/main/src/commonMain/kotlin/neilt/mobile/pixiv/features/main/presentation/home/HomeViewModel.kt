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

package neilt.mobile.pixiv.features.main.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
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
import neilt.mobile.pixiv.domain.repositories.home.HomeRepository
import neilt.mobile.pixiv.features.illustration.presentation.PixivIllustrationSection

internal class HomeViewModel(
    private val homeRepository: HomeRepository,
    private val navigator: Navigator,
) : ViewModel() {
    private val _state = MutableStateFlow<ViewState>(LoadingState)
    val state: StateFlow<ViewState> = _state.asStateFlow()

    init {
        loadIllustrations()
    }

    private fun loadIllustrations() {
        viewModelScope.launch {
            _state.value = LoadingState
            try {
                val illustrations = withContext(Dispatchers.IO) {
                    homeRepository.getRecommendedIllustrations(
                        includeRankingIllustrations = false,
                        includePrivacyPolicy = false,
                    )
                }
                _state.value = LoadedState(data = illustrations)
            } catch (e: Exception) {
                _state.value = ErrorState(
                    message = e.localizedMessage ?: "Error loading illustrations",
                )
            }
        }
    }

    fun navigateToIllustrationDetails(illustrationId: Int) {
        viewModelScope.launch {
            navigator.navigateTo(
                PixivIllustrationSection.IllustrationDetailsScreen(illustrationId),
            )
        }
    }
}
