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

import neilt.mobile.pixiv.data.remote.common.AuthorizationInterceptor
import neilt.mobile.pixiv.data.remote.common.PixivHeaderInterceptor
import neilt.mobile.pixiv.data.remote.services.auth.AuthService
import neilt.mobile.pixiv.data.remote.services.home.HomeService
import neilt.mobile.pixiv.data.repositories.auth.ActiveUserTokenProvider
import neilt.mobile.pixiv.domain.repositories.auth.TokenProvider
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val OAUTH_BASE_URL = "https://oauth.secure.pixiv.net/"
private const val PIXIV_BASE_URL = "https://app-api.pixiv.net/"

private fun provideRetrofit(baseUrl: String, tokenProvider: TokenProvider? = null): Retrofit {
    val clientBuilder = OkHttpClient.Builder()
    tokenProvider?.let { clientBuilder.addInterceptor(AuthorizationInterceptor(it)) }
    clientBuilder.addInterceptor(PixivHeaderInterceptor())
    clientBuilder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))

    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(clientBuilder.build())
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
}

internal val remoteModule = module {
    single<TokenProvider> { ActiveUserTokenProvider(authRepository = get()) }

    single(named("OAuthApi")) { provideRetrofit(baseUrl = OAUTH_BASE_URL) }
    single(named("PixivApi")) {
        provideRetrofit(
            baseUrl = PIXIV_BASE_URL,
            tokenProvider = get()
        )
    }

    single { get<Retrofit>(named("OAuthApi")).create(AuthService::class.java) }
    single { get<Retrofit>(named("PixivApi")).create(HomeService::class.java) }
}
