package com.firmfreez.app.home.impl.domain

import com.firmfreez.app.book.importer.api.EpubCoverExtractor
import com.firmfreez.app.common.domain.models.result_wrapper.ResultOf
import com.firmfreez.app.common.domain.repositories.BooksRepository
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Single

@Single(binds = [DeleteBookUseCase::class])
class DeleteBookUseCaseImpl(
    @Provided private val booksRepository: BooksRepository,
    @Provided private val bookCoverExtractor: EpubCoverExtractor,
) : DeleteBookUseCase {

    override suspend fun invoke(id: String): ResultOf<Unit> {
        bookCoverExtractor.removeCover(bookId = id)

        return booksRepository.deleteBook(id = id)
    }
}
