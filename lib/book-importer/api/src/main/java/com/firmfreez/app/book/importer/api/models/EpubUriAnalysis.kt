package com.firmfreez.app.book.importer.api.models

data class EpubUriAnalysis(
    val uri: String,
    val displayName: String?,
    val sizeBytes: Long?,
    val mimeType: String?,
    val isReadable: Boolean,
    val isEpub: Boolean,
    val error: EpubUriAnalysisError?,
)
