package com.firmfreez.app.common.ui_components.loader

import androidx.compose.animation.core.DurationBasedAnimationSpec
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.firmfreez.app.core.style_compose.dimensions.LocalCornerRadii

fun Modifier.verticalShimmerBackground(
    shimmerGradientColors: List<Color>? = null,
    animationSpec: DurationBasedAnimationSpec<Float> = ShimmerDefaults.animationSpec,
    shape: Shape? = null,
    enabled: Boolean = true,
): Modifier = composed {
    val transition = rememberInfiniteTransition()

    val translateAnimation = transition.animateFloat(
        initialValue = ShimmerDefaults.shimmerInitialValue,
        targetValue = ShimmerDefaults.shimmerTargetValue,
        animationSpec = infiniteRepeatable(
            animation = animationSpec,
        ),
    )

    val colors =  shimmerGradientColors ?: ShimmerColors.shimmerDefault

    if (enabled) {
        clip(shape ?: ShimmerDefaults.defaultShape).drawWithContent {
            drawRoundRect(
                brush = verticalShimmerBrush(
                    width = size.width,
                    progress = size.width * translateAnimation.value,
                    shimmerGradientColors = colors,
                ),
            )
        }
    } else {
        this
    }
}

private fun verticalShimmerBrush(
    width: Float,
    progress: Float,
    shimmerGradientColors: List<Color>,
): Brush {
    return Brush.horizontalGradient(
        colors = shimmerGradientColors,
        startX = progress - width,
        endX = progress,
    )
}

private object ShimmerDefaults {
    const val animationDuration: Int = 1_800
    const val shimmerInitialValue = 0f
    const val shimmerTargetValue = 4f
    val animationSpec = tween<Float>(
        durationMillis = animationDuration,
        delayMillis = 0,
        easing = LinearOutSlowInEasing,
    )

    val defaultShape: Shape
        @Composable
        get() = RoundedCornerShape(LocalCornerRadii.current.cornersX3)
}

object ShimmerColors {
    val shimmerDefault: List<Color>
        @Composable
        get() = listOf(
            MaterialTheme.colorScheme.surfaceVariant,
            MaterialTheme.colorScheme.surface,
            MaterialTheme.colorScheme.surface,
            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f),
            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f),
            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f),
            MaterialTheme.colorScheme.surface,
            MaterialTheme.colorScheme.surface,
            MaterialTheme.colorScheme.surfaceVariant,
        )

}

@Preview(showBackground = true)
@Composable
private fun ShimmerItemPreview() {
    Column(
        modifier = Modifier
            .width(256.dp)
            .height(400.dp)
            .clip(RoundedCornerShape(24.dp))
            .verticalShimmerBackground(),
    ) {
    }
}
