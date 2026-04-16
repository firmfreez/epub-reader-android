package com.firmfreez.app.common.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey
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
    val progress: Float?
)
