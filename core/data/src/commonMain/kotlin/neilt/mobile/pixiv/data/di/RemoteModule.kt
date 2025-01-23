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

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import neilt.mobile.pixiv.data.remote.common.addAuthorizationInterceptor
import neilt.mobile.pixiv.data.remote.services.auth.AuthService
import neilt.mobile.pixiv.data.remote.services.details.illustration.IllustrationService
import neilt.mobile.pixiv.data.remote.services.github.GitHubService
import neilt.mobile.pixiv.data.remote.services.home.HomeService
import neilt.mobile.pixiv.data.remote.services.profile.ProfileService
import neilt.mobile.pixiv.data.remote.services.search.SearchService
import neilt.mobile.pixiv.data.repositories.auth.ActiveUserTokenProvider
import neilt.mobile.pixiv.data.sources.auth.AuthRemoteDataSource
import neilt.mobile.pixiv.data.sources.github.GitHubRemoteDataSource
import neilt.mobile.pixiv.data.sources.home.HomeRemoteDataSource
import neilt.mobile.pixiv.data.sources.illustration.IllustrationRemoteDataSource
import neilt.mobile.pixiv.data.sources.profile.ProfileRemoteDataSource
import neilt.mobile.pixiv.data.sources.search.SearchRemoteDataSource
import neilt.mobile.pixiv.domain.repositories.auth.TokenProvider
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

private const val OAUTH_BASE_URL = "https://oauth.secure.pixiv.net/"
private const val PIXIV_BASE_URL = "https://app-api.pixiv.net/"

private fun provideHttpClient(
    baseUrl: String,
    tokenProvider: TokenProvider? = null,
): HttpClient {
    return HttpClient(CIO) {
        install(Logging)
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                },
            )
        }

        defaultRequest {
            url(baseUrl)
            headers.apply {
                addAuthorizationInterceptor(tokenProvider)
            }
        }
    }
}

internal val remoteModule = module {

    singleOf(::AuthRemoteDataSource)
    singleOf(::GitHubRemoteDataSource)
    singleOf(::HomeRemoteDataSource)
    singleOf(::IllustrationRemoteDataSource)
    singleOf(::ProfileRemoteDataSource)
    singleOf(::SearchRemoteDataSource)

    single<TokenProvider> { ActiveUserTokenProvider(authRepository = get()) }

    single(named("OAuthApi")) { provideHttpClient(baseUrl = OAUTH_BASE_URL) }
    single(named("PixivApi")) {
        provideHttpClient(
            baseUrl = PIXIV_BASE_URL,
            tokenProvider = get(),
        )
    }

    single { AuthService(get<HttpClient>(named("OAuthApi"))) }
    singleOf(::GitHubService)
    single { HomeService(get<HttpClient>(named("PixivApi"))) }
    single { SearchService(get<HttpClient>(named("PixivApi"))) }
    single { ProfileService(get<HttpClient>(named("PixivApi"))) }
    single { IllustrationService(get<HttpClient>(named("PixivApi"))) }

    includes(platformRemoteModule)
}

internal expect val platformRemoteModule: Module
