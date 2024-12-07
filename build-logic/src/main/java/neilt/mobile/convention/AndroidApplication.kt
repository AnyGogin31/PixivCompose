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

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Project

internal fun Project.configureAndroidApplication(
    applicationExtension: ApplicationExtension,
) {
    applicationExtension.apply {
        defaultConfig {
            applicationId = Configuration.App.APPLICATION_ID

            targetSdk = Configuration.Sdk.TARGET_SDK

            versionName = Configuration.App.VERSION_NAME
            versionCode = Configuration.App.VERSION_CODE

            vectorDrawables {
                useSupportLibrary = true
            }
        }

        signingConfigs {
            create("release") {
                storeFile = file("keystore/key")
                storePassword = System.getenv("KEY_STORE_PASSWORD")
                keyAlias = System.getenv("KEY_ALIAS")
                keyPassword = System.getenv("KEY_PASSWORD")

                enableV1Signing = false
                enableV2Signing = true
                enableV3Signing = true
                enableV4Signing = true
            }
        }

        buildTypes {
            release {
                isMinifyEnabled = true
                isShrinkResources = true
                isCrunchPngs = true
                isDebuggable = false
                multiDexEnabled = true

                signingConfig = signingConfigs.getByName("release")

                proguardFiles(
                    getDefaultProguardFile(
                        "proguard-android-optimize.txt"
                    ),
                    "proguard-rules.pro"
                )
            }
        }
    }
}
