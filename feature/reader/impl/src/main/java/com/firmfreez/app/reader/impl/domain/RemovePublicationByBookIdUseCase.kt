package com.firmfreez.app.reader.impl.domain

import org.readium.r2.shared.publication.Publication

interface RemovePublicationByBookIdUseCase {

    suspend operator fun invoke(bookId: String): Publication?
}