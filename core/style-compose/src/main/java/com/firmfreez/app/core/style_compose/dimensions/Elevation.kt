package com.firmfreez.app.core.style_compose.dimensions

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * This file defines the Elevation data class and provides a way to access elevation values.
 * The values are defined in dp (density-independent pixels).
 */
data class Elevation(
    val elevation0: Dp = 0.dp,
    val elevationX0_5: Dp = 2.dp,
    val elevationX1: Dp = 4.dp,
    val elevationX2: Dp = 4.dp,
)

val LocalElevation = staticCompositionLocalOf { Elevation() }
