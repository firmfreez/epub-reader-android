package com.firmfreez.app.home.impl.ui.utils

import android.content.ActivityNotFoundException
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable

@Composable
fun rememberEpubPicker(
    onFilePicked: (Uri) -> Unit,
    onPickerUnavailable: () -> Unit,
): () -> Unit {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        if (uri != null) {
            onFilePicked(uri)
        }
    }

    return {
        try {
            launcher.launch(arrayOf("application/epub+zip"))
        } catch (_: ActivityNotFoundException) {
            onPickerUnavailable()
        }
    }
}
