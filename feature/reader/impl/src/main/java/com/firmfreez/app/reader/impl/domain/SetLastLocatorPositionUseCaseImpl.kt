package com.firmfreez.app.reader.impl.domain

import com.firmfreez.app.common.domain.repositories.BooksRepository
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Single
import org.readium.r2.shared.publication.Locator

@Single(binds = [SetLastLocatorPositionUseCase::class])
class SetLastLocatorPositionUseCaseImpl(
    @Provided private val booksRepository: BooksRepository
) : SetLastLocatorPositionUseCase {

    override suspend fun invoke(bookId: String, locator: Locator, progress: Float) {
        booksRepository.setLastLocatorInfo(id = bookId, locatorJson = locator.toJSON().toString(), progress = progress)
    }
}

