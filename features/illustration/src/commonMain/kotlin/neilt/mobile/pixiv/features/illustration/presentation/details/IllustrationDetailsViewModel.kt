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

package neilt.mobile.pixiv.features.illustration.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import neilt.mobile.pixiv.core.state.ErrorState
import neilt.mobile.pixiv.core.state.LoadedState
import neilt.mobile.pixiv.core.state.LoadingState
import neilt.mobile.pixiv.core.state.ViewState
import neilt.mobile.pixiv.domain.repositories.details.illustration.IllustrationRepository
import neilt.mobile.pixiv.features.illustration.provider.ToastProvider
import neilt.mobile.pixiv.resources.Res
import neilt.mobile.pixiv.resources.toast_download_complete
import neilt.mobile.pixiv.resources.toast_downloading
import org.jetbrains.compose.resources.getString

internal class IllustrationDetailsViewModel(
    private val illustrationRepository: IllustrationRepository,
    private val toastProvider: ToastProvider,
) : ViewModel() {
    private val _state = MutableStateFlow<ViewState>(LoadingState)
    val state: StateFlow<ViewState> = _state.asStateFlow()

    fun loadIllustration(illustrationId: Int) {
        viewModelScope.launch {
            _state.value = LoadingState
            try {
                val illustration = withContext(Dispatchers.IO) {
                    illustrationRepository.getIllustration(illustrationId)
                }
                _state.value = LoadedState(data = illustration)
            } catch (e: Exception) {
                _state.value = ErrorState(
                    message = e.message ?: "Error loading illustrations",
                )
            }
        }
    }

    fun downloadIllustration(url: String?, fileName: String) {
        if (url.isNullOrEmpty()) {
            return
        }

        viewModelScope.launch {
            toastProvider.showToast(getString(Res.string.toast_downloading))
            illustrationRepository.downloadIllustration(url, fileName)
            toastProvider.showToast(getString(Res.string.toast_download_complete))
        }
    }
}
