package com.firmfreez.app.common.ui_components.input_field

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.firmfreez.app.common.ui_components.R
import com.firmfreez.app.core.style_compose.AppTheme
import com.firmfreez.app.core.style_compose.dimensions.LocalSpacing

@Composable
fun InputPasswordField(
    password: TextFieldValue,
    onPasswordChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = InputFieldDefaults.enabled,
    placeholderText: String = "",
    isError: Boolean = false,
    onFocusChanged: (Boolean) -> Unit = {},
    style: TextStyle = MaterialTheme.typography.bodyLarge,
    hideTextIfDisabled: Boolean = true,
) {
    var isPasswordShowed by remember { mutableStateOf(false) }

    val passwordVisibilityChangeModifier = Modifier.clickable { isPasswordShowed = !isPasswordShowed }

    val focusManager = LocalFocusManager.current

    val backgroundColor = if (enabled) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.surfaceVariant

    Row(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = InputTextFieldDefaults.bgrShape,
            )
            .border(
                width = InputTextFieldDefaults.borderWidth,
                color = if (isError) MaterialTheme.colorScheme.error else backgroundColor,
                shape = InputTextFieldDefaults.bgrShape,
            ),
        verticalAlignment = CenterVertically,
    ) {
        InputField(
            modifier = Modifier
                .height(InputTextFieldDefaults.height)
                .onFocusChanged { state -> onFocusChanged(state.hasFocus) }
                .weight(1F),
            value = if (!enabled && placeholderText.isNotEmpty() && hideTextIfDisabled) TextFieldValue("") else password,
            onValueChange = onPasswordChange,
            enabled = enabled,
            colors = TextFieldDefaults.inputFieldTransparentIndicatorColors(),
            singleLine = true,
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
            keyboardOptions = KeyboardOptions.Default,
            visualTransformation = when {
                isPasswordShowed -> VisualTransformation.None
                else -> PasswordVisualTransformation(InputPasswordFieldDefaults.inputMaskLetter)
            },
            rightButton = {
                when {
                    isPasswordShowed -> InputFieldButton(
                        modifier = passwordVisibilityChangeModifier,
                        iconResId = R.drawable.ic_eye,
                        iconContentDescriptionResId = null
                    )

                    else -> InputFieldButton(
                        modifier = passwordVisibilityChangeModifier,
                        iconResId = R.drawable.ic_eye_closed,
                        iconContentDescriptionResId = null
                    )
                }
            },
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus(force = true)
                },
            ),
        )
    }
}

internal object InputPasswordFieldDefaults {
    const val inputMaskLetter = '*'
}

@Preview(showBackground = true)
@Composable
private fun InputPasswordFieldPreview() =
    AppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(LocalSpacing.current.paddingX4)
        ) {


            InputPasswordField(
                modifier = Modifier,
                password = TextFieldValue("12345"),
                onPasswordChange = {},
                placeholderText = "Enter password"
            )
        }
    }