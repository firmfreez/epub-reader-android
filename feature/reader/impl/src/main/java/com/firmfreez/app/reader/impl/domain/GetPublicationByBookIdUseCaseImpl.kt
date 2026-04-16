package com.firmfreez.app.reader.impl.domain

import com.firmfreez.app.reader.impl.data.sources.PublicationsLocalSource
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Single
import org.readium.r2.shared.publication.Publication

@Single(binds = [GetPublicationByBookIdUseCase::class])
class GetPublicationByBookIdUseCaseImpl(
    @Provided private val publicationsLocalSource: PublicationsLocalSource
) : GetPublicationByBookIdUseCase {

    override fun invoke(bookId: String): Publication? {
        return publicationsLocalSource.get(id = bookId)
    }
}
