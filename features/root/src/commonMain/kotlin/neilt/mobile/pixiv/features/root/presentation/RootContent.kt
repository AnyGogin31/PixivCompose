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

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import neilt.mobile.core.navigation.NavigationAction
import neilt.mobile.core.navigation.Navigator
import neilt.mobile.core.navigation.extensions.hasDestination
import neilt.mobile.pixiv.desingsystem.components.navigation.BottomNavigationBar
import neilt.mobile.pixiv.desingsystem.components.navigation.BottomNavigationItem
import neilt.mobile.pixiv.desingsystem.components.utils.ObserveAsEvents
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
        bottomNavigationItems = viewModel.bottomNavigationItems
    )
}

@Composable
private fun PixivScaffold(
    modifier: Modifier = Modifier,
    navigator: Navigator,
    bottomNavigationItems: List<BottomNavigationItem>
) {
    val navController = rememberNavController()
    Scaffold(
        modifier = modifier.fillMaxSize(),
        content = {
            PixivContent(
                modifier = Modifier.padding(it),
                navController = navController,
                navigator = navigator
            )
        },
        bottomBar = {
            PixivBottomNavigation(
                items = bottomNavigationItems,
                currentDestination = navController.currentDestination,
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
    ObserveAsEvents(flow = navigator.navigationActions) { action ->
        when (action) {
            is NavigationAction.NavigateTo -> {
                navController.navigate(action.destination) {
                    action.navOptions(this)
                }
            }

            is NavigationAction.NavigateUp -> navController.navigateUp()
        }
    }
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = PixivMainSection,
    ) {
        addPixivMainSection()
    }
}

@Composable
private fun PixivBottomNavigation(
    items: List<BottomNavigationItem>,
    currentDestination: NavDestination? = null,
) {
    AnimatedVisibility(
        visible = currentDestination.hasDestination<PixivMainSection>(),
        enter = fadeIn() + slideInVertically { it },
        exit = fadeOut() + slideOutVertically { it },
    ) {
        currentDestination?.let {
            BottomNavigationBar(
                items = items,
                currentDestination = it,
            )
        }
    }
}
