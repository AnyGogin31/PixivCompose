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
import neilt.mobile.convention.extensions.libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompilerOptions
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

internal fun Project.configureAndroidKotlin(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    pluginManager.apply(libs.getPlugin("kotlinAndroid").get().pluginId)

    commonExtension.apply {
        compileSdk = Configuration.Sdk.COMPILE_SDK

        defaultConfig {
            minSdk = Configuration.Sdk.MIN_SDK
        }

        compileOptions {
            sourceCompatibility = Configuration.Java.JAVA_VERSION
            targetCompatibility = Configuration.Java.JAVA_VERSION
        }

        kotlinOptions {
            jvmTarget.set(Configuration.Java.JAVA_TARGET)
        }
    }
}

private fun Project.kotlinOptions(block: KotlinJvmCompilerOptions.() -> Unit) {
    tasks.withType<KotlinJvmCompile>().configureEach {
        compilerOptions {
            block()
        }
    }
}
