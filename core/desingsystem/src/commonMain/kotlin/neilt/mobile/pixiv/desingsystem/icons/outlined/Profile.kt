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
import neilt.mobile.pixiv.desingsystem.icons.PixivIconDefaults.PathFillBrush
import neilt.mobile.pixiv.desingsystem.icons.PixivIcons

val PixivIcons.Outlined.Profile: ImageVector by lazy(LazyThreadSafetyMode.NONE) {
    ImageVector.Builder(
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 960f,
        viewportHeight = 960f,
    ).path(fill = PathFillBrush) {
        moveTo(480f, 480f)
        quadToRelative(-66f, 0f, -113f, -47f)
        reflectiveQuadToRelative(-47f, -113f)
        quadToRelative(0f, -66f, 47f, -113f)
        reflectiveQuadToRelative(113f, -47f)
        quadToRelative(66f, 0f, 113f, 47f)
        reflectiveQuadToRelative(47f, 113f)
        quadToRelative(0f, 66f, -47f, 113f)
        reflectiveQuadToRelative(-113f, 47f)
        close()
        moveTo(160f, 720f)
        verticalLineToRelative(-32f)
        quadToRelative(0f, -34f, 17.5f, -62.5f)
        reflectiveQuadTo(224f, 582f)
        quadToRelative(62f, -31f, 126f, -46.5f)
        reflectiveQuadTo(480f, 520f)
        quadToRelative(66f, 0f, 130f, 15.5f)
        reflectiveQuadTo(736f, 582f)
        quadToRelative(29f, 15f, 46.5f, 43.5f)
        reflectiveQuadTo(800f, 688f)
        verticalLineToRelative(32f)
        quadToRelative(0f, 33f, -23.5f, 56.5f)
        reflectiveQuadTo(720f, 800f)
        horizontalLineTo(240f)
        quadToRelative(-33f, 0f, -56.5f, -23.5f)
        reflectiveQuadTo(160f, 720f)
        close()
        moveToRelative(80f, 0f)
        horizontalLineToRelative(480f)
        verticalLineToRelative(-32f)
        quadToRelative(0f, -11f, -5.5f, -20f)
        reflectiveQuadTo(700f, 654f)
        quadToRelative(-54f, -27f, -109f, -40.5f)
        reflectiveQuadTo(480f, 600f)
        quadToRelative(-56f, 0f, -111f, 13.5f)
        reflectiveQuadTo(260f, 654f)
        quadToRelative(-9f, 5f, -14.5f, 14f)
        reflectiveQuadToRelative(-5.5f, 20f)
        verticalLineToRelative(32f)
        close()
        moveToRelative(240f, -320f)
        quadToRelative(33f, 0f, 56.5f, -23.5f)
        reflectiveQuadTo(560f, 320f)
        quadToRelative(0f, -33f, -23.5f, -56.5f)
        reflectiveQuadTo(480f, 240f)
        quadToRelative(-33f, 0f, -56.5f, 23.5f)
        reflectiveQuadTo(400f, 320f)
        quadToRelative(0f, 33f, 23.5f, 56.5f)
        reflectiveQuadTo(480f, 400f)
        close()
        moveToRelative(0f, -80f)
        close()
        moveToRelative(0f, 400f)
        close()
    }.build()
}
