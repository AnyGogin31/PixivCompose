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

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import neilt.mobile.pixiv.core.navigation.Destination
import neilt.mobile.pixiv.core.navigation.NavOptions
import neilt.mobile.pixiv.core.navigation.NavigationAction
import neilt.mobile.pixiv.core.navigation.Navigator

/**
 * Default implementation of [Navigator].
 *
 * This implementation ensures that duplicate navigation actions are not emitted and provides a flow of
 * navigation events for observation.
 */
internal class DefaultNavigator : Navigator {
    private val _navigationActions = Channel<NavigationAction>(Channel.BUFFERED)
    override val navigationActions = _navigationActions.receiveAsFlow()

    private var lastAction: NavigationAction? = null

    /**
     * Handles navigation actions by executing the provided block if the action is not a duplicate.
     *
     * @param action The navigation action to handle.
     * @param block A block of code to execute with the provided [NavigationAction].
     */
    private inline fun handleAction(action: NavigationAction, block: (NavigationAction) -> Unit) {
        if (action != lastAction) {
            lastAction = action.also(block)
        }
    }

    /**
     * Navigates to the specified [Destination] with the given navigation options.
     *
     * @param destination The target [Destination].
     * @param navOptions Additional options to customize navigation behavior.
     * @throws IllegalStateException if the action cannot be enqueued.
     */
    override suspend fun navigateTo(destination: Destination, navOptions: NavOptions) {
        handleAction(NavigationAction.NavigateTo(destination, navOptions)) {
            if (_navigationActions.trySend(it).isFailure) {
                throw IllegalStateException("Failed to enqueue navigation action")
            }
        }
    }

    /**
     * Navigates up in the navigation stack.
     *
     * @throws IllegalStateException if the action cannot be enqueued.
     */
    override suspend fun navigateUp() {
        handleAction(NavigationAction.NavigateUp) {
            if (_navigationActions.trySend(it).isFailure) {
                throw IllegalStateException("Failed to enqueue navigate up action")
            }
        }
    }
}
