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

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil3.PlatformContext
import coil3.compose.setSingletonImageLoaderFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import neilt.mobile.pixiv.core.navigation.Destination
import neilt.mobile.pixiv.core.navigation.Navigator
import neilt.mobile.pixiv.core.navigation.extensions.hasDestination
import neilt.mobile.pixiv.core.navigation.observer.NavigationObserver
import neilt.mobile.pixiv.core.router.Router
import neilt.mobile.pixiv.desingsystem.PixivTheme
import neilt.mobile.pixiv.desingsystem.foundation.animation.AsyncContent
import neilt.mobile.pixiv.desingsystem.foundation.suite.NavigationSuiteScaffold
import neilt.mobile.pixiv.desingsystem.provider.provideImageLoader
import neilt.mobile.pixiv.domain.provider.UpdateCheckerProvider
import neilt.mobile.pixiv.domain.repositories.icon.IconRepository
import neilt.mobile.pixiv.features.auth.presentation.addPixivAuthSection
import neilt.mobile.pixiv.features.main.presentation.addPixivMainSection
import neilt.mobile.pixiv.features.settings.presentation.addPixivSettingsSection
import neilt.mobile.pixiv.resources.Res
import neilt.mobile.pixiv.resources.new_version_available
import neilt.mobile.pixiv.shared.navigation.TOP_LEVEL_DESTINATIONS
import neilt.mobile.pixiv.shared.navigation.TopLevelDestination
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

@Composable
internal fun PixivMultiplatformView(
    modifier: Modifier = Modifier,
) {
    setSingletonImageLoaderFactory { context: PlatformContext ->
        provideImageLoader(context)
    }

    val iconRepository: IconRepository = koinInject()
    val updateCheckerProvider: UpdateCheckerProvider = koinInject()

    LaunchedEffect(key1 = Unit) {
        iconRepository.updateIconIfNeed()
        updateCheckerProvider.checkAppUpdates(getString(Res.string.new_version_available))
    }

    val router: Router = koinInject()

    PixivTheme {
        PixivScaffoldAdaptive(
            computeStartDestination = router::computeStartDestination,
            modifier = modifier,
        ) {
            addPixivAuthSection()
            addPixivMainSection()
            addPixivSettingsSection()
        }
    }
}

@Composable
private fun PixivScaffoldAdaptive(
    computeStartDestination: suspend () -> Destination,
    modifier: Modifier = Modifier,
    builder: NavGraphBuilder.() -> Unit,
) {
    val navController: NavHostController = rememberNavController()

    val currentBackEntry by navController.currentBackStackEntryAsState()
    val currentDestination: NavDestination? = currentBackEntry?.destination

    val navigator: Navigator = koinInject()

    val coroutineScope: CoroutineScope = rememberCoroutineScope()

    @Composable
    fun TopLevelDestination.renderLabel() {
        Text(
            text = stringResource(label),
        )
    }

    @Composable
    fun TopLevelDestination.renderIcon(isSelected: Boolean) {
        Crossfade(targetState = isSelected) { isSelectedState ->
            val icon = if (isSelectedState) selectedIcon else unselectedIcon
            Icon(
                imageVector = icon,
                contentDescription = stringResource(label),
            )
        }
    }

    @Composable
    fun renderNavigationItems(
        navigationItem: @Composable (item: TopLevelDestination, isSelected: Boolean, onClick: () -> Unit) -> Unit,
    ) {
        PixivNavigationItemContainer(
            isItemSelected = currentDestination::hasDestination,
            onItemSelected = {
                coroutineScope.launch {
                    navigator.navigateTo(it) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
            navigationItem = navigationItem,
        )
    }

    NavigationSuiteScaffold(
        navigationBar = {
            BottomAppBar(
                modifier = modifier.fillMaxWidth(),
            ) {
                renderNavigationItems { item, isSelected, onClick ->
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = onClick,
                        icon = {
                            item.renderIcon(isSelected)
                        },
                        label = {
                            item.renderLabel()
                        },
                    )
                }
            }
        },
        navigationRail = {
            NavigationRail(
                modifier = modifier.fillMaxHeight(),
            ) {
                renderNavigationItems { item, isSelected, onClick ->
                    NavigationRailItem(
                        selected = isSelected,
                        onClick = onClick,
                        icon = {
                            item.renderIcon(isSelected)
                        },
                        label = {
                            item.renderLabel()
                        },
                    )
                }
            }
        },
        navigationDrawer = {
            PermanentDrawerSheet(
                modifier = modifier.sizeIn(minWidth = 200.dp, maxWidth = 300.dp),
            ) {
                renderNavigationItems { item, isSelected, onClick ->
                    NavigationDrawerItem(
                        selected = isSelected,
                        onClick = onClick,
                        icon = {
                            item.renderIcon(isSelected)
                        },
                        label = {
                            item.renderLabel()
                        },
                    )
                }
            }
        },
        modifier = modifier,
        content = {
            NavigationObserver(
                navController = navController,
            )
            AsyncContent(
                loadData = computeStartDestination,
            ) { startDestination ->
                NavHost(
                    modifier = modifier,
                    navController = navController,
                    startDestination = startDestination,
                    builder = builder,
                )
            }
        },
    )
}

@Composable
private fun PixivNavigationItemContainer(
    isItemSelected: (destination: Destination) -> Boolean,
    onItemSelected: (destination: Destination) -> Unit,
    navigationItem: @Composable (item: TopLevelDestination, isSelected: Boolean, onClick: () -> Unit) -> Unit,
) {
    TOP_LEVEL_DESTINATIONS.forEach { item ->
        val isSelected = isItemSelected(item.destination)
        navigationItem(item, isSelected) { if (!isSelected) onItemSelected(item.destination) }
    }
}
