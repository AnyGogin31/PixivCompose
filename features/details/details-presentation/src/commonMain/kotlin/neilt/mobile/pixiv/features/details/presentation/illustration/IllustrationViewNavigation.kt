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

package neilt.mobile.pixiv.features.details.presentation.illustration

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import neilt.mobile.pixiv.desingsystem.foundation.suite.LocalNavigationSuiteScope
import neilt.mobile.pixiv.desingsystem.foundation.suite.NavigationSuiteScope
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun IllustrationViewNavigation(
    illustrationId: Int,
    onCloseClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel: IllustrationViewModel = koinViewModel()

    LaunchedEffect(key1 = illustrationId) {
        viewModel.loadIllustration(illustrationId)
    }

    val uiState by viewModel.uiState.collectAsState()
    val navigationSuiteScope: NavigationSuiteScope = LocalNavigationSuiteScope.current

    IllustrationView(
        uiState = uiState,
        navigationSuiteScope = navigationSuiteScope,
        onCloseClick = onCloseClick,
        modifier = modifier,
    )
}
