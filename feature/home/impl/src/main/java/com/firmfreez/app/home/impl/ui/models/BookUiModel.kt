package com.firmfreez.app.home.impl.ui.models

data class BookUiModel(
    val id: String,
    val title: String,
    val author: String?,
    val coverUri: String?,
    val subtitle: String?,
    val isAvailable: Boolean,
)
