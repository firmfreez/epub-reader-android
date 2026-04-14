package com.firmfreez.app.common.data.repositories

import com.firmfreez.app.common.data.room.mappers.BookDomainMapper
import com.firmfreez.app.common.data.sources.BooksLocalSource
import com.firmfreez.app.common.domain.models.Book
import com.firmfreez.app.common.domain.models.result_wrapper.ErrorHandler
import com.firmfreez.app.common.domain.models.result_wrapper.ResultOf
import com.firmfreez.app.common.domain.repositories.BooksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Single

@Single(binds = [BooksRepository::class])
class BooksRepositoryImpl(
    @Provided override val errorHandler: ErrorHandler,
    @Provided private val localSource: BooksLocalSource,
    @Provided private val bookDomainMapper: BookDomainMapper,
) : BooksRepository {

    override fun observeBooks(): Flow<List<Book>> = localSource
        .observeAll()
        .map { list -> list.map(bookDomainMapper::mapToDomain) }

    override suspend fun getBook(id: String): ResultOf<Book?> = resultOf {
        localSource.getBook(id = id)?.let(bookDomainMapper::mapToDomain)
    }

    override suspend fun appendBook(book: Book): ResultOf<Unit> = resultOf {
        val entity = bookDomainMapper.mapFromDomain(book)

        localSource.addBook(entity)
    }

    override suspend fun updateBook(book: Book): ResultOf<Unit> = resultOf {
        val entity = bookDomainMapper.mapFromDomain(book)

        localSource.updateBook(entity)
    }

    override suspend fun deleteBook(id: String): ResultOf<Unit> = resultOf {
        localSource.deleteBook(id)
    }
}