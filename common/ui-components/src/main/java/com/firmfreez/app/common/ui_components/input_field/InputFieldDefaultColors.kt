package com.firmfreez.app.common.ui_components.input_field

import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun TextFieldDefaults.inputFieldDefaultColors(
    focusedLabelColor: Color = MaterialTheme.colorScheme.primary,
    unfocusedLabelColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    unfocusedIndicatorColor: Color = MaterialTheme.colorScheme.outline,
    focusedIndicatorColor: Color = MaterialTheme.colorScheme.primary,
    trailingIconColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    cursorColor: Color = MaterialTheme.colorScheme.primary,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    disabledTextColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = InputFieldDefaultColorsDefaults.disabledAlpha),
    errorIndicatorColor: Color = MaterialTheme.colorScheme.error
) = colors(
    focusedLabelColor = focusedLabelColor,
    unfocusedLabelColor = unfocusedLabelColor,
    focusedContainerColor = backgroundColor,
    unfocusedContainerColor = backgroundColor,
    disabledContainerColor = backgroundColor,
    errorContainerColor = backgroundColor,
    unfocusedIndicatorColor = unfocusedIndicatorColor,
    focusedIndicatorColor = focusedIndicatorColor,
    disabledTrailingIconColor = trailingIconColor,
    focusedTrailingIconColor = trailingIconColor,
    unfocusedTrailingIconColor = trailingIconColor,
    errorTrailingIconColor = trailingIconColor,
    cursorColor = cursorColor,
    disabledTextColor = disabledTextColor,
    focusedTextColor = textColor,
    unfocusedTextColor = textColor,
    errorTextColor = textColor,
    errorIndicatorColor = errorIndicatorColor,
)

@Composable
fun TextFieldDefaults.inputFieldTransparentIndicatorColors(
    backgroundColor: Color = Color.Transparent,
    disabledIndicatorColor: Color = Color.Transparent,
    errorIndicatorColor: Color = Color.Transparent,
    focusedIndicatorColor: Color = Color.Transparent,
    unfocusedIndicatorColor: Color = Color.Transparent,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    disabledTextColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = InputFieldDefaultColorsDefaults.disabledAlpha),
    cursorColor: Color = MaterialTheme.colorScheme.primary
) = colors(
    focusedContainerColor = backgroundColor,
    unfocusedContainerColor = backgroundColor,
    disabledContainerColor = backgroundColor,
    errorContainerColor = backgroundColor,
    disabledIndicatorColor = disabledIndicatorColor,
    errorIndicatorColor = errorIndicatorColor,
    focusedIndicatorColor = focusedIndicatorColor,
    unfocusedIndicatorColor = unfocusedIndicatorColor,
    focusedTextColor = textColor,
    unfocusedTextColor = textColor,
    errorTextColor = textColor,
    disabledTextColor = disabledTextColor,
    cursorColor = cursorColor,
    selectionColors = TextSelectionColors(
        handleColor = cursorColor,
        backgroundColor = cursorColor.copy(alpha = InputFieldDefaultColorsDefaults.disabledAlpha),
    ),
)

internal object InputFieldDefaultColorsDefaults {
    const val disabledAlpha = 0.4f
}