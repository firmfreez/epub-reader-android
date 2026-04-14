package com.firmfreez.app.core.style_compose.fonts

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.firmfreez.app.core.style.R

/**
 * Font family for the app.
 *
 * This font family is used throughout the app for various text styles.
 * It includes different font weights and styles for flexibility in design.
 *
 * @see [FontFamily]
 */
val Roboto = FontFamily(
    Font(R.font.roboto_thin, FontWeight.W100),
    Font(R.font.roboto_thin_italic, FontWeight.W100),

    Font(R.font.roboto_light, FontWeight.W300),
    Font(R.font.roboto_light_italic, FontWeight.W300),

    Font(R.font.roboto_regular, FontWeight.W400),
    Font(R.font.roboto_italic, FontWeight.W400),

    Font(R.font.roboto_medium, FontWeight.W500),
    Font(R.font.roboto_medium_italic, FontWeight.W500),

    Font(R.font.roboto_bold, FontWeight.W700),
    Font(R.font.roboto_bold_italic, FontWeight.W700),

    Font(R.font.roboto_black, FontWeight.W900),
    Font(R.font.roboto_black_italic, FontWeight.W900)
)
