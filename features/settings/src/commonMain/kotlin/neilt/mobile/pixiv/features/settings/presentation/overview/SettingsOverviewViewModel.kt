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

internal class SettingsOverviewViewModel : ViewModel() {
    val settingsElements = listOf(
        SettingItem(
            title = "Account",
            subtitle = "Manage your account details",
            icon = PixivIcons.Filled.ManageAccounts,
            onClick = { /* Open browser with account settings page */ },
        ),
        SettingGroup(
            items = listOf(
                SettingItem(
                    title = "Appearance",
                    subtitle = "Customize app theme and layout",
                    icon = PixivIcons.Filled.Palette,
                    onClick = { /* Open appearance settings */ },
                ),
                SettingItem(
                    title = "Behavior",
                    subtitle = "Adjust app interaction settings",
                    icon = PixivIcons.Filled.Tune,
                    onClick = { /* Open behavior settings */ },
                ),
            ),
        ),
        SettingGroup(
            items = listOf(
                SettingItem(
                    title = "Cache",
                    subtitle = "Clear or manage stored data",
                    icon = PixivIcons.Filled.DeleteSweep,
                    onClick = { /* Open cache settings */ },
                ),
                SettingItem(
                    title = "Backup",
                    subtitle = "Backup and restore your data",
                    icon = PixivIcons.Filled.CloudUpload,
                    onClick = { /* Open backup settings */ },
                ),
                SettingItem(
                    title = "Analytics",
                    subtitle = "View and manage usage data",
                    icon = PixivIcons.Filled.Analytics,
                    onClick = { /* Open analytics settings */ },
                ),
            ),
        ),
        SettingGroup(
            items = listOf(
                SettingItem(
                    title = "Updates",
                    subtitle = "Check for app updates",
                    icon = PixivIcons.Filled.Update,
                    onClick = { /* Open update settings */ },
                ),
                SettingItem(
                    title = "About",
                    subtitle = "App version and developer info",
                    icon = PixivIcons.Filled.Info,
                    onClick = { /* Open about section */ },
                ),
            ),
        ),
    )
}
