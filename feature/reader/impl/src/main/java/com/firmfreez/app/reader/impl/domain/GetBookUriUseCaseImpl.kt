package com.firmfreez.app.reader.impl.domain

import com.firmfreez.app.common.domain.models.errors.AppThrowable
import com.firmfreez.app.common.domain.models.result_wrapper.ResultOf
import com.firmfreez.app.common.domain.models.result_wrapper.toSuccessOrNull
import com.firmfreez.app.common.domain.repositories.BooksRepository
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Single

@Single(binds = [GetBookUriUseCase::class])
class GetBookUriUseCaseImpl(
    @Provided private val booksRepository: BooksRepository
) : GetBookUriUseCase {

    override suspend fun invoke(bookId: String): ResultOf<String> {
        val book = booksRepository.getBook(id = bookId).toSuccessOrNull {
            return ResultOf.Fail(it)
        } ?: return ResultOf.Fail(AppThrowable.unknown())

        return ResultOf.Success(book.bookUri)
    }
}
