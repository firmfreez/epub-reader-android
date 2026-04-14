package com.firmfreez.app.core.style_compose

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import com.firmfreez.app.core.style_compose.colors.DarkColorScheme
import com.firmfreez.app.core.style_compose.colors.LightColorScheme
import com.firmfreez.app.core.style_compose.colors.LocalExtendedColors
import com.firmfreez.app.core.style_compose.colors.getExtendedColors
import com.firmfreez.app.core.style_compose.dimensions.BorderWidth
import com.firmfreez.app.core.style_compose.dimensions.ButtonHeight
import com.firmfreez.app.core.style_compose.dimensions.CornerRadii
import com.firmfreez.app.core.style_compose.dimensions.Elevation
import com.firmfreez.app.core.style_compose.dimensions.LocalBorderWidth
import com.firmfreez.app.core.style_compose.dimensions.LocalButtonHeight
import com.firmfreez.app.core.style_compose.dimensions.LocalCornerRadii
import com.firmfreez.app.core.style_compose.dimensions.LocalElevation
import com.firmfreez.app.core.style_compose.dimensions.LocalSize
import com.firmfreez.app.core.style_compose.dimensions.LocalSpacing
import com.firmfreez.app.core.style_compose.dimensions.Size
import com.firmfreez.app.core.style_compose.dimensions.Spacing
import com.firmfreez.app.core.style_compose.fonts.LocalExtendedFontSizes
import com.firmfreez.app.core.style_compose.fonts.LocalExtendedFonts
import com.firmfreez.app.core.style_compose.fonts.LocalFontSizes
import com.firmfreez.app.core.style_compose.fonts.getAppTypography
import com.firmfreez.app.core.style_compose.fonts.getExtendedFonts

/**
 * This file defines the AppTheme function, which is a custom theme for the application.
 * It uses Material Design 3 and provides a way to customize colors, typography, and other styles.
 * The theme can adapt to system settings (dark/light mode) and supports dynamic color on Android 12+.
 */
@Composable
fun AppTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (useDarkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        useDarkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val typographyScheme = getAppTypography(sizes = LocalFontSizes.current)

    val extendedColors = getExtendedColors(useDarkTheme = useDarkTheme)
    val extendedFonts = getExtendedFonts(extendedFontSizes = LocalExtendedFontSizes.current)

    val spacing = Spacing()
    val elevation = Elevation()
    val cornerRadii = CornerRadii()
    val borderWidth = BorderWidth()
    val buttonHeight = ButtonHeight()
    val size = Size()

    CompositionLocalProvider(
        LocalExtendedFonts provides extendedFonts,
        LocalExtendedColors provides extendedColors,
        LocalSpacing provides spacing,
        LocalElevation provides elevation,
        LocalCornerRadii provides cornerRadii,
        LocalBorderWidth provides borderWidth,
        LocalButtonHeight provides buttonHeight,
        LocalSize provides size
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = typographyScheme,
            content = content
        )
    }
}
