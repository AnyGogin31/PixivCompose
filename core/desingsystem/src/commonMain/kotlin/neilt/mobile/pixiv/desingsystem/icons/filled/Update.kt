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

val PixivIcons.Filled.Update: ImageVector by lazy(LazyThreadSafetyMode.NONE) {
    ImageVector.Builder(
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 960f,
        viewportHeight = 960f,
    ).path(fill = PathFillBrush) {
        moveTo(480f, 840f)
        quadToRelative(-75f, 0f, -140.5f, -28.5f)
        reflectiveQuadToRelative(-114f, -77f)
        quadToRelative(-48.5f, -48.5f, -77f, -114f)
        reflectiveQuadTo(120f, 480f)
        quadToRelative(0f, -75f, 28.5f, -140.5f)
        reflectiveQuadToRelative(77f, -114f)
        quadToRelative(48.5f, -48.5f, 114f, -77f)
        reflectiveQuadTo(480f, 120f)
        quadToRelative(82f, 0f, 155.5f, 35f)
        reflectiveQuadTo(760f, 254f)
        verticalLineToRelative(-54f)
        quadToRelative(0f, -17f, 11.5f, -28.5f)
        reflectiveQuadTo(800f, 160f)
        quadToRelative(17f, 0f, 28.5f, 11.5f)
        reflectiveQuadTo(840f, 200f)
        verticalLineToRelative(160f)
        quadToRelative(0f, 17f, -11.5f, 28.5f)
        reflectiveQuadTo(800f, 400f)
        horizontalLineTo(640f)
        quadToRelative(-17f, 0f, -28.5f, -11.5f)
        reflectiveQuadTo(600f, 360f)
        quadToRelative(0f, -17f, 11.5f, -28.5f)
        reflectiveQuadTo(640f, 320f)
        horizontalLineToRelative(70f)
        quadToRelative(-41f, -56f, -101f, -88f)
        reflectiveQuadToRelative(-129f, -32f)
        quadToRelative(-117f, 0f, -198.5f, 81.5f)
        reflectiveQuadTo(200f, 480f)
        quadToRelative(0f, 117f, 81.5f, 198.5f)
        reflectiveQuadTo(480f, 760f)
        quadToRelative(95f, 0f, 170f, -57f)
        reflectiveQuadToRelative(99f, -147f)
        quadToRelative(5f, -16f, 18f, -24f)
        reflectiveQuadToRelative(29f, -6f)
        quadToRelative(17f, 2f, 27f, 14.5f)
        reflectiveQuadToRelative(6f, 27.5f)
        quadToRelative(-29f, 119f, -126f, 195.5f)
        reflectiveQuadTo(480f, 840f)
        close()
        moveToRelative(40f, -376f)
        lineToRelative(100f, 100f)
        quadToRelative(11f, 11f, 11f, 28f)
        reflectiveQuadToRelative(-11f, 28f)
        quadToRelative(-11f, 11f, -28f, 11f)
        reflectiveQuadToRelative(-28f, -11f)
        lineTo(452f, 508f)
        quadToRelative(-6f, -6f, -9f, -13.5f)
        reflectiveQuadToRelative(-3f, -15.5f)
        verticalLineToRelative(-159f)
        quadToRelative(0f, -17f, 11.5f, -28.5f)
        reflectiveQuadTo(480f, 280f)
        quadToRelative(17f, 0f, 28.5f, 11.5f)
        reflectiveQuadTo(520f, 320f)
        verticalLineToRelative(144f)
        close()
    }.build()
}
