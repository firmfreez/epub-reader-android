package com.firmfreez.app.core.style_compose.dimensions

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * This file defines the ButtonHeight data class and provides a way to access button height values.
 * The values are defined in dp (density-independent pixels).
 */
data class ButtonHeight(
    val heightX16: Dp = 64.dp,
    val heightX12: Dp = 48.dp,
    val heightX10: Dp = 40.dp,
    val heightX9: Dp = 36.dp,
)

val LocalButtonHeight = staticCompositionLocalOf { ButtonHeight() }
