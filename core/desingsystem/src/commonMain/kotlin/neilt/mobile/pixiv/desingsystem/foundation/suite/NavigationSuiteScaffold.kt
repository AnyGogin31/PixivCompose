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

package neilt.mobile.pixiv.desingsystem.foundation.suite

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerDefaults
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationRailDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldLayout
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.window.core.layout.WindowHeightSizeClass
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass

@Composable
fun NavigationSuiteScaffold(
    navigationBar: @Composable () -> Unit,
    navigationRail: @Composable (contentPosition: NavigationContentPosition) -> Unit,
    navigationDrawer: @Composable (contentPosition: NavigationContentPosition) -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = NavigationSuiteScaffoldDefaults.containerColor,
    contentColor: Color = NavigationSuiteScaffoldDefaults.contentColor,
    content: @Composable () -> Unit,
) {
    val adaptiveInfo = currentWindowAdaptiveInfo()

    val layoutType = when {
        adaptiveInfo.windowPosture.isTabletop -> NavigationSuiteType.NavigationBar
        adaptiveInfo.windowSizeClass.isCompact -> NavigationSuiteType.NavigationBar
        adaptiveInfo.windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.EXPANDED -> NavigationSuiteType.NavigationDrawer

        else -> NavigationSuiteType.NavigationRail
    }

    val contentPosition = when (adaptiveInfo.windowSizeClass.windowHeightSizeClass) {
        WindowHeightSizeClass.COMPACT -> NavigationContentPosition.TOP
        WindowHeightSizeClass.MEDIUM,
        WindowHeightSizeClass.EXPANDED,
        -> NavigationContentPosition.CENTER

        else -> NavigationContentPosition.TOP
    }

    val contentType = when (adaptiveInfo.windowSizeClass.windowWidthSizeClass) {
        WindowWidthSizeClass.COMPACT -> NavigationContentType.SINGLE_PANE
        WindowWidthSizeClass.MEDIUM -> NavigationContentType.SINGLE_PANE
        WindowWidthSizeClass.EXPANDED -> NavigationContentType.DUAL_PANE

        else -> NavigationContentType.SINGLE_PANE
    }
    val contentScope = NavigationSuiteScopeImpl(contentType, layoutType)

    Surface(
        modifier = modifier.fillMaxSize(),
        color = containerColor,
        contentColor = contentColor,
    ) {
        NavigationSuiteScaffoldLayout(
            navigationSuite = {
                when (layoutType) {
                    NavigationSuiteType.NavigationBar -> navigationBar()
                    NavigationSuiteType.NavigationRail -> navigationRail(contentPosition)
                    NavigationSuiteType.NavigationDrawer -> navigationDrawer(contentPosition)
                }
            },
            layoutType = layoutType,
            content = {
                CompositionLocalProvider(
                    LocalNavigationSuiteScope provides contentScope,
                ) {
                    Box(
                        Modifier.consumeWindowInsets(
                            when (layoutType) {
                                NavigationSuiteType.NavigationBar -> NavigationBarDefaults.windowInsets
                                NavigationSuiteType.NavigationRail -> NavigationRailDefaults.windowInsets
                                NavigationSuiteType.NavigationDrawer -> DrawerDefaults.windowInsets
                                else -> WindowInsets(0, 0, 0, 0)
                            },
                        ),
                    ) {
                        content()
                    }
                }
            },
        )
    }
}

private val WindowSizeClass.isCompact: Boolean
    get() = windowWidthSizeClass == WindowWidthSizeClass.COMPACT ||
        windowHeightSizeClass == WindowHeightSizeClass.COMPACT

private class NavigationSuiteScopeImpl(
    override val contentType: NavigationContentType,
    override val layoutType: NavigationSuiteType,
) : NavigationSuiteScope
