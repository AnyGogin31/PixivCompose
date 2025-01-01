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

package neilt.mobile.pixiv.features.settings.presentation.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import neilt.mobile.pixiv.core.navigation.Destination
import neilt.mobile.pixiv.core.navigation.Navigator
import neilt.mobile.pixiv.desingsystem.components.settings.SettingGroup
import neilt.mobile.pixiv.desingsystem.components.settings.SettingItem
import neilt.mobile.pixiv.desingsystem.icons.PixivIcons
import neilt.mobile.pixiv.desingsystem.icons.filled.Analytics
import neilt.mobile.pixiv.desingsystem.icons.filled.CloudUpload
import neilt.mobile.pixiv.desingsystem.icons.filled.DeleteSweep
import neilt.mobile.pixiv.desingsystem.icons.filled.Info
import neilt.mobile.pixiv.desingsystem.icons.filled.ManageAccounts
import neilt.mobile.pixiv.desingsystem.icons.filled.Palette
import neilt.mobile.pixiv.desingsystem.icons.filled.Tune
import neilt.mobile.pixiv.desingsystem.icons.filled.Update
import neilt.mobile.pixiv.domain.provider.BrowserProvider
import neilt.mobile.pixiv.features.settings.presentation.PixivSettingsSection
import neilt.mobile.pixiv.resources.Res
import neilt.mobile.pixiv.resources.settings_about_subtitle
import neilt.mobile.pixiv.resources.settings_about_title
import neilt.mobile.pixiv.resources.settings_account_subtitle
import neilt.mobile.pixiv.resources.settings_account_title
import neilt.mobile.pixiv.resources.settings_analytics_subtitle
import neilt.mobile.pixiv.resources.settings_analytics_title
import neilt.mobile.pixiv.resources.settings_appearance_subtitle
import neilt.mobile.pixiv.resources.settings_appearance_title
import neilt.mobile.pixiv.resources.settings_backup_subtitle
import neilt.mobile.pixiv.resources.settings_backup_title
import neilt.mobile.pixiv.resources.settings_behavior_subtitle
import neilt.mobile.pixiv.resources.settings_behavior_title
import neilt.mobile.pixiv.resources.settings_cache_subtitle
import neilt.mobile.pixiv.resources.settings_cache_title
import neilt.mobile.pixiv.resources.settings_updates_subtitle
import neilt.mobile.pixiv.resources.settings_updates_title

internal class OverviewViewModel(
    private val browserProvider: BrowserProvider,
    private val navigator: Navigator,
) : ViewModel() {
    val settingsElements = listOf(
        SettingItem(
            title = Res.string.settings_account_title,
            subtitle = Res.string.settings_account_subtitle,
            icon = PixivIcons.Filled.ManageAccounts,
            onClick = {
                viewModelScope.launch {
                    browserProvider.openChromeCustomTabs("https://pixiv.net/settings/account")
                }
            },
        ),
        SettingGroup(
            items = listOf(
                SettingItem(
                    title = Res.string.settings_appearance_title,
                    subtitle = Res.string.settings_appearance_subtitle,
                    icon = PixivIcons.Filled.Palette,
                    onClick = {
                        navigateTo(PixivSettingsSection.AppearanceScreen)
                    },
                    isEnable = false,
                ),
                SettingItem(
                    title = Res.string.settings_behavior_title,
                    subtitle = Res.string.settings_behavior_subtitle,
                    icon = PixivIcons.Filled.Tune,
                    onClick = {
                        navigateTo(PixivSettingsSection.BehaviorScreen)
                    },
                    isEnable = false,
                ),
            ),
        ),
        SettingGroup(
            items = listOf(
                SettingItem(
                    title = Res.string.settings_cache_title,
                    subtitle = Res.string.settings_cache_subtitle,
                    icon = PixivIcons.Filled.DeleteSweep,
                    onClick = {
                        navigateTo(PixivSettingsSection.CacheScreen)
                    },
                ),
                SettingItem(
                    title = Res.string.settings_backup_title,
                    subtitle = Res.string.settings_backup_subtitle,
                    icon = PixivIcons.Filled.CloudUpload,
                    onClick = {
                        navigateTo(PixivSettingsSection.BackupScreen)
                    },
                    isEnable = false,
                ),
                SettingItem(
                    title = Res.string.settings_analytics_title,
                    subtitle = Res.string.settings_analytics_subtitle,
                    icon = PixivIcons.Filled.Analytics,
                    onClick = {
                        navigateTo(PixivSettingsSection.AnalyticsScreen)
                    },
                    isEnable = false,
                ),
            ),
        ),
        SettingGroup(
            items = listOf(
                SettingItem(
                    title = Res.string.settings_updates_title,
                    subtitle = Res.string.settings_updates_subtitle,
                    icon = PixivIcons.Filled.Update,
                    onClick = {
                        navigateTo(PixivSettingsSection.UpdatesScreen)
                    },
                ),
                SettingItem(
                    title = Res.string.settings_about_title,
                    subtitle = Res.string.settings_about_subtitle,
                    icon = PixivIcons.Filled.Info,
                    onClick = {
                        navigateTo(PixivSettingsSection.AboutScreen)
                    },
                    isEnable = false,
                ),
            ),
        ),
    )

    private fun navigateTo(destination: Destination) {
        viewModelScope.launch {
            navigator.navigateTo(destination)
        }
    }
}
