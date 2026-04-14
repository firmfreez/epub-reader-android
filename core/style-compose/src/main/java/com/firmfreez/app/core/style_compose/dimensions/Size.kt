package com.firmfreez.app.core.style_compose.dimensions

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * This file defines the Size data class and provides a way to access size values.
 * The values are defined in dp (density-independent pixels).
 */
data class Size(
    val sizeX0: Dp = 0.dp,
    val sizeX0_25: Dp = 1.dp,
    val sizeX0_5: Dp = 2.dp,
    val sizeX1: Dp = 4.dp,
    val sizeX1_5: Dp = 6.dp,
    val sizeX2: Dp = 8.dp,
    val sizeX2_5: Dp = 10.dp,
    val sizeX3: Dp = 12.dp,
    val sizeX4: Dp = 16.dp,
    val sizeX4_5: Dp = 18.dp,
    val sizeX5: Dp = 20.dp,
    val sizeX5_5: Dp = 22.dp,
    val sizeX6: Dp = 24.dp,
    val sizeX7: Dp = 28.dp,
    val sizeX8: Dp = 32.dp,
    val sizeX8_5: Dp = 32.dp,
    val sizeX9: Dp = 36.dp,
    val sizeX10: Dp = 40.dp,
    val sizeX10_5: Dp = 42.dp,
    val sizeX11: Dp = 44.dp,
    val sizeX12: Dp = 48.dp,
    val sizeX14: Dp = 56.dp,
    val sizeX16: Dp = 64.dp,
    val sizeX16_5: Dp = 66.dp,
    val sizeX18: Dp = 72.dp,
    val sizeX19: Dp = 76.dp,
    val sizeX21: Dp = 84.dp,
    val sizeX22: Dp = 88.dp,
    val sizeX22_5: Dp = 90.dp,
    val sizeX24: Dp = 96.dp,
    val sizeX25: Dp = 100.dp,
    val sizeX25_5: Dp = 102.dp,
    val sizeX26: Dp = 104.dp,
    val sizeX28: Dp = 112.dp,
    val sizeX30: Dp = 120.dp,
    val sizeX35: Dp = 140.dp,
    val sizeX44: Dp = 176.dp,
    val sizeX52: Dp = 208.dp,
    val sizeX55: Dp = 220.dp,
    val sizeX56: Dp = 224.dp,
    val sizeX64: Dp = 256.dp,
    val sizeX70: Dp = 280.dp,
    val sizeX76: Dp = 304.dp,
    val sizeX80: Dp = 320.dp,
    val sizeX81_5: Dp = 326.dp,
    val sizeX82: Dp = 328.dp,
    val sizeX86: Dp = 344.dp,
    val sizeX100: Dp = 400.dp,
    val sizeX150: Dp = 600.dp
)

val LocalSize = staticCompositionLocalOf { Size() }
