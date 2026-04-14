package com.firmfreez.app.common.ui_components.loader

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.firmfreez.app.common.ui_components.R
import com.firmfreez.app.core.style_compose.AppTheme
import com.firmfreez.app.core.style_compose.dimensions.LocalSize

@Composable
fun Loader(
    modifier: Modifier = Modifier,
    tint: Color = MaterialTheme.colorScheme.onPrimary,
    size: Dp = LocalSize.current.sizeX10,
    rotationPeriod: Int = LoaderDefaults.rotationPeriod,
) {
    val transition = rememberInfiniteTransition()
    val currentRotation by transition.animateFloat(
        initialValue = LoaderDefaults.initialRotationDegree,
        targetValue = LoaderDefaults.finishRotationDegree,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = rotationPeriod,
                easing = LinearEasing,
            ),
        ),
    )

    Icon(
        modifier = modifier
            .size(size)
            .rotate(currentRotation),
        painter = painterResource(R.drawable.ic_loader),
        tint = tint,
        contentDescription = null,
    )
}

@Composable
fun Loader(
    modifier: Modifier = Modifier,
    tint: Color = MaterialTheme.colorScheme.onPrimary,
    size: Dp = LocalSize.current.sizeX10,
    progress: Float,
    scaleFraction: Float = 2.5F
) {
    val currentRotation = LoaderDefaults.finishRotationDegree * progress
    val currentScale = progress.coerceAtMost(scaleFraction) / scaleFraction
    val currentAlpha = (progress.coerceAtMost(scaleFraction) / scaleFraction).coerceAtLeast(0.5F)

    Icon(
        modifier = modifier
            .size(size)
            .rotate(currentRotation)
            .scale(currentScale)
            .alpha(currentAlpha),
        painter = painterResource(R.drawable.ic_loader),
        tint = tint,
        contentDescription = null,
    )
}

private object LoaderDefaults {
    const val rotationPeriod = 1_000
    const val initialRotationDegree = 0f
    const val finishRotationDegree = 360f
}

@Preview
@Composable
private fun LoaderPreview() = AppTheme {
    Loader()
}
