package com.firmfreez.app.common.ui_components.snackbars

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ErrorSnackbar(
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onError,
    containerColor: Color = MaterialTheme.colorScheme.error,
    action: @Composable (RowScope.() -> Unit)? = null
) {
    AppSnackbar(
        modifier = modifier,
        snackbarHostState = snackbarHostState,
        color = color,
        containerColor = containerColor,
        action = action
    )
}

@Composable
fun SnackbarErrorCloseAction(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onError,
) {
    SnackbarCloseAction(
        modifier = modifier,
        color = color,
        onClick = onClick
    )
}
