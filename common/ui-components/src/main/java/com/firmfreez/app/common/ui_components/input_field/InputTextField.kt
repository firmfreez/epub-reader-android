package com.firmfreez.app.common.ui_components.input_field

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.firmfreez.app.common.ui_components.VerticalSpacer
import com.firmfreez.app.common.ui_components.loader.ShimmerColors
import com.firmfreez.app.common.ui_components.loader.verticalShimmerBackground
import com.firmfreez.app.common.ui_components.texts.FieldErrorText
import com.firmfreez.app.common.ui_components.texts.FieldWarningText
import com.firmfreez.app.core.style_compose.AppTheme
import com.firmfreez.app.core.style_compose.colors.LocalExtendedColors
import com.firmfreez.app.core.style_compose.dimensions.LocalCornerRadii
import com.firmfreez.app.core.style_compose.dimensions.LocalSize
import com.firmfreez.app.core.style_compose.dimensions.LocalSpacing

@Composable
fun InputTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    height: Dp = InputTextFieldDefaults.height,
    enabled: Boolean = InputFieldDefaults.enabled,
    placeholderText: String = "",
    isError: Boolean = false,
    errorText: String? = null,
    isWarning: Boolean = false,
    warningText: String? = null,
    inputRegex: Regex? = null,
    maxLength: Int? = null,
    maxLines: Int? = null,
    minLines: Int? = null,
    singleLine: Boolean = true,
    focusManager: FocusManager = LocalFocusManager.current,
    transformation: VisualTransformation? = null,
    filterTransformation: ((TextFieldValue) -> TextFieldValue)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onFocusChanged: (Boolean) -> Unit = {},
    hideTextWhenDisabled: Boolean = true,
    style: TextStyle = MaterialTheme.typography.bodyLarge,
) {
    val backgroundColor = if (enabled) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.surfaceVariant
    val borderColor = when {
        isError -> MaterialTheme.colorScheme.error
        isWarning -> LocalExtendedColors.current.warning
        else -> backgroundColor
    }

    Column {
        Row(
            modifier = modifier
                .height(height)
                .background(
                    color = backgroundColor,
                    shape = InputTextFieldDefaults.bgrShape,
                )
                .border(
                    width = InputTextFieldDefaults.borderWidth,
                    color = borderColor,
                    shape = InputTextFieldDefaults.bgrShape,
                ),
            verticalAlignment = CenterVertically,
        ) {
            InputField(
                modifier = Modifier
                    .fillMaxHeight()
                    .onFocusChanged { state -> onFocusChanged(state.hasFocus) }
                    .weight(1F),
                value = if (!enabled && placeholderText.isNotEmpty() && hideTextWhenDisabled) TextFieldValue("") else value,
                onValueChange = {
                    if (it.text.isNotEmpty() && inputRegex != null && !it.text.contains(inputRegex)) {
                        return@InputField
                    }
                    onValueChange(filterTransformation?.let { transform -> transform(it) } ?: it)
                },
                maxLength = maxLength,
                enabled = enabled,
                colors = TextFieldDefaults.inputFieldTransparentIndicatorColors(),
                singleLine = singleLine,
                textStyle = style,
                placeholder = {
                    InputFieldPlaceholder(
                        text = placeholderText,
                    )
                },
                contentPadding = TextFieldDefaults.contentPaddingWithoutLabel(
                    top = LocalSpacing.current.paddingX2_5,
                    bottom = LocalSpacing.current.padding0,
                ),
                keyboardOptions = keyboardOptions,
                visualTransformation = if (!enabled && placeholderText.isNotEmpty() || transformation == null) {
                    InputFieldDefaults.visualTransformation
                } else {
                    transformation
                },
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus(force = true)
                    },
                ),
                maxLines = maxLines ?: Int.MAX_VALUE,
                minLines = minLines ?: 1,
            )
        }

        if (isError && errorText != null) {
            VerticalSpacer(height = LocalSpacing.current.paddingX1)

            FieldErrorText(text = errorText)
        } else if (isWarning && warningText != null) {
            VerticalSpacer(height = LocalSpacing.current.paddingX1)

            FieldWarningText(text = warningText)
        }
    }
}

@Composable
fun InputTextField(
    title: String,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    height: Dp = InputTextFieldDefaults.height,
    enabled: Boolean = InputFieldDefaults.enabled,
    placeholderText: String = "",
    isError: Boolean = false,
    errorText: String? = null,
    isWarning: Boolean = false,
    warningText: String? = null,
    inputRegex: Regex? = null,
    maxLength: Int? = null,
    maxLines: Int? = null,
    minLines: Int? = null,
    singleLine: Boolean = true,
    focusManager: FocusManager = LocalFocusManager.current,
    transformation: VisualTransformation? = null,
    filterTransformation: ((TextFieldValue) -> TextFieldValue)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onFocusChanged: (Boolean) -> Unit = {},
    hideTextWhenDisabled: Boolean = true,
    style: TextStyle = MaterialTheme.typography.titleLarge,
) {

    Column(modifier = modifier) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyMedium,
        )

        VerticalSpacer(height = LocalSpacing.current.paddingX2)


        InputTextField(
            value = value,
            onValueChange = onValueChange,
            height = height,
            enabled = enabled,
            placeholderText = placeholderText,
            isError = isError,
            errorText = errorText,
            isWarning = isWarning,
            warningText = warningText,
            inputRegex = inputRegex,
            maxLength = maxLength,
            maxLines = maxLines,
            minLines = minLines,
            singleLine = singleLine,
            focusManager = focusManager,
            transformation = transformation,
            filterTransformation = filterTransformation,
            keyboardOptions = keyboardOptions,
            onFocusChanged = onFocusChanged,
            hideTextWhenDisabled = hideTextWhenDisabled,
            style = style
        )
    }
}

@Composable
fun ShimmeredInputTextField(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.onSurface,
                shape = InputTextFieldDefaults.bgrShape,
            )
            .height(InputTextFieldDefaults.height)
            .verticalShimmerBackground(
                shape = InputTextFieldDefaults.bgrShape,
                shimmerGradientColors = ShimmerColors.shimmerDefault,
            ),
    ) {}
}

internal object InputTextFieldDefaults {
    val height = 48.dp
    val bgrShape: Shape
        @Composable
        get() = RoundedCornerShape(LocalCornerRadii.current.cornersX3)
    val borderWidth = 1.dp
}

@Preview(showBackground = false)
@Composable
private fun InputTextFieldPreview() {
    var text by remember { mutableStateOf(TextFieldValue("Какой то текстfdsfsd sdfsdfsdfsdfdsfsdf sfsdfsdfsdf  sdf sdfsdfsdf sdf sdf sdf sdfs dfdsfsdf")) }

    AppTheme {
        Column(
            modifier = Modifier.padding(horizontal = LocalSpacing.current.paddingX4),
            verticalArrangement = Arrangement.spacedBy(LocalSpacing.current.paddingX4),
        ) {
            InputTextField(
                value = text,
                height = LocalSize.current.sizeX22_5,
                singleLine = false,
                onValueChange = { text = it },
                enabled = true,
                placeholderText = "Test placeholder",
            )

            ShimmeredInputTextField()
        }
    }
}
