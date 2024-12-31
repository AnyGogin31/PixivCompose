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

package neilt.mobile.pixiv.core.navigation

import kotlinx.coroutines.flow.Flow

/**
 * Interface for defining navigation behavior within an application.
 *
 * This interface provides methods for navigating to a destination or navigating up in the stack,
 * along with a flow of navigation actions to observe.
 */
interface Navigator {
    /**
     * A flow of [NavigationAction] events representing navigation actions.
     */
    val navigationActions: Flow<NavigationAction>

    /**
     * Navigates to the specified [Destination] with the provided navigation options.
     *
     * @param destination The target [Destination] to navigate to.
     * @param navOptions Additional navigation options for customizing the navigation behavior.
     */
    suspend fun navigateTo(destination: Destination, navOptions: NavOptions = {})

    /**
     * Navigates up in the navigation stack.
     */
    suspend fun navigateUp()
}
