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

import neilt.mobile.pixiv.data.repositories.auth.AuthRepositoryImpl
import neilt.mobile.pixiv.data.repositories.details.illustration.IllustrationRepositoryImpl
import neilt.mobile.pixiv.data.repositories.home.HomeRepositoryImpl
import neilt.mobile.pixiv.data.repositories.profile.ProfileRepositoryImpl
import neilt.mobile.pixiv.data.repositories.search.SearchRepositoryImpl
import neilt.mobile.pixiv.domain.repositories.auth.AuthRepository
import neilt.mobile.pixiv.domain.repositories.details.illustration.IllustrationRepository
import neilt.mobile.pixiv.domain.repositories.home.HomeRepository
import neilt.mobile.pixiv.domain.repositories.profile.ProfileRepository
import neilt.mobile.pixiv.domain.repositories.search.SearchRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    includes(localModule, remoteModule)

    singleOf(::AuthRepositoryImpl) bind AuthRepository::class
    singleOf(::HomeRepositoryImpl) bind HomeRepository::class
    singleOf(::SearchRepositoryImpl) bind SearchRepository::class
    singleOf(::ProfileRepositoryImpl) bind ProfileRepository::class
    singleOf(::IllustrationRepositoryImpl) bind IllustrationRepository::class
}
