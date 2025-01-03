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

package neilt.mobile.pixiv.shared

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import neilt.mobile.pixiv.core.router.Router
import neilt.mobile.pixiv.desingsystem.PixivTheme
import neilt.mobile.pixiv.desingsystem.components.scaffold.PixivScaffold
import neilt.mobile.pixiv.features.auth.presentation.addPixivAuthSection
import neilt.mobile.pixiv.features.details.presentation.addPixivIllustrationSection
import neilt.mobile.pixiv.features.main.presentation.PixivMainSection
import neilt.mobile.pixiv.features.main.presentation.addPixivMainSection
import neilt.mobile.pixiv.features.search.presentation.addPixivSearchSection
import neilt.mobile.pixiv.features.settings.presentation.addPixivSettingsSection
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun PixivMultiplatformView(
    modifier: Modifier = Modifier,
    router: Router = koinInject(),
    viewModel: PixivMultiplatformViewModel = koinViewModel(),
) {
    PixivTheme {
        PixivScaffold(
            modifier = modifier,
            computeStartDestination = router::computeStartDestination,
            bottomNavigationTargetSection = PixivMainSection,
            bottomNavigationItems = viewModel.bottomNavigationItems,
            onBottomNavigationItemClick = viewModel::navigateWithPopUp,
        ) {
            addPixivAuthSection()
            addPixivMainSection()
            addPixivIllustrationSection()
            addPixivSearchSection()
            addPixivSettingsSection()
        }
    }
}
