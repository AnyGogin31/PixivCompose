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

import neilt.mobile.convention.extensions.getPlugin
import neilt.mobile.convention.extensions.libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jlleitschuh.gradle.ktlint.KtlintExtension
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

internal fun Project.configureAndroidKtlint() {
    pluginManager.apply(libs.getPlugin("ktlint").get().pluginId)

    extensions.configure<KtlintExtension> {
        android.set(true)
        verbose.set(true)
        outputToConsole.set(true)
        additionalEditorconfig.set(
            mapOf(
                "ktlint_standard_no-wildcard-imports" to "disabled",
                "ktlint_standard_filename" to "disabled",
                "ktlint_standard_function-naming" to "disabled",
                "ktlint_standard_function-signature" to "disabled",
                "ktlint_standard_class-naming" to "disabled",
                "ktlint_standard_annotation" to "disabled",
                "ktlint_standard_blank-line-before-declaration" to "disabled",
                "ktlint_standard_string-template-indent" to "disabled",
                "ktlint_standard_multiline-expression-wrapping" to "disabled"
            )
        )
        reporters {
            reporter(ReporterType.PLAIN)
        }
    }
}
