package com.firmfreez.app.core.style_compose.fonts

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

/**
 * This file defines the FontSizes data class and provides a way to access font size values.
 * The values are defined in TextUnit (text units).
 */
fun getAppTypography(sizes: FontSizes) = Typography(
    displayLarge = with(sizes.displayLarge) {
        TextStyle(
            fontFamily = Roboto,
            fontWeight = FontWeight.Normal,
            fontSize = fontSize,
            lineHeight = lineHeight,
            letterSpacing = letterSpacing
        )
    },
    displayMedium = with(sizes.displayMedium) {
        TextStyle(
            fontFamily = Roboto,
            fontWeight = FontWeight.Normal,
            fontSize = fontSize,
            lineHeight = lineHeight,
            letterSpacing = letterSpacing
        )
    },
    displaySmall = with(sizes.displaySmall) {
        TextStyle(
            fontFamily = Roboto,
            fontWeight = FontWeight.Normal,
            fontSize = fontSize,
            lineHeight = lineHeight,
            letterSpacing = letterSpacing
        )
    },

    headlineLarge = with(sizes.headlineLarge) {
        TextStyle(
            fontFamily = Roboto,
            fontWeight = FontWeight.Normal,
            fontSize = fontSize,
            lineHeight = lineHeight,
            letterSpacing = letterSpacing
        )
    },
    headlineMedium = with(sizes.headlineMedium) {
        TextStyle(
            fontFamily = Roboto,
            fontWeight = FontWeight.Normal,
            fontSize = fontSize,
            lineHeight = lineHeight,
            letterSpacing = letterSpacing
        )
    },
    headlineSmall = with(sizes.headlineSmall) {
        TextStyle(
            fontFamily = Roboto,
            fontWeight = FontWeight.Normal,
            fontSize = fontSize,
            lineHeight = lineHeight,
            letterSpacing = letterSpacing
        )
    },

    titleLarge = with(sizes.titleLarge) {
        TextStyle(
            fontFamily = Roboto,
            fontWeight = FontWeight.Normal,
            fontSize = fontSize,
            lineHeight = lineHeight,
            letterSpacing = letterSpacing
        )
    },
    titleMedium = with(sizes.titleMedium) {
        TextStyle(
            fontFamily = Roboto,
            fontWeight = FontWeight.Medium,
            fontSize = fontSize,
            lineHeight = lineHeight,
            letterSpacing = letterSpacing
        )
    },
    titleSmall = with(sizes.titleSmall) {
        TextStyle(
            fontFamily = Roboto,
            fontWeight = FontWeight.Medium,
            fontSize = fontSize,
            lineHeight = lineHeight,
            letterSpacing = letterSpacing
        )
    },

    bodyLarge = with(sizes.bodyLarge) {
        TextStyle(
            fontFamily = Roboto,
            fontWeight = FontWeight.Normal,
            fontSize = fontSize,
            lineHeight = lineHeight,
            letterSpacing = letterSpacing
        )
    },
    bodyMedium = with(sizes.bodyMedium) {
        TextStyle(
            fontFamily = Roboto,
            fontWeight = FontWeight.Normal,
            fontSize = fontSize,
            lineHeight = lineHeight,
            letterSpacing = letterSpacing
        )
    },
    bodySmall = with(sizes.bodySmall) {
        TextStyle(
            fontFamily = Roboto,
            fontWeight = FontWeight.Normal,
            fontSize = fontSize,
            lineHeight = lineHeight,
            letterSpacing = letterSpacing
        )
    },

    labelLarge = with(sizes.labelLarge) {
        TextStyle(
            fontFamily = Roboto,
            fontWeight = FontWeight.Medium,
            fontSize = fontSize,
            lineHeight = lineHeight,
            letterSpacing = letterSpacing
        )
    },
    labelMedium = with(sizes.labelMedium) {
        TextStyle(
            fontFamily = Roboto,
            fontWeight = FontWeight.Medium,
            fontSize = fontSize,
            lineHeight = lineHeight,
            letterSpacing = letterSpacing
        )
    },
    labelSmall = with(sizes.labelSmall) {
        TextStyle(
            fontFamily = Roboto,
            fontWeight = FontWeight.Medium,
            fontSize = fontSize,
            lineHeight = lineHeight,
            letterSpacing = letterSpacing
        )
    }
)
