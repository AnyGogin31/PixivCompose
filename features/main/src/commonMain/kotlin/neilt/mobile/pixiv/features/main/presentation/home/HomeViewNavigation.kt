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

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import neilt.mobile.pixiv.desingsystem.foundation.suite.LocalNavigationSuiteScope
import neilt.mobile.pixiv.desingsystem.foundation.suite.NavigationSuiteScope
import neilt.mobile.pixiv.features.main.presentation.PixivMainSection
import org.koin.compose.viewmodel.koinViewModel

internal fun NavGraphBuilder.addPixivHomeView() {
    composable<PixivMainSection.HomeScreen> {
        val viewModel: HomeViewModel = koinViewModel()

        val uiState by viewModel.uiState.collectAsState()
        val navigationSuiteScope: NavigationSuiteScope = LocalNavigationSuiteScope.current

        HomeView(
            uiState = uiState,
            navigationSuiteScope = navigationSuiteScope,
            onCloseClick = viewModel::onCloseClick,
            onTagClick = viewModel::onTagClick,
            onIllustrationClick = viewModel::onIllustrationClick,
            loadMoreIllustrations = viewModel::loadMoreIllustrations,
        )
    }
}
