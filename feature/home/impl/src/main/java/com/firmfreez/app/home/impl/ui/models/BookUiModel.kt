package com.firmfreez.app.home.impl.ui.models

import androidx.compose.runtime.Immutable

data class BookUiModel(
    val id: String,
    val title: String,
    val author: String?,
    val coverUri: String?,
    val subtitle: String?,
    val isAvailable: Boolean,
    val progressState: ProgressState
) {

    @Immutable
    sealed interface ProgressState {
        data object New : ProgressState
        data class InProgress(val progress: Int) : ProgressState
        data object Finished : ProgressState
    }
}
