package com.firmfreez.app.core.style_compose.fonts

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

/**
 * This file defines the ExtendedFonts data class and provides a way to access extended font styles.
 * The styles are defined using the TextStyle class from Jetpack Compose.
 */
data class ExtendedFonts(
    val veryBigText: TextStyle,
    val verySmallCaption: TextStyle,
)

val LocalExtendedFonts = staticCompositionLocalOf<ExtendedFonts> {
    error("ExtendedFonts not provided")
}

/**
 * This function returns an instance of ExtendedFonts with specific font styles.
 * The styles are defined using the TextStyle class from Jetpack Compose.
 */
fun getExtendedFonts(
    extendedFontSizes: ExtendedFontSizes
): ExtendedFonts {
    return ExtendedFonts(
        veryBigText = with(extendedFontSizes.veryBigText) {
            TextStyle(
                fontFamily = Roboto,
                fontWeight = FontWeight.Bold,
                fontSize = fontSize,
                lineHeight = lineHeight,
                letterSpacing = letterSpacing
            )
        },
        verySmallCaption = with(extendedFontSizes.verySmallCaption) {
            TextStyle(
                fontFamily = Roboto,
                fontWeight = FontWeight.Normal,
                fontSize = fontSize,
                lineHeight = lineHeight,
                letterSpacing = letterSpacing
            )
        }
    )
}