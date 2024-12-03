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

package neilt.mobile.pixiv.desingsystem.icons.outlined

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import neilt.mobile.pixiv.desingsystem.icons.IconDefaults.PathFillBrush
import neilt.mobile.pixiv.desingsystem.icons.PixivIcons

val PixivIcons.Outlined.Home: ImageVector by lazy(LazyThreadSafetyMode.NONE) {
    ImageVector.Builder(
        name = "Outlined.Home",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 960f,
        viewportHeight = 960f,
    ).path(fill = PathFillBrush) {
        moveTo(240f, 760f)
        horizontalLineToRelative(120f)
        verticalLineToRelative(-200f)
        quadToRelative(0f, -17f, 11.5f, -28.5f)
        reflectiveQuadTo(400f, 520f)
        horizontalLineToRelative(160f)
        quadToRelative(17f, 0f, 28.5f, 11.5f)
        reflectiveQuadTo(600f, 560f)
        verticalLineToRelative(200f)
        horizontalLineToRelative(120f)
        verticalLineToRelative(-360f)
        lineTo(480f, 220f)
        lineTo(240f, 400f)
        verticalLineToRelative(360f)
        close()
        moveToRelative(-80f, 0f)
        verticalLineToRelative(-360f)
        quadToRelative(0f, -19f, 8.5f, -36f)
        reflectiveQuadToRelative(23.5f, -28f)
        lineToRelative(240f, -180f)
        quadToRelative(21f, -16f, 48f, -16f)
        reflectiveQuadToRelative(48f, 16f)
        lineToRelative(240f, 180f)
        quadToRelative(15f, 11f, 23.5f, 28f)
        reflectiveQuadToRelative(8.5f, 36f)
        verticalLineToRelative(360f)
        quadToRelative(0f, 33f, -23.5f, 56.5f)
        reflectiveQuadTo(720f, 840f)
        horizontalLineTo(560f)
        quadToRelative(-17f, 0f, -28.5f, -11.5f)
        reflectiveQuadTo(520f, 800f)
        verticalLineToRelative(-200f)
        horizontalLineToRelative(-80f)
        verticalLineToRelative(200f)
        quadToRelative(0f, 17f, -11.5f, 28.5f)
        reflectiveQuadTo(400f, 840f)
        horizontalLineTo(240f)
        quadToRelative(-33f, 0f, -56.5f, -23.5f)
        reflectiveQuadTo(160f, 760f)
        close()
        moveToRelative(320f, -270f)
        close()
    }.build()
}
