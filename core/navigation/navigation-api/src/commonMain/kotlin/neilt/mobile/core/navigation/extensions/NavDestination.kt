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

package neilt.mobile.core.navigation.extensions

import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import neilt.mobile.core.navigation.Destination

/**
 * Extension function for [NavDestination] to check if the destination or any destination in its hierarchy
 * matches a specific [Destination] type.
 *
 * @param T The type of [Destination] to check for.
 * @return `true` if the destination or any of its parents in the hierarchy matches the given [Destination] type,
 *         `false` otherwise.
 */
inline fun <reified T : Destination> NavDestination?.hasDestination(): Boolean {
    return this?.hierarchy?.any { it.hasRoute<T>() } == true
}

/**
 * Checks if the current [NavDestination] or any destination in its hierarchy matches the specified [Destination].
 *
 * This method uses the class of the given [Destination] to determine a match.
 *
 * @param destination The [Destination] to check for.
 * @return `true` if the destination or any of its parents in the hierarchy matches the given [Destination],
 *         `false` otherwise.
 */
fun NavDestination?.hasDestination(destination: Destination): Boolean {
    return this?.hierarchy?.any { it.hasRoute(destination::class) } == true
}
