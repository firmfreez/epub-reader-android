package com.firmfreez.app.common.data.sources

import com.firmfreez.app.common.data.room.dao.BooksDao
import com.firmfreez.app.common.data.room.db.BooksDatabase
import com.firmfreez.app.common.data.room.entities.BookEntity
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Single

@Single(binds = [BooksLocalSource::class])
class BooksDataBaseSourceImpl(
    @Provided private val booksDatabase: BooksDatabase
) : BooksLocalSource {

    private val dao: BooksDao
        get() = booksDatabase.booksDao()

    override fun observeAll(): Flow<List<BookEntity>> = dao.observeAll()

    override suspend fun getBook(id: String): BookEntity? {
        return dao.getById(id = id)
    }

    override suspend fun getBookByFileHash(fileHash: String): BookEntity? {
        return dao.getBookByFileHash(fileHash = fileHash)
    }

    override suspend fun isBookExists(fileHash: String): Boolean {
        return dao.isBookExists(fileHash = fileHash)
    }

    override suspend fun updateReadingPosition(
        id: String,
        readingPosition: String,
        progress: Float
    ) {
        dao.updateReadingPosition(bookId = id, locatorJson = readingPosition, progress = progress)
    }

    override suspend fun getLocatorInfo(id: String): String? {
        return dao.getLastLocatorJson(bookId = id)
    }

    override suspend fun addBook(book: BookEntity) {
        dao.upsert(book = book)
    }

    override suspend fun updateBook(book: BookEntity) {
        dao.upsert(book = book)
    }

    override suspend fun deleteBook(id: String) {
        dao.deleteById(id = id)
    }
}
