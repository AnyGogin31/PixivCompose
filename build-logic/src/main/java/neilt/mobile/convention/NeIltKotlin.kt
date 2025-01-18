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
import neilt.mobile.convention.extensions.kotlin
import neilt.mobile.convention.extensions.libs
import org.gradle.api.Project

internal fun Project.configureKotlinMultiplatform(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    pluginManager.apply(libs.getPlugin("kotlin-multiplatform").get().pluginId)

    kotlin.apply {
        androidTarget {
            compilerOptions {
                jvmTarget.set(Configuration.JAVA_TARGET)
            }
        }

        iosX64()
        iosArm64()
        iosSimulatorArm64()

        jvm("desktop")

        jvmToolchain(Configuration.JAVA_TOOLCHAIN)
    }

    configureKotlin(commonExtension)
}

private fun Project.configureKotlin(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        compileSdk = libs.getVersion("android-compileSdk").toString().toInt()

        defaultConfig {
            minSdk = libs.getVersion("android-minSdk").toString().toInt()
        }

        compileOptions {
            sourceCompatibility = Configuration.JAVA_VERSION
            targetCompatibility = Configuration.JAVA_VERSION
        }
    }
}
