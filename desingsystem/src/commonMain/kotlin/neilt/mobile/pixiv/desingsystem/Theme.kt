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

package neilt.mobile.pixiv.desingsystem

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import neilt.mobile.pixiv.desingsystem.provider.ThemeProvider
import org.koin.compose.koinInject

private val PixivDarkColorScheme = darkColorScheme()
private val PixivLightColorScheme = lightColorScheme()

@Composable
fun PixivTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    isDynamicColor: Boolean = true,
    themeProvider: ThemeProvider = koinInject(),
    content: @Composable () -> Unit,
) {
    val dynamicColor = isDynamicColor && themeProvider.isDynamicColorSupported
    val colorScheme = when {
        dynamicColor && isDarkTheme -> themeProvider.dynamicDarkColorScheme
        dynamicColor && !isDarkTheme -> themeProvider.dynamicLightColorScheme
        isDarkTheme -> PixivDarkColorScheme
        else -> PixivLightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = PixivTypography,
        content = content,
    )
}
