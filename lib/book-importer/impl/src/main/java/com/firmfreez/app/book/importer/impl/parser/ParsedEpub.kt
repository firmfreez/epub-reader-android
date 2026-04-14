package com.firmfreez.app.book.importer.impl.parser

internal data class ParsedEpub(
    val title: String?,
    val author: String?,
    val description: String?,
    val coverPathInZip: String?,
    val entries: Map<String, ByteArray>,
)