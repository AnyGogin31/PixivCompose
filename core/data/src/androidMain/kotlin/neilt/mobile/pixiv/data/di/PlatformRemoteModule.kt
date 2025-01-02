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

package neilt.mobile.pixiv.data.di

import neilt.mobile.pixiv.data.provider.AndroidUpdateCheckerProvider
import neilt.mobile.pixiv.domain.provider.AndroidBrowserProvider
import neilt.mobile.pixiv.domain.provider.AndroidPKCEProvider
import neilt.mobile.pixiv.domain.provider.BrowserProvider
import neilt.mobile.pixiv.domain.provider.PKCEProvider
import neilt.mobile.pixiv.domain.provider.UpdateCheckerProvider
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

internal actual val platformRemoteModule = module {
    single {
        AndroidBrowserProvider(
            context = getOrNull(named("activity")) ?: get(),
        )
    } bind BrowserProvider::class
    singleOf(::AndroidPKCEProvider) bind PKCEProvider::class
    factoryOf(::AndroidUpdateCheckerProvider) bind UpdateCheckerProvider::class
}
