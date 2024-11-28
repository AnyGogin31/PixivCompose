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

import java.io.File
import java.util.Locale

val isWindows: Boolean
    get() = System.getProperty("os.name").lowercase(Locale.getDefault()).contains("windows")

val scriptsDir = File("$rootDir/scripts")
val hooksDir = File("$rootDir/.git/hooks")

tasks.register("installGitHooks") {
    description = "Installs the git hooks from the scripts directory."
    group = "git hooks"

    val scripts = scriptsDir.listFiles { file -> file.extension == "sh" }?.sortedBy { it.name } ?: emptyList()

    outputs.upToDateWhen {
        val hooks = hooksDir.listFiles()?.sortedBy { it.name } ?: emptyList()
        hooks.size == scripts.size && hooks.zip(scripts).all { (hook, script) ->
            hook.readText() == script.readText()
        }
    }

    doLast {
        if (!scriptsDir.exists()) {
            throw GradleException("Scripts directory does not exist: ${scriptsDir.absolutePath}. Please check your project setup.")
        }

        if (!hooksDir.exists()) {
            hooksDir.mkdirs()
        }

        if (scripts.isEmpty()) {
            throw GradleException("No shell scripts found in ${scriptsDir.absolutePath}.")
        }

        scripts.forEach { script ->
            val destination = File(hooksDir, script.nameWithoutExtension)
            script.copyTo(destination, overwrite = true)
            if (!isWindows) {
                destination.setExecutable(true)
            }
        }

        logger.lifecycle("Git hooks installed successfully in ${hooksDir.absolutePath}")
    }
}

afterEvaluate {
    tasks.matching {
        it.name in listOf("build", "assembleDebug", "clean")
    }.configureEach {
        dependsOn("installGitHooks")
    }
}
