package com.firmfreez.app.common.domain.models

data class Book(
    val id: String,
    val title: String,
    val author: String?,
    val description: String?,
    val coverUri: String?,
    val bookUri: String,
    val fileName: String?,
    val fileSizeBytes: Long?,
    val isAvailable: Boolean,
    val addedAtMillis: Long,
    val lastOpenedAtMillis: Long?,
    val lastLocatorJson: String?,
)
