package com.firmfreez.app.core.style_compose.colors

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

/**
 * This file defines a data class for extended colors used in the application.
 * It includes colors for success, warning, and info states.
 *
 * The `getExtendedColors` function provides a way to retrieve the appropriate
 * color values based on the current theme (dark or light).
 */
data class ExtendedColors(
    val success: Color,
    val warning: Color,
    val info: Color,
    val transparent: Color,
)

val LocalExtendedColors = staticCompositionLocalOf<ExtendedColors> {
    error("ExtendedColors not provided")
}

fun getExtendedColors(
    useDarkTheme: Boolean
): ExtendedColors {
    return if (useDarkTheme) {
        ExtendedColors(
            success = Color(0xFF4CAF50),
            warning = Color(0xFFFFC107),
            info = Color(0xFF2196F3),
            transparent = Color(0x00000000)
        )
    } else {
        ExtendedColors(
            success = Color(0xFF388E3C),
            warning = Color(0xFFFF9800),
            info = Color(0xFF1976D2),
            transparent = Color(0x00000000)
        )
    }
}