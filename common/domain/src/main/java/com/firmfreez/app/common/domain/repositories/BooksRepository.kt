package com.firmfreez.app.common.domain.repositories

import com.firmfreez.app.common.domain.models.Book
import com.firmfreez.app.common.domain.models.result_wrapper.ResultOf
import com.firmfreez.app.common.domain.models.result_wrapper.SafeRepositoryCall
import kotlinx.coroutines.flow.Flow

interface BooksRepository : SafeRepositoryCall {

    fun observeBooks(): Flow<List<Book>>

    suspend fun getBook(id: String): ResultOf<Book?>

    suspend fun isBookExists(fileHash: String): ResultOf<Boolean>

    suspend fun getLastLocatorInfo(id: String): ResultOf<String?>

    suspend fun setLastLocatorInfo(id: String, locatorJson: String, progress: Float): ResultOf<Unit>

    suspend fun appendBook(book: Book): ResultOf<Unit>

    suspend fun updateBook(book: Book): ResultOf<Unit>

    suspend fun deleteBook(id: String): ResultOf<Unit>
}
