package com.firmfreez.app.home.impl.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.firmfreez.app.common.ui_components.button.AppButton
import com.firmfreez.app.common.ui_components.button.ButtonState
import com.firmfreez.app.core.style_compose.AppTheme
import com.firmfreez.app.core.style_compose.dimensions.LocalSize
import com.firmfreez.app.core.style_compose.dimensions.LocalSpacing
import com.firmfreez.app.home.impl.R

@Composable
internal fun EmptyBooksScreenContent(
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val spacing = LocalSpacing.current
    val size = LocalSize.current

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Outlined.MenuBook,
            contentDescription = null,
            modifier = Modifier.height(size.sizeX16),
            tint = MaterialTheme.colorScheme.primary,
        )

        Spacer(modifier = Modifier.height(spacing.paddingX4))

        Text(
            text = stringResource(R.string.home_screen_empty_title),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(spacing.paddingX2))

        Text(
            text = stringResource(R.string.home_screen_empty_message),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(0.78f),
        )

        Spacer(modifier = Modifier.height(spacing.paddingX6))

        AppButton(
            onClick = onAddClick,
            state = ButtonState.Outlined()
        ) {
            Text(text = stringResource(R.string.home_screen_empty_add_button))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EmptyBooksScreenContentPreview() =
    AppTheme {
        EmptyBooksScreenContent(
            onAddClick = {},
        )
    }