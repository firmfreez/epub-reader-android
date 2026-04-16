package com.firmfreez.app.reader.impl.domain

import org.readium.r2.shared.publication.Locator

interface SetLastLocatorPositionUseCase {

    suspend operator fun invoke(bookId: String, locator: Locator, progress: Float)
}