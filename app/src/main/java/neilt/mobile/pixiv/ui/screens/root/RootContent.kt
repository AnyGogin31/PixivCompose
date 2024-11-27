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

package neilt.mobile.pixiv.ui.screens.root

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import neilt.mobile.pixiv.ui.designsystem.navigation.PixivBottomNavigation
import neilt.mobile.pixiv.ui.screens.auth.LoginScreen
import neilt.mobile.pixiv.ui.screens.home.HomeScreen
import neilt.mobile.pixiv.ui.theme.PixivTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun RootContent(
    viewModel: RootViewModel = koinViewModel(),
) {
    PixivTheme {
        val navController = rememberNavController()

        val currentBackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackEntry?.destination

        LaunchedEffect(Unit) {
            val startDestination = viewModel.determineStartDestination()
            navController.navigate(startDestination) {
                popUpTo("AuthSection") { inclusive = true }
            }
        }

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                PixivBottomNavigation(
                    currentRoute = currentDestination?.route,
                )
            },
        ) { innerPadding ->
            NavHost(
                modifier = Modifier.padding(innerPadding),
                navController = navController,
                startDestination = "AuthSection",
            ) {
                navigation(
                    route = "MainSection",
                    startDestination = "MainSection_HomeScreen",
                ) {
                    composable(route = "MainSection_HomeScreen") {
                        HomeScreen()
                    }
                }

                navigation(
                    route = "AuthSection",
                    startDestination = "AuthSection_LoginScreen",
                ) {
                    composable(route = "AuthSection_LoginScreen") {
                        LoginScreen()
                    }
                }
            }
        }
    }
}
