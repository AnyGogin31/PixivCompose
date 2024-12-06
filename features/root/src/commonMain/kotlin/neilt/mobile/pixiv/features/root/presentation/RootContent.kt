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

package neilt.mobile.pixiv.features.root.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import neilt.mobile.core.navigation.Navigator
import neilt.mobile.core.navigation.components.NavigationObserver
import neilt.mobile.pixiv.desingsystem.components.navigation.BottomNavigationItem
import neilt.mobile.pixiv.desingsystem.components.navigation.CollapsibleBottomNavigation
import neilt.mobile.pixiv.features.auth.presentation.PixivAuthSection
import neilt.mobile.pixiv.features.auth.presentation.addPixivAuthSection
import neilt.mobile.pixiv.features.illustration.presentation.addPixivIllustrationSection
import neilt.mobile.pixiv.features.main.presentation.PixivMainSection
import neilt.mobile.pixiv.features.main.presentation.addPixivMainSection
import org.koin.androidx.compose.koinViewModel

@Composable
fun RootContent(
    modifier: Modifier = Modifier,
    viewModel: RootViewModel = koinViewModel(),
) {
    PixivScaffold(
        modifier = modifier,
        navigator = viewModel.navigator,
        bottomNavigationItems = viewModel.bottomNavigationItems,
    )
}

@Composable
private fun PixivScaffold(
    modifier: Modifier = Modifier,
    navigator: Navigator,
    bottomNavigationItems: List<BottomNavigationItem>,
) {
    val navController = rememberNavController()

    val currentBackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackEntry?.destination

    Scaffold(
        modifier = modifier.fillMaxSize(),
        content = {
            PixivContent(
                modifier = Modifier.padding(it),
                navController = navController,
                navigator = navigator,
            )
        },
        bottomBar = {
            CollapsibleBottomNavigation(
                items = bottomNavigationItems,
                currentDestination = currentDestination,
                targetSection = PixivMainSection,
            )
        },
    )
}

@Composable
private fun PixivContent(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    navigator: Navigator,
) {
    NavigationObserver(
        navController = navController,
        navigator = navigator,
    )
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = PixivAuthSection,
    ) {
        addPixivAuthSection()
        addPixivMainSection()
        addPixivIllustrationSection()
    }
}
