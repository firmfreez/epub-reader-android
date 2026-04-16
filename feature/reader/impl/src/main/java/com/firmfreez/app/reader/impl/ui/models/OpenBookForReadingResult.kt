package com.firmfreez.app.reader.impl.ui.models

import org.readium.r2.shared.publication.Locator

data class OpenBookForReadingResult(
    val bookId: String,
    val locator: Locator?
)