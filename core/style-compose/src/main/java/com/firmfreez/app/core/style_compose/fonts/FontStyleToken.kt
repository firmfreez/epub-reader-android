package com.firmfreez.app.core.style_compose.fonts

import androidx.compose.ui.unit.TextUnit

/**
 * This file defines the FontStyleToken data class and provides a way to access font style values.
 * The values are defined in TextUnit (text units).
 */
data class FontStyleToken(
    val fontSize: TextUnit,
    val lineHeight: TextUnit,
    val letterSpacing: TextUnit = TextUnit.Unspecified
)