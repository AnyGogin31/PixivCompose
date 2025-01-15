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

package neilt.mobile.pixiv.desingsystem.foundation.animation

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import kotlin.math.ln

fun Modifier.shimmerEffect(
    animationSpec: InfiniteRepeatableSpec<Float> = shimmerAnimationSpec(),
): Modifier = composed {
    var componentSize by remember { mutableStateOf(IntSize.Zero) }

    val shimmerStartOffsetMultiplier by remember(componentSize) {
        derivedStateOf {
            ln(componentSize.width.toFloat()).coerceAtLeast(1f)
        }
    }

    val transition = rememberInfiniteTransition()
    val shimmerOffsetX by transition.animateFloat(
        initialValue = -shimmerStartOffsetMultiplier * componentSize.width.toFloat(),
        targetValue = shimmerStartOffsetMultiplier * componentSize.width.toFloat(),
        animationSpec = animationSpec,
    )

    val colors = listOf(
        MaterialTheme.colorScheme.surface,
        MaterialTheme.colorScheme.primary.copy(alpha = 0.45f),
        MaterialTheme.colorScheme.surface,
    )
    val shimmerBrush = createShimmerBrush(colors, shimmerOffsetX, componentSize)

    this.background(shimmerBrush)
        .onGloballyPositioned { layoutCoordinates ->
            componentSize = layoutCoordinates.size
        }
}

fun shimmerAnimationSpec(
    durationMillis: Int = 1000,
): InfiniteRepeatableSpec<Float> {
    return infiniteRepeatable(
        animation = tween(
            durationMillis = durationMillis,
            easing = FastOutLinearInEasing,
        ),
    )
}

private fun createShimmerBrush(
    colors: List<Color>,
    startOffsetX: Float,
    size: IntSize,
): Brush {
    return Brush.linearGradient(
        colors = colors,
        start = Offset(startOffsetX, 0f),
        end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat()),
    )
}
