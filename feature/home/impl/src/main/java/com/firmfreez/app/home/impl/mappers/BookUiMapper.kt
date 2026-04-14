package com.firmfreez.app.home.impl.mappers

import com.firmfreez.app.book.importer.api.models.EpubUriAnalysis
import com.firmfreez.app.common.domain.models.Book
import com.firmfreez.app.home.impl.ui.models.BookUiModel

interface BookUiMapper {

    fun mapFromDomain(dto: Book): BookUiModel

    fun mapFromDomain(list: List<Book>): List<BookUiModel>

    fun mapDomainFromAnalysis(dto: EpubUriAnalysis): Book
}
