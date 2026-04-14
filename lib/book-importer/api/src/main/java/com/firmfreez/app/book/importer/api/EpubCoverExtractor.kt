package com.firmfreez.app.book.importer.api

// Return URI cover if it exists
interface EpubCoverExtractor {

    suspend fun extractCover(bookId: String, bookUri: String): String?

    suspend fun removeCover(bookId: String)
}