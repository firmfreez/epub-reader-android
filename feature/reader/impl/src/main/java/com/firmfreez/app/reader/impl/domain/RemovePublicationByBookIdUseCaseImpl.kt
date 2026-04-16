package com.firmfreez.app.reader.impl.domain

import com.firmfreez.app.reader.impl.data.sources.PublicationsLocalSource
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Single
import org.readium.r2.shared.publication.Publication

@Single(binds = [RemovePublicationByBookIdUseCase::class])
class RemovePublicationByBookIdUseCaseImpl(
    @Provided private val publicationsLocalSource: PublicationsLocalSource
) : RemovePublicationByBookIdUseCase {

    override suspend fun invoke(bookId: String): Publication? {
        return publicationsLocalSource.remove(id = bookId)
    }
}