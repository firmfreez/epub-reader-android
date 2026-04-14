package com.firmfreez.app.core.style_compose.fonts

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.sp

/**
 * This file defines the FontSizes data class and provides a way to access font size values.
 * The values are defined in sp (scale-independent pixels).
 */
data class FontSizes(
    val displayLarge: FontStyleToken = FontStyleToken(57.sp, 64.sp, (-0.25).sp),
    val displayMedium: FontStyleToken = FontStyleToken(45.sp, 52.sp),
    val displaySmall: FontStyleToken = FontStyleToken(36.sp, 44.sp),

    val headlineLarge: FontStyleToken = FontStyleToken(32.sp, 40.sp),
    val headlineMedium: FontStyleToken = FontStyleToken(28.sp, 36.sp),
    val headlineSmall: FontStyleToken = FontStyleToken(24.sp, 32.sp),

    val titleLarge: FontStyleToken = FontStyleToken(22.sp, 28.sp),
    val titleMedium: FontStyleToken = FontStyleToken(16.sp, 24.sp, 0.15.sp),
    val titleSmall: FontStyleToken = FontStyleToken(14.sp, 20.sp, 0.1.sp),

    val bodyLarge: FontStyleToken = FontStyleToken(16.sp, 24.sp, 0.5.sp),
    val bodyMedium: FontStyleToken = FontStyleToken(14.sp, 20.sp, 0.25.sp),
    val bodySmall: FontStyleToken = FontStyleToken(12.sp, 16.sp, 0.4.sp),

    val labelLarge: FontStyleToken = FontStyleToken(14.sp, 20.sp, 0.1.sp),
    val labelMedium: FontStyleToken = FontStyleToken(12.sp, 16.sp, 0.5.sp),
    val labelSmall: FontStyleToken = FontStyleToken(11.sp, 16.sp, 0.5.sp)
)

data class ExtendedFontSizes(
    val veryBigText: FontStyleToken = FontStyleToken(100.sp, 120.sp),
    val verySmallCaption: FontStyleToken = FontStyleToken(8.sp, 12.sp)
)

val LocalExtendedFontSizes = staticCompositionLocalOf { ExtendedFontSizes() }
val LocalFontSizes = staticCompositionLocalOf { FontSizes() }
