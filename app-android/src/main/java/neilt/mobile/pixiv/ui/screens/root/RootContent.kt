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

package neilt.mobile.pixiv.ui.screens.root

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import neilt.mobile.core.navigation.components.NavigationObserver
import neilt.mobile.pixiv.desingsystem.PixivTheme
import neilt.mobile.pixiv.ui.components.navigation.PixivBottomNavigation
import neilt.mobile.pixiv.ui.navigation.PixivDestination
import neilt.mobile.pixiv.ui.screens.auth.LoginScreen
import neilt.mobile.pixiv.ui.screens.details.illustration.IllustrationDetailsScreen
import neilt.mobile.pixiv.ui.screens.explore.ExploreScreen
import neilt.mobile.pixiv.ui.screens.home.HomeScreen
import neilt.mobile.pixiv.ui.screens.profile.ProfileScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun RootContent(
    startDestination: PixivDestination,
    viewModel: RootViewModel = koinViewModel(),
) {
    PixivTheme {
        val navController = rememberNavController()
        val navigator = viewModel.navigator

        val currentBackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackEntry?.destination

        LaunchedEffect(startDestination) {
            navigator.navigateTo(startDestination)
        }

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                PixivBottomNavigation(
                    items = viewModel.bottomNavigationItems,
                    currentDestination = currentDestination,
                )
            },
        ) { innerPadding ->
            NavigationObserver(navController, navigator)
            NavHost(
                modifier = Modifier.padding(innerPadding),
                navController = navController,
                startDestination = navigator.startDestination,
            ) {
                navigation<PixivDestination.MainSection>(
                    startDestination = PixivDestination.MainSection.HomeScreen,
                ) {
                    composable<PixivDestination.MainSection.HomeScreen> { HomeScreen() }
                    composable<PixivDestination.MainSection.ExploreScreen> { ExploreScreen() }
                    composable<PixivDestination.MainSection.ProfileScreen> { ProfileScreen() }
                }

                navigation<PixivDestination.IllustrationSection>(
                    startDestination = PixivDestination.IllustrationSection.IllustrationDetailsScreen::class,
                ) {
                    composable<PixivDestination.IllustrationSection.IllustrationDetailsScreen> {
                        val args = it.toRoute<PixivDestination.IllustrationSection.IllustrationDetailsScreen>()
                        IllustrationDetailsScreen(
                            illustrationId = args.illustrationId,
                        )
                    }
                }

                navigation<PixivDestination.AuthSection>(
                    startDestination = PixivDestination.AuthSection.LoginScreen,
                ) {
                    composable<PixivDestination.AuthSection.LoginScreen> { LoginScreen() }
                }
            }
        }
    }
}
