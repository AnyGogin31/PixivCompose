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

package neilt.mobile.convention.extensions

import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.DependencyHandlerScope

/**
 * Adds an implementation dependency on the specified project.
 *
 * @param project The project to add as an implementation dependency.
 */
internal fun DependencyHandlerScope.implementation(project: Project) {
    "implementation"(project)
}

/**
 * Adds an implementation dependency on the specified provider.
 *
 * @param provider The provider to add as an implementation dependency.
 */
internal fun DependencyHandlerScope.implementation(provider: Provider<*>) {
    "implementation"(provider)
}

/**
 * Adds a debug implementation dependency on the specified provider.
 *
 * @param provider The provider to add as a debug implementation dependency.
 */
internal fun DependencyHandlerScope.debugImplementation(provider: Provider<*>) {
    "debugImplementation"(provider)
}

/**
 * Adds a release implementation dependency on the specified provider.
 *
 * @param provider The provider to add as a release implementation dependency.
 */
internal fun DependencyHandlerScope.releaseImplementation(provider: Provider<*>) {
    "releaseImplementation"(provider)
}

/**
 * Adds an androidTest implementation dependency on the specified provider.
 *
 * @param provider The provider to add as an androidTest implementation dependency.
 */
internal fun DependencyHandlerScope.androidTestImplementation(provider: Provider<*>) {
    "androidTestImplementation"(provider)
}

/**
 * Adds a test implementation dependency on the specified provider.
 *
 * @param provider The provider to add as a test implementation dependency.
 */
internal fun DependencyHandlerScope.testImplementation(provider: Provider<*>) {
    "testImplementation"(provider)
}

/**
 * Adds a ksp dependency on the specified provider.
 *
 * @param provider The provider that contains the ksp dependency to be added.
 */
internal fun DependencyHandlerScope.ksp(provider: Provider<*>) {
    "ksp"(provider)
}
