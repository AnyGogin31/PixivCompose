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

package neilt.mobile.pixiv.features.main.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import neilt.mobile.pixiv.core.state.ErrorState
import neilt.mobile.pixiv.core.state.LoadedState
import neilt.mobile.pixiv.core.state.LoadingState
import neilt.mobile.pixiv.core.state.ViewState
import neilt.mobile.pixiv.domain.repositories.auth.AuthRepository
import neilt.mobile.pixiv.domain.repositories.profile.ProfileRepository

internal class ProfileViewModel(
    private val profileRepository: ProfileRepository,
    private val authRepository: AuthRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow<ViewState>(LoadingState)
    val uiState: StateFlow<ViewState> = _uiState

    init {
        fetchCurrentUserProfile()
    }

    private fun fetchCurrentUserProfile() {
        viewModelScope.launch {
            _uiState.value = LoadingState
            try {
                val activeUser = withContext(Dispatchers.IO) { authRepository.getActiveUser() }
                if (activeUser != null) {
                    val userDetail = profileRepository.getUserDetail(activeUser.id.toInt())
                    _uiState.value = LoadedState(data = userDetail)
                } else {
                    _uiState.value = ErrorState("No active user found")
                }
            } catch (e: Exception) {
                _uiState.value = ErrorState(
                    e.message ?: "Error fetching profile",
                )
            }
        }
    }
}
