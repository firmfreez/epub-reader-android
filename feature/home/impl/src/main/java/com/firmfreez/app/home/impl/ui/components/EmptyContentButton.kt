package com.firmfreez.app.home.impl.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.firmfreez.app.core.style_compose.AppTheme
import com.firmfreez.app.core.style_compose.dimensions.LocalBorderWidth
import com.firmfreez.app.core.style_compose.dimensions.LocalCornerRadii
import com.firmfreez.app.core.style_compose.dimensions.LocalSize
import com.firmfreez.app.core.style_compose.dimensions.LocalSpacing
import com.firmfreez.app.home.impl.R

@Composable
internal fun EmptyContentButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val spacing = LocalSpacing.current
    val cornerRadii = LocalCornerRadii.current
    val borderWidth = LocalBorderWidth.current
    val size = LocalSize.current

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(size.sizeX35)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(cornerRadii.cornersX4),
        color = MaterialTheme.colorScheme.surfaceVariant,
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        border = BorderStroke(
            width = borderWidth.borderX0_25,
            color = MaterialTheme.colorScheme.outline
        ),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = spacing.paddingX5),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = stringResource(R.string.home_screen_picker_empty_button_text),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Preview(name = "EmptyContentItem", showBackground = true)
@Composable
private fun EmptyContentButtonPreview() {
    AppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(LocalSpacing.current.paddingX4)
        ) {
            EmptyContentButton(
                onClick = {}
            )
        }
    }
}