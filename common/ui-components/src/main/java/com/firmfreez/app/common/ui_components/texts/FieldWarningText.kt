package com.firmfreez.app.common.ui_components.texts

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.firmfreez.app.core.style_compose.AppTheme
import com.firmfreez.app.core.style_compose.colors.LocalExtendedColors

@Composable
fun FieldWarningText(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.labelLarge,
        color = LocalExtendedColors.current.warning,
    )
}

@Preview(showBackground = true)
@Composable
private fun FieldWarningTextPreview() {
    AppTheme {
        FieldWarningText(text = "Какое то предупреждение")
    }
}
