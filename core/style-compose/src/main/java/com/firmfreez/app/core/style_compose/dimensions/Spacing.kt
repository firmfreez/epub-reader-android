package com.firmfreez.app.core.style_compose.dimensions

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * This file defines the Spacing data class and provides a way to access spacing values.
 * The values are defined in dp (density-independent pixels).
 */
data class Spacing(
    val padding0: Dp = 0.dp,
    val paddingX0_5: Dp = 2.dp,
    val paddingX1: Dp = 4.dp,
    val paddingX1_5: Dp = 6.dp,
    val paddingX2: Dp = 8.dp,
    val paddingX2_5: Dp = 10.dp,
    val paddingX3: Dp = 12.dp,
    val paddingX3_5: Dp = 14.dp,
    val paddingX4: Dp = 16.dp,
    val paddingX4_5: Dp = 18.dp,
    val paddingX5: Dp = 20.dp,
    val paddingX5_5: Dp = 22.dp,
    val paddingX6: Dp = 24.dp,
    val paddingX7: Dp = 28.dp,
    val paddingX6_5: Dp = 26.dp,
    val paddingX7_5: Dp = 30.dp,
    val paddingX8: Dp = 32.dp,
    val paddingX8_5: Dp = 34.dp,
    val paddingX9: Dp = 36.dp,
    val paddingX9_5: Dp = 38.dp,
    val paddingX10: Dp = 40.dp,
    val paddingX10_5: Dp = 42.dp,
    val paddingX11: Dp = 44.dp,
    val paddingX12: Dp = 48.dp,
    val paddingX13: Dp = 52.dp,
    val paddingX15: Dp = 60.dp,
    val paddingX15_5: Dp = 62.dp,
    val paddingX18: Dp = 72.dp,
    val paddingX20: Dp = 80.dp,
    val paddingX24: Dp = 96.dp,
    val paddingX25: Dp = 100.dp,
    val paddingX50: Dp = 200.dp,
    val paddingX100: Dp = 400.dp,
)

val LocalSpacing = staticCompositionLocalOf { Spacing() }
