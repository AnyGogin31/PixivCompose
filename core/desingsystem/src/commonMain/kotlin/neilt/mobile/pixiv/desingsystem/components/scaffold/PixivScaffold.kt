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

package neilt.mobile.pixiv.desingsystem.components.scaffold

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import neilt.mobile.core.navigation.Destination
import neilt.mobile.core.navigation.Navigator
import neilt.mobile.core.navigation.components.NavigationObserver
import neilt.mobile.core.navigation.extensions.hasDestination
import neilt.mobile.pixiv.desingsystem.components.animation.VerticalSlideVisibility
import neilt.mobile.pixiv.desingsystem.components.navigation.AsyncNavHost
import neilt.mobile.pixiv.desingsystem.components.navigation.BottomNavigationBar
import neilt.mobile.pixiv.desingsystem.components.navigation.BottomNavigationItem
import org.koin.compose.koinInject

@Composable
fun PixivScaffold(
    modifier: Modifier = Modifier,
    fetchStartDestination: suspend () -> Destination,
    bottomNavigationTargetSection: Destination,
    bottomNavigationItems: List<BottomNavigationItem>,
    onBottomNavigationItemClick: (startDestinationId: Int, destination: Destination) -> Unit,
    builder: NavGraphBuilder.() -> Unit,
) {
    val navController = rememberNavController()

    val currentBackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackEntry?.destination

    Scaffold(
        modifier = modifier,
        content = { innerPadding: PaddingValues ->
            PixivScaffoldContent(
                modifier = Modifier.padding(innerPadding),
                navController = navController,
                fetchStartDestination = fetchStartDestination,
                builder = builder,
            )
        },
        bottomBar = {
            PixivScaffoldBottomBar(
                targetSection = bottomNavigationTargetSection,
                currentDestination = currentDestination,
                bottomNavigationItems = bottomNavigationItems,
                onItemSelected = {
                    onBottomNavigationItemClick(navController.graph.startDestinationId, it)
                },
            )
        },
        floatingActionButton = {
            PixivFloatingActionButton(
                items = bottomNavigationItems,
                currentDestination = currentDestination,
            )
        },
    )
}

@Composable
private fun PixivScaffoldContent(
    navController: NavHostController,
    fetchStartDestination: suspend () -> Destination,
    modifier: Modifier = Modifier,
    navigator: Navigator = koinInject(),
    builder: NavGraphBuilder.() -> Unit,
) {
    NavigationObserver(
        navController = navController,
        navigator = navigator,
    )
    AsyncNavHost(
        modifier = modifier,
        navController = navController,
        fetchStartDestination = fetchStartDestination,
        builder = builder,
    )
}

@Composable
private fun PixivScaffoldBottomBar(
    targetSection: Destination,
    currentDestination: NavDestination?,
    bottomNavigationItems: List<BottomNavigationItem>,
    onItemSelected: (currentDestination: Destination) -> Unit,
    modifier: Modifier = Modifier,
) {
    VerticalSlideVisibility(
        visible = currentDestination.hasDestination(targetSection),
    ) {
        BottomNavigationBar(
            modifier = modifier,
            items = bottomNavigationItems,
            isSelectedItem = currentDestination::hasDestination,
            onItemSelected = onItemSelected,
        )
    }
}

@Composable
private fun PixivFloatingActionButton(
    modifier: Modifier = Modifier,
    items: List<BottomNavigationItem>,
    currentDestination: NavDestination? = null,
) {
    val selectedItem = items.firstOrNull { currentDestination.hasDestination(it.destination) }
    selectedItem?.actionButton?.let { actionButton ->
        FloatingActionButton(
            modifier = modifier,
            onClick = actionButton.onClick,
        ) {
            Icon(
                imageVector = actionButton.icon,
                contentDescription = null,
            )
        }
    }
}