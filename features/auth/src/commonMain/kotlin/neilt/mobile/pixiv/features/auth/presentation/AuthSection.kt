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

package neilt.mobile.pixiv.features.auth.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import neilt.mobile.pixiv.core.navigation.Destination
import neilt.mobile.pixiv.features.auth.presentation.auth.AuthView
import neilt.mobile.pixiv.features.auth.presentation.login.LoginView

@Serializable
data object PixivAuthSection : Destination {
    @Serializable
    data object LoginScreen : Destination

    @Serializable
    internal data class AuthScreen(val code: String) : Destination
}

fun NavGraphBuilder.addPixivAuthSection() {
    navigation<PixivAuthSection>(
        startDestination = PixivAuthSection.LoginScreen,
    ) {
        composable<PixivAuthSection.LoginScreen> { LoginView() }
        composable<PixivAuthSection.AuthScreen>(
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "pixiv://account/login?code={code}"
                },
            ),
        ) {
            AuthView(codeAuthorization = it.toRoute<PixivAuthSection.AuthScreen>().code)
        }
    }
}
