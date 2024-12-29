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

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import neilt.mobile.core.navigation.Destination
import neilt.mobile.core.navigation.NavOptions
import neilt.mobile.core.navigation.Navigator
import neilt.mobile.pixiv.desingsystem.components.navigation.BottomNavigationItem
import neilt.mobile.pixiv.desingsystem.components.navigation.NavigationActionButton
import neilt.mobile.pixiv.desingsystem.icons.PixivIcons
import neilt.mobile.pixiv.desingsystem.icons.filled.Home
import neilt.mobile.pixiv.desingsystem.icons.filled.MenuBook
import neilt.mobile.pixiv.desingsystem.icons.filled.Profile
import neilt.mobile.pixiv.desingsystem.icons.outlined.Home
import neilt.mobile.pixiv.desingsystem.icons.outlined.MenuBook
import neilt.mobile.pixiv.desingsystem.icons.outlined.Profile
import neilt.mobile.pixiv.domain.repositories.auth.AuthRepository
import neilt.mobile.pixiv.features.auth.presentation.PixivAuthSection
import neilt.mobile.pixiv.features.main.presentation.PixivMainSection
import neilt.mobile.pixiv.features.search.PixivSearchSection
import neilt.mobile.pixiv.features.settings.presentation.PixivSettingsSection
import neilt.mobile.pixiv.resources.Res
import neilt.mobile.pixiv.resources.navigation_home
import neilt.mobile.pixiv.resources.navigation_manga
import neilt.mobile.pixiv.resources.navigation_profile

internal class PixivMultiplatformViewModel(
    private val authRepository: AuthRepository,
    private val navigator: Navigator,
) : ViewModel() {
    val bottomNavigationItems = listOf(
        BottomNavigationItem(
            destination = PixivMainSection.HomeScreen,
            label = Res.string.navigation_home,
            selectedIcon = PixivIcons.Filled.Home,
            unselectedIcon = PixivIcons.Outlined.Home,
            actionButton = NavigationActionButton(
                icon = Icons.Default.Search,
                onClick = {
                    navigateTo(
                        PixivSearchSection.ExploreScreen(
                            exploreType = 0,
                            query = "",
                        ),
                    )
                },
            ),
        ),
        BottomNavigationItem(
            destination = PixivMainSection.MangaScreen,
            label = Res.string.navigation_manga,
            selectedIcon = PixivIcons.Filled.MenuBook,
            unselectedIcon = PixivIcons.Outlined.MenuBook,
            actionButton = NavigationActionButton(
                icon = Icons.Default.Search,
                onClick = {
                    navigateTo(
                        PixivSearchSection.ExploreScreen(
                            exploreType = 1,
                            query = "",
                        ),
                    )
                },
            ),
        ),
        BottomNavigationItem(
            destination = PixivMainSection.ProfileScreen,
            label = Res.string.navigation_profile,
            selectedIcon = PixivIcons.Filled.Profile,
            unselectedIcon = PixivIcons.Outlined.Profile,
            actionButton = NavigationActionButton(
                icon = Icons.Default.Settings,
                onClick = {
                    navigateTo(PixivSettingsSection)
                },
            ),
        ),
    )

    suspend fun computeStartDestination(): Destination {
        return withContext(Dispatchers.IO) {
            val activeUser = authRepository.getActiveUser()
            if (activeUser != null) PixivMainSection else PixivAuthSection
        }
    }

    fun navigateWithPopUp(
        startDestinationId: Int,
        destination: Destination,
    ) {
        navigateTo(destination) {
            popUpTo(startDestinationId) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    private fun navigateTo(
        destination: Destination,
        navOptions: NavOptions = {},
    ) {
        viewModelScope.launch {
            navigator.navigateTo(destination, navOptions)
        }
    }
}
