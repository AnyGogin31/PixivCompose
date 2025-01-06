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

package neilt.mobile.pixiv.data.provider

import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import neilt.mobile.pixiv.domain.provider.UpdateCheckerProvider
import neilt.mobile.pixiv.domain.repositories.github.GitHubRepository
import java.util.regex.Pattern

class AndroidUpdateCheckerProvider(
    private val context: Context,
    private val gitHubRepository: GitHubRepository,
) : UpdateCheckerProvider {
    override suspend fun checkAppUpdates(newVersionAvailableMessage: String?): Boolean {
        return try {
            val latestRelease = gitHubRepository.getLatestGitHubRelease().tagName
            val currentVersion = getAppVersionFromManifest() ?: throw Exception()

            val formattedLatestVersion = formatVersion(latestRelease)
            if (isUpdateAvailable(currentVersion, formattedLatestVersion)) {
                newVersionAvailableMessage?.let {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                }
                return true
            }
            false
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    private fun formatVersion(version: String): String {
        return version.replaceFirst("^v".toRegex(), "")
    }

    private fun isUpdateAvailable(currentVersion: String, latestVersion: String): Boolean {
        val currentVersionParts = currentVersion.split(Pattern.compile("[.-]").toRegex())
        val latestVersionParts = latestVersion.split(Pattern.compile("[.-]").toRegex())

        for (i in 0 until minOf(currentVersionParts.size, latestVersionParts.size)) {
            val currentPart = currentVersionParts[i].toIntOrNull()
            val latestPart = latestVersionParts[i].toIntOrNull()

            if (currentPart == null || latestPart == null) {
                return currentVersionParts[i] < latestVersionParts[i]
            }
            if (latestPart > currentPart) {
                return true
            } else if (latestPart < currentPart) {
                return false
            }
        }
        return latestVersionParts.size > currentVersionParts.size
    }

    private fun getAppVersionFromManifest(): String? {
        return try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            packageInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            null
        }
    }
}
