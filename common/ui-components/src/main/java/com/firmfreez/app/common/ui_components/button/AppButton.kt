package com.firmfreez.app.common.ui_components.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.firmfreez.app.common.ui_components.loader.Loader
import com.firmfreez.app.core.style_compose.AppTheme
import com.firmfreez.app.core.style_compose.colors.LocalExtendedColors
import com.firmfreez.app.core.style_compose.dimensions.ButtonHeight
import com.firmfreez.app.core.style_compose.dimensions.LocalButtonHeight
import com.firmfreez.app.core.style_compose.dimensions.LocalCornerRadii
import com.firmfreez.app.core.style_compose.dimensions.LocalSize
import com.firmfreez.app.core.style_compose.dimensions.LocalSpacing

@Composable
fun AppButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    state: ButtonState = ButtonState.Filled(),
    colors: ButtonColorsType = ButtonColorsType.Primary,
    shape: Shape = RoundedCornerShape(LocalCornerRadii.current.cornersX3),
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    val height = state.sizeType.getAppButtonHeight(heights = LocalButtonHeight.current)

    when (state) {
        is ButtonState.Filled -> AppFilledButton(
            modifier = modifier.heightIn(min = height),
            onClick = onClick,
            isEnabled = state.enabled,
            shape = shape,
            colors = colors,
            contentPadding = contentPadding,
            content = content,
        )

        is ButtonState.Outlined -> AppOutlinedButton(
            modifier = modifier.height(height),
            onClick = onClick,
            isEnabled = state.enabled,
            shape = shape,
            colors = colors,
            contentPadding = contentPadding,
            content = content,
        )

        is ButtonState.Loading -> AppLoadingButton(
            modifier = modifier.height(height),
            colors = colors,
            contentPadding = contentPadding,
            shape = shape,
        )
    }
}

fun ButtonSizeType.getAppButtonHeight(heights: ButtonHeight): Dp = when (this) {
    ButtonSizeType.Default -> heights.heightX12
    ButtonSizeType.Medium -> heights.heightX10
    ButtonSizeType.Small -> heights.heightX9
}

@Composable
private fun AppFilledButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isEnabled: Boolean,
    shape: Shape,
    colors: ButtonColorsType,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    return Button(
        modifier = modifier,
        shape = shape,
        colors = colors.FilledButtonColors(),
        enabled = isEnabled,
        contentPadding = contentPadding,
        onClick = onClick,
    ) {
        ProvideAppButtonTextStyle {
            content(this)
        }
    }
}

@Composable
private fun AppOutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isEnabled: Boolean,
    shape: Shape,
    colors: ButtonColorsType,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    val strokeColor = when (colors) {
        ButtonColorsType.Transparent -> LocalExtendedColors.current.transparent
        ButtonColorsType.Primary -> MaterialTheme.colorScheme.primary
        ButtonColorsType.Secondary -> MaterialTheme.colorScheme.secondary
        ButtonColorsType.Tertiary -> MaterialTheme.colorScheme.tertiary
        ButtonColorsType.Surface -> MaterialTheme.colorScheme.surface
        ButtonColorsType.Error -> MaterialTheme.colorScheme.error
    }

    val textColor = when (colors) {
        ButtonColorsType.Transparent -> MaterialTheme.colorScheme.primary
        ButtonColorsType.Primary -> MaterialTheme.colorScheme.primary
        ButtonColorsType.Secondary -> MaterialTheme.colorScheme.secondary
        ButtonColorsType.Tertiary -> MaterialTheme.colorScheme.tertiary
        ButtonColorsType.Surface -> MaterialTheme.colorScheme.surface
        ButtonColorsType.Error -> MaterialTheme.colorScheme.error
    }


    val border = BorderStroke(
        width = AppButtonDefaults.strokeWidth,
        color = if (isEnabled) strokeColor else MaterialTheme.colorScheme.outline,
    )


    OutlinedButton(
        modifier =  modifier,
        enabled = isEnabled,
        shape = shape,
        contentPadding = contentPadding,
        border = border,
        colors = ButtonDefaults.buttonColors(
            contentColor = textColor,
            containerColor = MaterialTheme.colorScheme.background,
        ),
        onClick = onClick,
    ) {
        ProvideAppButtonTextStyle {
            content(this)
        }
    }
}

@Composable
private fun AppLoadingButton(
    shape: Shape,
    modifier: Modifier = Modifier,
    colors: ButtonColorsType,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    onClick: () -> Unit = {},
) {
    val loaderColors = when (colors) {
        ButtonColorsType.Primary -> MaterialTheme.colorScheme.onPrimary
        ButtonColorsType.Secondary -> MaterialTheme.colorScheme.onSecondary
        ButtonColorsType.Tertiary -> MaterialTheme.colorScheme.onTertiary
        ButtonColorsType.Surface -> MaterialTheme.colorScheme.onSurface
        ButtonColorsType.Error -> MaterialTheme.colorScheme.onError
        ButtonColorsType.Transparent -> MaterialTheme.colorScheme.primary
    }

    return Button(
        modifier = (modifier)
            .alpha(AppButtonDefaults.loadingStateAlpha),
        shape = shape,
        colors = colors.FilledButtonColors(),
        enabled = false,
        contentPadding = contentPadding,
        onClick = onClick,
    ) {
        Spacer(modifier = Modifier.weight(1F))

        Loader(
            modifier = Modifier
                .size(LocalSize.current.sizeX6)
                .align(Alignment.CenterVertically),
            tint = loaderColors,
        )

        Spacer(modifier = Modifier.weight(1F))
    }
}

@Composable
private fun ProvideAppButtonTextStyle(content: @Composable () -> Unit) = ProvideTextStyle(
    value = MaterialTheme.typography.titleMedium,
    content = content,
)

@Composable
private fun ButtonColorsType.FilledButtonColors() = when (this) {
    ButtonColorsType.Primary -> ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        disabledContainerColor = MaterialTheme.colorScheme.primaryContainer,
        disabledContentColor = MaterialTheme.colorScheme.onPrimaryContainer
    )

    ButtonColorsType.Secondary -> ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.secondary,
        contentColor = MaterialTheme.colorScheme.onSecondary,
        disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
        disabledContentColor = MaterialTheme.colorScheme.onSecondaryContainer
    )

    ButtonColorsType.Tertiary -> ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.tertiary,
        contentColor = MaterialTheme.colorScheme.onTertiary,
        disabledContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
        disabledContentColor = MaterialTheme.colorScheme.onTertiaryContainer
    )

    ButtonColorsType.Transparent -> ButtonDefaults.buttonColors(
        containerColor = LocalExtendedColors.current.transparent,
        contentColor = MaterialTheme.colorScheme.primary,
        disabledContainerColor = LocalExtendedColors.current.transparent,
        disabledContentColor = MaterialTheme.colorScheme.primaryContainer,
    )

    ButtonColorsType.Surface -> ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
        disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
        disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
    )

    ButtonColorsType.Error -> ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.error,
        contentColor = MaterialTheme.colorScheme.onError,
        disabledContainerColor = MaterialTheme.colorScheme.errorContainer,
        disabledContentColor = MaterialTheme.colorScheme.onErrorContainer
    )
}

enum class ButtonColorsType {
    Primary,
    Secondary,
    Tertiary,
    Surface,
    Error,
    Transparent,
}

enum class ButtonSizeType {
    Default,
    Medium,
    Small,
}

private object AppButtonDefaults {
    val strokeWidth = 1.dp
    const val loadingStateAlpha = 0.8F
}

@Immutable
sealed class ButtonState(open val enabled: Boolean, open val sizeType: ButtonSizeType) {
    data class Filled(
        override val enabled: Boolean = true,
        override val sizeType: ButtonSizeType = ButtonSizeType.Default,
    ) : ButtonState(enabled, sizeType)

    data class Outlined(
        override val enabled: Boolean = true,
        override val sizeType: ButtonSizeType = ButtonSizeType.Default,
    ) : ButtonState(enabled, sizeType)

    data class Loading(
        override val sizeType: ButtonSizeType = ButtonSizeType.Default,
    ) : ButtonState(enabled = false, sizeType)
}

@Preview(showBackground = true)
@Composable
private fun AppButtonPreview() {
    AppTheme {
        Column(modifier = Modifier) {
            AppButton(
                modifier = Modifier.padding(LocalSpacing.current.paddingX2),
                state = ButtonState.Filled(),
                colors = ButtonColorsType.Primary,
                onClick = {},
            ) {
                Text(text = "Filled button")
            }

            AppButton(
                modifier = Modifier.padding(LocalSpacing.current.paddingX2),
                state = ButtonState.Outlined(),
                onClick = {},
            ) {
                Text(text = "Outlined button")
            }

            AppButton(
                modifier = Modifier.padding(LocalSpacing.current.paddingX2),
                state = ButtonState.Loading(),
                onClick = {},
            ) {}
        }
    }
}
