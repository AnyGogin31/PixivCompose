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
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavDestination.Companion.hasRoute
import neilt.mobile.core.navigation.Destination
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class NavDestinationTest {

    @Test
    fun `hasDestination() returns true for matching Destination type`() {
        val destination = mock<NavDestination>()
        val mockHierarchy = sequenceOf(
            mock<NavDestination> {
                whenever(it.hasRoute<TestDestination>()).thenReturn(true)
            }
        )

        whenever(destination.hierarchy).thenReturn(mockHierarchy)

        val result = destination.hasDestination<TestDestination>()

        assertTrue(result)
    }

    @Test
    fun `hasDestination() returns false for non-matching Destination type`() {
        val destination = mock<NavDestination>()
        val mockHierarchy = sequenceOf(
            mock<NavDestination> {
                whenever(it.hasRoute<TestDestination>()).thenReturn(false)
            }
        )

        whenever(destination.hierarchy).thenReturn(mockHierarchy)

        val result = destination.hasDestination<TestDestination>()

        assertFalse(result)
    }

    @Test
    fun `hasDestination(destination) returns true for matching Destination instance`() {
        val destination = mock<NavDestination>()
        val testDestination = TestDestination()
        val mockHierarchy = sequenceOf(
            mock<NavDestination> {
                whenever(it.hasRoute(testDestination::class)).thenReturn(true)
            }
        )

        whenever(destination.hierarchy).thenReturn(mockHierarchy)

        val result = destination.hasDestination(testDestination)

        assertTrue(result)
    }

    @Test
    fun `hasDestination(destination) returns false for non-matching Destination instance`() {
        val destination = mock<NavDestination>()
        val testDestination = TestDestination()
        val mockHierarchy = sequenceOf(
            mock<NavDestination> {
                whenever(it.hasRoute(testDestination::class)).thenReturn(false)
            }
        )

        whenever(destination.hierarchy).thenReturn(mockHierarchy)

        val result = destination.hasDestination(testDestination)

        assertFalse(result)
    }
}

class TestDestination : Destination
