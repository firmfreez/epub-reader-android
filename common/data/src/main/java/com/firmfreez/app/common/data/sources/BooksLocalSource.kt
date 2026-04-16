package com.firmfreez.app.common.data.sources

import com.firmfreez.app.common.data.room.entities.BookEntity
import kotlinx.coroutines.flow.Flow

interface BooksLocalSource {

    fun observeAll(): Flow<List<BookEntity>>

    suspend fun getBook(id: String): BookEntity?

    suspend fun updateReadingPosition(id: String, readingPosition: String, progress: Float)

    suspend fun getLocatorInfo(id: String): String?

    suspend fun addBook(book: BookEntity)

    suspend fun updateBook(book: BookEntity)

    suspend fun isBookExists(fileHash: String): Boolean

    suspend fun deleteBook(id: String)
}