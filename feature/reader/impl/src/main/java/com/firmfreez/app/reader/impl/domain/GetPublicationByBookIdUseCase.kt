package com.firmfreez.app.reader.impl.domain

import org.readium.r2.shared.publication.Publication

interface GetPublicationByBookIdUseCase {

    operator fun invoke(bookId: String): Publication?
}