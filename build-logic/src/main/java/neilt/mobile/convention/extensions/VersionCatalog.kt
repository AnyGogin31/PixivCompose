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

import org.gradle.api.artifacts.ExternalModuleDependencyBundle
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionConstraint
import org.gradle.api.provider.Provider
import org.gradle.plugin.use.PluginDependency

/**
 * Retrieves a plugin from the VersionCatalog by its name.
 *
 * @param pluginName The name of the plugin to find.
 * @return A Provider containing the PluginDependency.
 * @throws NoSuchElementException If the plugin is not found in the catalog.
 */
internal fun VersionCatalog.getPlugin(pluginName: String): Provider<PluginDependency> {
    return findPlugin(pluginName).orElseThrow {
        NoSuchElementException("Plugin with name $pluginName not found in the catalog")
    }
}

/**
 * Retrieves a library from the VersionCatalog by its name.
 *
 * @param libraryName The name of the library to find.
 * @return A Provider containing the MinimalExternalModuleDependency.
 * @throws NoSuchElementException If the library is not found in the catalog.
 */
internal fun VersionCatalog.getLibrary(libraryName: String): Provider<MinimalExternalModuleDependency> {
    return findLibrary(libraryName).orElseThrow {
        NoSuchElementException("Library with name $libraryName not found in the catalog")
    }
}

/**
 * Retrieves a version from the VersionCatalog by its name.
 *
 * @param versionName The name of the version to find.
 * @return The VersionConstraint for the specified version.
 * @throws NoSuchElementException If the version is not found in the catalog.
 */
internal fun VersionCatalog.getVersion(versionName: String): VersionConstraint {
    return findVersion(versionName).orElseThrow {
        NoSuchElementException("Version with name $versionName not found in the catalog")
    }
}
