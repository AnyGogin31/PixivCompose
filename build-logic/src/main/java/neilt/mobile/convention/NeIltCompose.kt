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

package neilt.mobile.convention

import com.android.build.api.dsl.CommonExtension
import neilt.mobile.convention.extensions.getPlugin
import neilt.mobile.convention.extensions.getVersion
import neilt.mobile.convention.extensions.libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.compose.ComposePlugin
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun Project.configureComposeMultiplatform(
    commonExtension: CommonExtension<*, *, *, *, *, *>
) {
    pluginManager.apply(libs.getPlugin("kotlinCompose").get().pluginId)
    pluginManager.apply(libs.getPlugin("compose").get().pluginId)

    extensions.configure<KotlinMultiplatformExtension> {
        sourceSets.commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.material3)

            implementation(compose.components.resources)
        }
    }

    configureCompose(commonExtension)
}

private fun Project.configureCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>
) {
    commonExtension.apply {
        buildFeatures {
            compose = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion = libs.getVersion("kotlinCompiler").toString()
        }
    }
}

private val KotlinMultiplatformExtension.compose : ComposePlugin.Dependencies
    get() = extensions.getByType()
