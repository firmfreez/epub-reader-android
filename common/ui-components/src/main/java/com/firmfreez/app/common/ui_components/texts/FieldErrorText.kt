package com.firmfreez.app.common.ui_components.texts

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.firmfreez.app.core.style_compose.AppTheme

@Composable
fun FieldErrorText(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.error,
    )
}

@Preview(showBackground = true)
@Composable
private fun FieldErrorTextPreview() {
    AppTheme {
        FieldErrorText(text = "Какая то ошибка")
    }
}
