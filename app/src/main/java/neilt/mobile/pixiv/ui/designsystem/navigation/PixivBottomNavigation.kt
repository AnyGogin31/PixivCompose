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

package neilt.mobile.pixiv.ui.designsystem.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import neilt.mobile.pixiv.R
import neilt.mobile.pixiv.ui.components.navigation.BottomNavigationBar
import neilt.mobile.pixiv.ui.components.navigation.BottomNavigationItem
import neilt.mobile.pixiv.ui.components.navigation.NavigationItemContent
import neilt.mobile.pixiv.ui.icons.PixivIcons
import neilt.mobile.pixiv.ui.icons.filled.Explore
import neilt.mobile.pixiv.ui.icons.filled.Home
import neilt.mobile.pixiv.ui.icons.filled.Profile
import neilt.mobile.pixiv.ui.icons.outlined.Explore
import neilt.mobile.pixiv.ui.icons.outlined.Home
import neilt.mobile.pixiv.ui.icons.outlined.Profile

private val bottomNavigationItems = listOf(
    BottomNavigationItem(
        route = "MainSection_HomeScreen",
        content = NavigationItemContent(
            label = { stringResource(R.string.navigation_home) },
            selectedIcon = PixivIcons.Filled.Home,
            unselectedIcon = PixivIcons.Outlined.Home,
        ),
    ),
    BottomNavigationItem(
        route = "MainSection_ExploreScreen",
        content = NavigationItemContent(
            label = { stringResource(R.string.navigation_explore) },
            selectedIcon = PixivIcons.Filled.Explore,
            unselectedIcon = PixivIcons.Outlined.Explore,
        ),
    ),
    BottomNavigationItem(
        route = "MainSection_ProfileScreen",
        content = NavigationItemContent(
            label = { stringResource(R.string.navigation_profile) },
            selectedIcon = PixivIcons.Filled.Profile,
            unselectedIcon = PixivIcons.Outlined.Profile,
        ),
    ),
)

@Composable
fun PixivBottomNavigation(
    items: List<BottomNavigationItem> = bottomNavigationItems,
    currentRoute: String? = null,
) {
    AnimatedVisibility(
        visible = currentRoute?.startsWith("MainSection") == true,
        enter = fadeIn() + slideInVertically { it },
        exit = fadeOut() + slideOutVertically { it },
    ) {
        BottomNavigationBar(
            items = items,
            currentRoute = currentRoute.orEmpty(),
        )
    }
}