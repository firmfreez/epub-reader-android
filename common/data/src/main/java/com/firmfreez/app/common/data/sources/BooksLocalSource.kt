package com.firmfreez.app.common.data.sources

import com.firmfreez.app.common.data.room.entities.BookEntity
import kotlinx.coroutines.flow.Flow

interface BooksLocalSource {

    fun observeAll(): Flow<List<BookEntity>>

    suspend fun getBook(id: String): BookEntity?

    suspend fun addBook(book: BookEntity)

    suspend fun updateBook(book: BookEntity)

    suspend fun deleteBook(id: String)
}