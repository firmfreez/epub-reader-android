package com.firmfreez.app.common.ui_components.input_field

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource

@Composable
fun InputFieldButton(
    @DrawableRes
    iconResId: Int,
    @StringRes
    iconContentDescriptionResId: Int?,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary
) {
    Image(
        modifier = modifier,
        painter = painterResource(iconResId),
        contentDescription = iconContentDescriptionResId?.let { stringResource(it) },
        colorFilter = ColorFilter.tint(color)
    )
}
