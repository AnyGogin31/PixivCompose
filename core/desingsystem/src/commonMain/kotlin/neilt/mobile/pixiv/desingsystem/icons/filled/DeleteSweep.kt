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

package neilt.mobile.pixiv.desingsystem.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import neilt.mobile.pixiv.desingsystem.icons.PixivIconDefaults.PathFillBrush
import neilt.mobile.pixiv.desingsystem.icons.PixivIcons

val PixivIcons.Filled.DeleteSweep: ImageVector by lazy(LazyThreadSafetyMode.NONE) {
    ImageVector.Builder(
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 960f,
        viewportHeight = 960f,
    ).path(fill = PathFillBrush) {
        moveTo(200f, 760f)
        quadToRelative(-33f, 0f, -56.5f, -23.5f)
        reflectiveQuadTo(120f, 680f)
        verticalLineToRelative(-360f)
        quadToRelative(-17f, 0f, -28.5f, -11.5f)
        reflectiveQuadTo(80f, 280f)
        quadToRelative(0f, -17f, 11.5f, -28.5f)
        reflectiveQuadTo(120f, 240f)
        horizontalLineToRelative(120f)
        verticalLineToRelative(-20f)
        quadToRelative(0f, -17f, 11.5f, -28.5f)
        reflectiveQuadTo(280f, 180f)
        horizontalLineToRelative(80f)
        quadToRelative(17f, 0f, 28.5f, 11.5f)
        reflectiveQuadTo(400f, 220f)
        verticalLineToRelative(20f)
        horizontalLineToRelative(120f)
        quadToRelative(17f, 0f, 28.5f, 11.5f)
        reflectiveQuadTo(560f, 280f)
        quadToRelative(0f, 17f, -11.5f, 28.5f)
        reflectiveQuadTo(520f, 320f)
        verticalLineToRelative(360f)
        quadToRelative(0f, 33f, -23.5f, 56.5f)
        reflectiveQuadTo(440f, 760f)
        horizontalLineTo(200f)
        close()
        moveToRelative(440f, -40f)
        quadToRelative(-17f, 0f, -28.5f, -11.5f)
        reflectiveQuadTo(600f, 680f)
        quadToRelative(0f, -17f, 11.5f, -28.5f)
        reflectiveQuadTo(640f, 640f)
        horizontalLineToRelative(80f)
        quadToRelative(17f, 0f, 28.5f, 11.5f)
        reflectiveQuadTo(760f, 680f)
        quadToRelative(0f, 17f, -11.5f, 28.5f)
        reflectiveQuadTo(720f, 720f)
        horizontalLineToRelative(-80f)
        close()
        moveToRelative(0f, -160f)
        quadToRelative(-17f, 0f, -28.5f, -11.5f)
        reflectiveQuadTo(600f, 520f)
        quadToRelative(0f, -17f, 11.5f, -28.5f)
        reflectiveQuadTo(640f, 480f)
        horizontalLineToRelative(160f)
        quadToRelative(17f, 0f, 28.5f, 11.5f)
        reflectiveQuadTo(840f, 520f)
        quadToRelative(0f, 17f, -11.5f, 28.5f)
        reflectiveQuadTo(800f, 560f)
        horizontalLineTo(640f)
        close()
        moveToRelative(0f, -160f)
        quadToRelative(-17f, 0f, -28.5f, -11.5f)
        reflectiveQuadTo(600f, 360f)
        quadToRelative(0f, -17f, 11.5f, -28.5f)
        reflectiveQuadTo(640f, 320f)
        horizontalLineToRelative(200f)
        quadToRelative(17f, 0f, 28.5f, 11.5f)
        reflectiveQuadTo(880f, 360f)
        quadToRelative(0f, 17f, -11.5f, 28.5f)
        reflectiveQuadTo(840f, 400f)
        horizontalLineTo(640f)
        close()
    }.build()
}
