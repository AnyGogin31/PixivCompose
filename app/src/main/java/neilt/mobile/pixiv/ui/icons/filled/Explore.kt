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

val PixivIcons.Filled.Explore: ImageVector by lazy(LazyThreadSafetyMode.NONE) {
    ImageVector.Builder(
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 960f,
        viewportHeight = 960f,
    ).path(fill = DefaultPathFillBrush) {
        moveTo(335f, 650f)
        lineToRelative(202f, -58f)
        quadToRelative(20f, -6f, 34.5f, -20.5f)
        reflectiveQuadTo(592f, 537f)
        lineToRelative(58f, -202f)
        quadToRelative(3f, -11f, -5.5f, -19.5f)
        reflectiveQuadTo(625f, 310f)
        lineToRelative(-202f, 58f)
        quadToRelative(-20f, 6f, -34.5f, 20.5f)
        reflectiveQuadTo(368f, 423f)
        lineToRelative(-58f, 202f)
        quadToRelative(-3f, 11f, 5.5f, 19.5f)
        reflectiveQuadTo(335f, 650f)
        close()
        moveToRelative(145f, -110f)
        quadToRelative(-25f, 0f, -42.5f, -17.5f)
        reflectiveQuadTo(420f, 480f)
        quadToRelative(0f, -25f, 17.5f, -42.5f)
        reflectiveQuadTo(480f, 420f)
        quadToRelative(25f, 0f, 42.5f, 17.5f)
        reflectiveQuadTo(540f, 480f)
        quadToRelative(0f, 25f, -17.5f, 42.5f)
        reflectiveQuadTo(480f, 540f)
        close()
        moveToRelative(0f, 340f)
        quadToRelative(-83f, 0f, -156f, -31.5f)
        reflectiveQuadTo(197f, 763f)
        quadToRelative(-54f, -54f, -85.5f, -127f)
        reflectiveQuadTo(80f, 480f)
        quadToRelative(0f, -83f, 31.5f, -156f)
        reflectiveQuadTo(197f, 197f)
        quadToRelative(54f, -54f, 127f, -85.5f)
        reflectiveQuadTo(480f, 80f)
        quadToRelative(83f, 0f, 156f, 31.5f)
        reflectiveQuadTo(763f, 197f)
        quadToRelative(54f, 54f, 85.5f, 127f)
        reflectiveQuadTo(880f, 480f)
        quadToRelative(0f, 83f, -31.5f, 156f)
        reflectiveQuadTo(763f, 763f)
        quadToRelative(-54f, 54f, -127f, 85.5f)
        reflectiveQuadTo(480f, 880f)
        close()
    }.build()
}
