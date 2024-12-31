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

package neilt.mobile.pixiv.shared.di

import neilt.mobile.pixiv.core.navigation.di.navigationModule
import neilt.mobile.pixiv.core.router.di.routerModule
import neilt.mobile.pixiv.data.di.repositoryModule
import neilt.mobile.pixiv.desingsystem.di.designSystemModule
import neilt.mobile.pixiv.features.auth.di.authFeatureModule
import neilt.mobile.pixiv.features.details.di.detailsFeatureModule
import neilt.mobile.pixiv.features.main.di.mainFeatureModule
import neilt.mobile.pixiv.features.search.di.searchFeatureModule
import neilt.mobile.pixiv.features.settings.di.settingsFeatureModule

val pixivModules = listOf(
    designSystemModule,
    routerModule,
    repositoryModule,
    authFeatureModule,
    mainFeatureModule,
    detailsFeatureModule,
    searchFeatureModule,
    settingsFeatureModule,
    navigationModule,
    sharedModule,
)
