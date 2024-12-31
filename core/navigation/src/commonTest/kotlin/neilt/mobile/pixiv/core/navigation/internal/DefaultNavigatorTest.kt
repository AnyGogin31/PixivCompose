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

package neilt.mobile.pixiv.core.navigation.internal

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import neilt.mobile.pixiv.core.navigation.Destination
import neilt.mobile.pixiv.core.navigation.NavOptions
import neilt.mobile.pixiv.core.navigation.NavigationAction
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class DefaultNavigatorTest {
    object AnotherDestination : Destination

    @Test
    fun `navigateTo emits correct navigation action`() = runTest {
        val navigator = DefaultNavigator()
        val navOptions: NavOptions = { /* options */ }

        navigator.navigateTo(AnotherDestination, navOptions)

        val action = navigator.navigationActions.first()
        assertTrue(action is NavigationAction.NavigateTo, "Expected NavigateTo action")
        assertEquals(AnotherDestination, action.destination)
        assertEquals(navOptions, action.navOptions)
    }

    @Test
    fun `navigateUp emits NavigateUp action`() = runTest {
        val navigator = DefaultNavigator()

        navigator.navigateUp()

        val action = navigator.navigationActions.first()
        assertEquals(NavigationAction.NavigateUp, action, "Expected NavigateUp action")
    }

    @Test
    fun `duplicate actions are not emitted`() = runTest {
        val navigator = DefaultNavigator()
        val navOptions: NavOptions = { /* options */ }

        navigator.navigateTo(AnotherDestination, navOptions)
        navigator.navigateTo(AnotherDestination, navOptions)

        val action = navigator.navigationActions.first()
        assertTrue(action is NavigationAction.NavigateTo, "Expected NavigateTo action")
        assertEquals(AnotherDestination, action.destination)
    }
}
