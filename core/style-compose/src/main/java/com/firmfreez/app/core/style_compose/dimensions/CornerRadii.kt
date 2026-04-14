package com.firmfreez.app.core.style_compose.dimensions

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * This file defines the CornerRadii data class and provides a way to access corner radius values.
 * The values are defined in dp (density-independent pixels).
 */
data class CornerRadii(
    val corners0: Dp = 0.dp,
    val cornersX0_5: Dp = 2.dp,
    val cornersX1: Dp = 4.dp,
    val cornersX1_5: Dp = 6.dp,
    val cornersX2: Dp = 8.dp,
    val cornersX2_5: Dp = 10.dp,
    val cornersX3: Dp = 12.dp,
    val cornersX4: Dp = 16.dp,
    val cornersX6: Dp = 24.dp
)

val LocalCornerRadii = staticCompositionLocalOf { CornerRadii() }
