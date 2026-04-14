package com.firmfreez.app.common.ui_components.snackbars

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.firmfreez.app.common.ui_components.R
import com.firmfreez.app.core.style_compose.AppTheme
import com.firmfreez.app.core.style_compose.dimensions.LocalCornerRadii
import com.firmfreez.app.core.style_compose.dimensions.LocalSize
import com.firmfreez.app.core.style_compose.dimensions.LocalSpacing

@Composable
fun AppSnackbar(
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    action: @Composable (RowScope.() -> Unit)? = null
) {
    SnackbarHost(
        modifier = Modifier,
        hostState = snackbarHostState
    ) { snackbarData ->
        SnackbarContent(
            modifier = modifier,
            text = snackbarData.visuals.message,
            color = color,
            containerColor = containerColor,
            action = action
        )
    }
}

@Composable
internal fun SnackbarContent(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    action: @Composable (RowScope.() -> Unit)? = null
) {
    val paddings = LocalSpacing.current
    val corners = LocalCornerRadii.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(paddings.paddingX4)
            .background(
                color = containerColor,
                shape = RoundedCornerShape(corners.cornersX3)
            )
    ) {
        Text(
            modifier = Modifier
                .padding(paddings.paddingX4)
                .weight(1f),
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = color
        )
        action?.invoke(this)
    }
}


@Composable
fun SnackbarCloseAction(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onPrimaryContainer,
) {
    val paddings = LocalSpacing.current
    val sizes = LocalSize.current

    Image(
        modifier = modifier
            .padding(paddings.paddingX4)
            .size(sizes.sizeX5)
            .clickable(onClick = onClick),
        painter = painterResource(R.drawable.ic_close),
        contentDescription = null,
        colorFilter = ColorFilter.tint(color = color)
    )
}


@Preview(
    name = "Light Theme",
    showBackground = false,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "Dark Theme",
    showBackground = false,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun SnackbarPreview() {
    AppTheme {
        SnackbarContent(
            text = "Something wrong"
        )
    }
}


@Preview(
    name = "Light Theme",
    showBackground = false,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "Dark Theme",
    showBackground = false,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun SnackbarWithCloseActionPreview() {
    AppTheme {
        SnackbarContent(
            text = "Network error",
            action = {
                SnackbarCloseAction(onClick = {})
            }
        )
    }
}


@Preview(
    name = "Light Theme",
    showBackground = false,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "Dark Theme",
    showBackground = false,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun SnackbarLongTextPreview() {
    AppTheme {
        SnackbarContent(
            text = "Something went wrong. Please, try again later! Went wrong. Please, try again later!"
        )
    }
}

@Preview(
    name = "Light Theme",
    showBackground = false,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "Dark Theme",
    showBackground = false,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun SnackbarLongTextActionPreview() {
    AppTheme {
        SnackbarContent(
            text = "Something went wrong. Please, try again later! Went wrong. Please, try again later!",
            action = {
                SnackbarCloseAction(onClick = {})
            }
        )
    }
}

