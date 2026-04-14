package com.firmfreez.app.home.impl.domain

import com.firmfreez.app.common.domain.repositories.BooksRepository
import com.firmfreez.app.home.impl.mappers.BookUiMapper
import com.firmfreez.app.home.impl.ui.models.BookUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Single

@Single(binds = [ObserveBooksUseCase::class])
class ObserveBooksUseCaseImpl(
    @Provided private val booksRepository: BooksRepository,
    @Provided private val bookUiMapper: BookUiMapper
) : ObserveBooksUseCase {

    override fun invoke(): Flow<List<BookUiModel>> {
        return booksRepository.observeBooks().map(bookUiMapper::mapFromDomain)
    }
}
