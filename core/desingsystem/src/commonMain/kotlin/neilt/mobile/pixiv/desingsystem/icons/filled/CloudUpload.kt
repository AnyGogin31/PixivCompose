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
import neilt.mobile.pixiv.desingsystem.icons.IconDefaults.PathFillBrush
import neilt.mobile.pixiv.desingsystem.icons.PixivIcons

val PixivIcons.Filled.CloudUpload: ImageVector by lazy(LazyThreadSafetyMode.NONE) {
    ImageVector.Builder(
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 960f,
        viewportHeight = 960f,
    ).path(fill = PathFillBrush) {
        moveTo(260f, 800f)
        quadToRelative(-91f, 0f, -155.5f, -63f)
        reflectiveQuadTo(40f, 583f)
        quadToRelative(0f, -78f, 47f, -139f)
        reflectiveQuadToRelative(123f, -78f)
        quadToRelative(25f, -92f, 100f, -149f)
        reflectiveQuadToRelative(170f, -57f)
        quadToRelative(117f, 0f, 198.5f, 81.5f)
        reflectiveQuadTo(760f, 440f)
        quadToRelative(69f, 8f, 114.5f, 59.5f)
        reflectiveQuadTo(920f, 620f)
        quadToRelative(0f, 75f, -52.5f, 127.5f)
        reflectiveQuadTo(740f, 800f)
        horizontalLineTo(520f)
        verticalLineToRelative(-286f)
        lineToRelative(36f, 35f)
        quadToRelative(11f, 11f, 27.5f, 11f)
        reflectiveQuadToRelative(28.5f, -12f)
        quadToRelative(11f, -11f, 11f, -28f)
        reflectiveQuadToRelative(-11f, -28f)
        lineTo(508f, 388f)
        quadToRelative(-12f, -12f, -28f, -12f)
        reflectiveQuadToRelative(-28f, 12f)
        lineTo(348f, 492f)
        quadToRelative(-11f, 11f, -11.5f, 27.5f)
        reflectiveQuadTo(348f, 548f)
        quadToRelative(11f, 11f, 27.5f, 11.5f)
        reflectiveQuadTo(404f, 549f)
        lineToRelative(36f, -35f)
        verticalLineToRelative(286f)
        horizontalLineTo(260f)
        close()
    }.build()
}
