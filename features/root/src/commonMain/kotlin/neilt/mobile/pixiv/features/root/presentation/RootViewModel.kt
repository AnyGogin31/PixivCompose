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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import neilt.mobile.core.navigation.Navigator
import neilt.mobile.pixiv.desingsystem.components.navigation.BottomNavigationItem
import neilt.mobile.pixiv.desingsystem.components.navigation.NavigationItemContent
import neilt.mobile.pixiv.desingsystem.icons.PixivIcons
import neilt.mobile.pixiv.desingsystem.icons.filled.Explore
import neilt.mobile.pixiv.desingsystem.icons.filled.Home
import neilt.mobile.pixiv.desingsystem.icons.filled.Profile
import neilt.mobile.pixiv.desingsystem.icons.outlined.Explore
import neilt.mobile.pixiv.desingsystem.icons.outlined.Home
import neilt.mobile.pixiv.desingsystem.icons.outlined.Profile
import neilt.mobile.pixiv.features.main.presentation.PixivMainSection

class RootViewModel(val navigator: Navigator) : ViewModel() {

    val bottomNavigationItems = listOf(
        BottomNavigationItem(
            destination = PixivMainSection.HomeScreen,
            content = NavigationItemContent(
                label = { "stringResource(R.string.navigation_home)" },
                selectedIcon = PixivIcons.Filled.Home,
                unselectedIcon = PixivIcons.Outlined.Home,
            ),
            onSelect = {
                viewModelScope.launch {
                    navigator.navigateTo(it)
                }
            },
        ),
        BottomNavigationItem(
            destination = PixivMainSection.ExploreScreen,
            content = NavigationItemContent(
                label = { "stringResource(R.string.navigation_explore)" },
                selectedIcon = PixivIcons.Filled.Explore,
                unselectedIcon = PixivIcons.Outlined.Explore,
            ),
            onSelect = {
                viewModelScope.launch {
                    navigator.navigateTo(it)
                }
            },
        ),
        BottomNavigationItem(
            destination = PixivMainSection.ProfileScreen,
            content = NavigationItemContent(
                label = { "stringResource(R.string.navigation_profile)" },
                selectedIcon = PixivIcons.Filled.Profile,
                unselectedIcon = PixivIcons.Outlined.Profile,
            ),
            onSelect = {
                viewModelScope.launch {
                    navigator.navigateTo(it)
                }
            },
        ),
    )
}
