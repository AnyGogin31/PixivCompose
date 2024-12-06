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

package neilt.mobile.pixiv.desingsystem.di

import android.content.Context
import coil3.ImageLoader
import coil3.intercept.Interceptor
import coil3.map.Mapper
import coil3.network.NetworkHeaders
import coil3.network.httpHeaders
import coil3.request.CachePolicy
import coil3.request.crossfade
import neilt.mobile.pixiv.domain.models.home.ImageUrls
import neilt.mobile.pixiv.domain.models.profile.ProfileImageUrls
import org.koin.core.module.Module
import org.koin.dsl.module

private fun provideImageLoader(context: Context): ImageLoader {
    return ImageLoader.Builder(context)
        .crossfade(true)
        .components {
            add(Mapper<ImageUrls, String> { data, _ -> data.mediumUrl })
            add(Mapper<ProfileImageUrls, String> { data, _ -> data.medium })
            add(
                Interceptor {
                    it.withRequest(
                        it.request.newBuilder()
                            .httpHeaders(
                                NetworkHeaders.Builder()
                                    .set("referer", "https://app-api.pixiv.net/")
                                    .build(),
                            )
                            .build(),
                    ).proceed()
                },
            )
        }
        .diskCachePolicy(CachePolicy.ENABLED)
        .build()
}

val designSystemModule = module {

    single { provideImageLoader(context = get()) }

    includes(platformDesignSystemModule)
}

internal expect val platformDesignSystemModule: Module
