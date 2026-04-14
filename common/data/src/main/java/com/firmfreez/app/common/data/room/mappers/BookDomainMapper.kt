package com.firmfreez.app.common.data.room.mappers

import com.firmfreez.app.common.data.room.entities.BookEntity
import com.firmfreez.app.common.domain.models.Book

interface BookDomainMapper {

    fun mapToDomain(entity: BookEntity): Book

    fun mapFromDomain(domain: Book): BookEntity
}
