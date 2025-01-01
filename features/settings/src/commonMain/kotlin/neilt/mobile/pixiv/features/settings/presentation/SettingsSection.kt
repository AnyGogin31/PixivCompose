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

package neilt.mobile.pixiv.features.settings.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import kotlinx.serialization.Serializable
import neilt.mobile.pixiv.core.navigation.Destination
import neilt.mobile.pixiv.features.settings.presentation.about.AboutView
import neilt.mobile.pixiv.features.settings.presentation.analytics.AnalyticsView
import neilt.mobile.pixiv.features.settings.presentation.appearance.AppearanceView
import neilt.mobile.pixiv.features.settings.presentation.backup.BackupView
import neilt.mobile.pixiv.features.settings.presentation.behavior.BehaviorView
import neilt.mobile.pixiv.features.settings.presentation.cache.CacheView
import neilt.mobile.pixiv.features.settings.presentation.overview.SettingsOverviewView
import neilt.mobile.pixiv.features.settings.presentation.updates.UpdatesView

@Serializable
data object PixivSettingsSection : Destination {
    @Serializable
    data object AboutScreen : Destination

    @Serializable
    data object AppearanceScreen : Destination

    @Serializable
    data object AnalyticsScreen : Destination

    @Serializable
    data object BackupScreen : Destination

    @Serializable
    data object BehaviorScreen : Destination

    @Serializable
    data object CacheScreen : Destination

    @Serializable
    data object OverviewScreen : Destination

    @Serializable
    data object UpdatesScreen : Destination
}

fun NavGraphBuilder.addPixivSettingsSection() {
    navigation<PixivSettingsSection>(
        startDestination = PixivSettingsSection.OverviewScreen,
    ) {
        composable<PixivSettingsSection.AboutScreen> { AboutView() }
        composable<PixivSettingsSection.AppearanceScreen> { AppearanceView() }
        composable<PixivSettingsSection.AnalyticsScreen> { AnalyticsView() }
        composable<PixivSettingsSection.BackupScreen> { BackupView() }
        composable<PixivSettingsSection.BehaviorScreen> { BehaviorView() }
        composable<PixivSettingsSection.CacheScreen> { CacheView() }
        composable<PixivSettingsSection.OverviewScreen> { SettingsOverviewView() }
        composable<PixivSettingsSection.UpdatesScreen> { UpdatesView() }
    }
}
