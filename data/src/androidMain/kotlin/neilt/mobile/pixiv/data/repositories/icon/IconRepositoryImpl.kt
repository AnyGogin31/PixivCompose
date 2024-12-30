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

package neilt.mobile.pixiv.data.repositories.icon

import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import neilt.mobile.pixiv.domain.repositories.icon.IconRepository
import java.util.Calendar

class IconRepositoryImpl(
    private val context: Context,
) : IconRepository {
    override suspend fun updateIconIfNeed() {
        val today = getTodayDate()
        val newIcon = when {
            isChristmasTime(today) -> PixivLauncherIcon.SNOW
            else -> PixivLauncherIcon.DEFAULT
        }

        if (!isIconEnabled(newIcon)) {
            setIcon(newIcon)
        }
    }

    private fun isIconEnabled(icon: PixivLauncherIcon): Boolean {
        val packageManager = context.packageManager
        val currentState = packageManager.getComponentEnabledSetting(icon.getComponentName(context))
        return currentState == PackageManager.COMPONENT_ENABLED_STATE_ENABLED ||
            currentState == PackageManager.COMPONENT_ENABLED_STATE_DEFAULT &&
            icon === PixivLauncherIcon.DEFAULT
    }

    private fun setIcon(icon: PixivLauncherIcon) {
        val packageManager = context.packageManager
        PixivLauncherIcon.entries.forEach { currentIcon ->
            packageManager.setComponentEnabledSetting(
                currentIcon.getComponentName(context),
                if (currentIcon === icon) {
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED
                } else {
                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED
                },
                PackageManager.DONT_KILL_APP,
            )
        }
    }

    private fun getTodayDate(): Calendar {
        return Calendar.getInstance()
    }

    private fun isChristmasTime(today: Calendar): Boolean {
        val month = today[Calendar.MONTH]
        val dayOfMonth = today[Calendar.DAY_OF_MONTH]

        return (month == Calendar.DECEMBER && dayOfMonth >= 24) ||
            (month == Calendar.JANUARY && dayOfMonth <= 7)
    }
}

enum class PixivLauncherIcon(private val key: String) {
    DEFAULT("DefaultIcon"),
    SNOW("SnowIcon"),
    ;

    fun getComponentName(context: Context): ComponentName {
        return ComponentName(context.packageName, "${context.packageName}.$key")
    }
}
