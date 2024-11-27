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

package neilt.mobile.pixiv.ui.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import neilt.mobile.pixiv.ui.icons.PixivIcons
import neilt.mobile.pixiv.ui.icons.PixivIcons.DefaultPathFillBrush

val PixivIcons.Filled.Profile: ImageVector by lazy(LazyThreadSafetyMode.NONE) {
    ImageVector.Builder(
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 960f,
        viewportHeight = 960f,
    ).path(fill = DefaultPathFillBrush) {
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
    }.build()
}
