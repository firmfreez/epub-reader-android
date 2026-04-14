package com.firmfreez.app.core.style_compose.dimensions

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * This file defines the BorderWidth data class and provides a way to access border width values.
 * The values are defined in dp (density-independent pixels).
 */
data class BorderWidth(
    val border0: Dp = 0.dp,
    val borderX0_125: Dp = 0.5.dp,
    val borderX0_25: Dp = 1.dp,
    val borderX0_5: Dp = 2.dp,
    val borderX1: Dp = 4.dp,
)

val LocalBorderWidth = staticCompositionLocalOf { BorderWidth() }
