package com.firmfreez.app.common.ui_components.input_field

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldDefaults.indicatorLine
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.firmfreez.app.core.style_compose.AppTheme
import com.firmfreez.app.core.style_compose.dimensions.LocalSpacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = InputFieldDefaults.enabled,
    isError: Boolean = InputFieldDefaults.isError,
    singleLine: Boolean = InputFieldDefaults.singleLine,
    maxLines: Int = InputFieldDefaults.maxLines,
    minLines: Int = InputFieldDefaults.minLines,
    visualTransformation: VisualTransformation = InputFieldDefaults.visualTransformation,
    keyboardOptions: KeyboardOptions = InputFieldDefaults.keyboardOptions,
    keyboardActions: KeyboardActions = InputFieldDefaults.keyboardActions,
    colors: TextFieldColors = TextFieldDefaults.inputFieldDefaultColors(),
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    maxLength: Int? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    readOnly: Boolean = false,
    labelText: String? = null,
    placeholder: @Composable (() -> Unit)? = null,
    rightButton: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    contentPadding: PaddingValues = PaddingValues(LocalSpacing.current.paddingX4),
) {
    val currentTextStyle = if (enabled) textStyle else textStyle.copy(color = MaterialTheme.colorScheme.primaryContainer)
    val textSelectionColors = TextSelectionColors(
        handleColor = MaterialTheme.colorScheme.primary,
        backgroundColor = MaterialTheme.colorScheme.primary.copy(alpha = InputFieldDefaultColorsDefaults.disabledAlpha)
    )

    CompositionLocalProvider(LocalTextSelectionColors provides textSelectionColors) {
        BasicTextField(
            modifier = modifier
                .indicatorLine(enabled, isError, interactionSource, colors),
            value = value,
            onValueChange = { if (maxLength == null || it.text.length <= maxLength) onValueChange(it) },
            enabled = enabled,
            textStyle = currentTextStyle,
            singleLine = singleLine,
            maxLines = maxLines,
            minLines = minLines,
            interactionSource = interactionSource,
            readOnly = readOnly,
            visualTransformation = visualTransformation,
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
        ) { innerTextField ->
            TextFieldDefaults.DecorationBox(
                value = value.text,
                innerTextField = innerTextField,
                enabled = enabled,
                label = labelText?.let { { InputFieldLabel(text = it) } },
                trailingIcon = rightButton,
                leadingIcon = leadingIcon,
                placeholder = placeholder,
                colors = colors,
                isError = isError,
                singleLine = singleLine,
                interactionSource = interactionSource,
                visualTransformation = visualTransformation,
                contentPadding = contentPadding,
            )
        }
    }
}

@Composable
fun InputField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = InputFieldDefaults.enabled,
    isError: Boolean = InputFieldDefaults.isError,
    singleLine: Boolean = InputFieldDefaults.singleLine,
    maxLines: Int = InputFieldDefaults.maxLines,
    minLines: Int = InputFieldDefaults.minLines,
    visualTransformation: VisualTransformation = InputFieldDefaults.visualTransformation,
    keyboardOptions: KeyboardOptions = InputFieldDefaults.keyboardOptions,
    keyboardActions: KeyboardActions = InputFieldDefaults.keyboardActions,
    colors: TextFieldColors = TextFieldDefaults.inputFieldDefaultColors(),
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    maxLength: Int? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    readOnly: Boolean = false,
    labelText: String? = null,
    placeholder: @Composable (() -> Unit)? = null,
    rightButton: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    contentPadding: PaddingValues = PaddingValues(LocalSpacing.current.paddingX4),
) {
    var textFieldValueState by remember { mutableStateOf(TextFieldValue(text = value)) }
    val textFieldValue = textFieldValueState.copy(text = value)

    InputField(
        modifier = modifier,
        value = textFieldValue,
        onValueChange = {
            textFieldValueState = it
            if (value != it.text) {
                onValueChange(it.text)
            }
        },
        enabled = enabled,
        isError = isError,
        singleLine = singleLine,
        maxLines = maxLines,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        colors = colors,
        textStyle = textStyle,
        maxLength = maxLength,
        interactionSource = interactionSource,
        readOnly = readOnly,
        labelText = labelText,
        rightButton = rightButton,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        contentPadding = contentPadding,
        minLines = minLines
    )
}

object InputFieldDefaults {
    const val enabled = true
    const val isError = false
    const val singleLine = false
    const val maxLines = Int.MAX_VALUE
    const val minLines = 1
    val visualTransformation = VisualTransformation.None
    val keyboardOptions = KeyboardOptions.Default
    val keyboardActions = KeyboardActions()
}

@Preview(showBackground = true)
@Composable
private fun InputFieldPreview() {
    var text by remember { mutableStateOf("Text") }

    AppTheme {
        InputField(
            value = text,
            onValueChange = { text = it },
            enabled = true
        )
    }
}
