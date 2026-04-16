package com.firmfreez.app.common.data.room.mappers

import com.firmfreez.app.common.data.room.entities.BookEntity
import com.firmfreez.app.common.domain.models.Book
import org.koin.core.annotation.Single

@Single(binds = [BookDomainMapper::class])
class BookDomainMapperImpl : BookDomainMapper {

    override fun mapToDomain(entity: BookEntity): Book {
        return Book(
            id = entity.id,
            title = entity.title,
            author = entity.author,
            description = entity.description,
            coverUri = entity.coverUri,
            bookUri = entity.bookUri,
            fileName = entity.fileName,
            fileSizeBytes = entity.fileSizeBytes,
            isAvailable = entity.isAvailable,
            addedAtMillis = entity.addedAtMillis,
            lastOpenedAtMillis = entity.lastOpenedAtMillis,
            lastLocatorJson = entity.lastLocatorJson,
            progress = entity.progress
        )
    }

    override fun mapFromDomain(domain: Book): BookEntity {
        return BookEntity(
            id = domain.id,
            title = domain.title,
            author = domain.author,
            description = domain.description,
            coverUri = domain.coverUri,
            bookUri = domain.bookUri,
            fileName = domain.fileName,
            fileSizeBytes = domain.fileSizeBytes,
            isAvailable = domain.isAvailable,
            addedAtMillis = domain.addedAtMillis,
            lastOpenedAtMillis = domain.lastOpenedAtMillis,
            lastLocatorJson = domain.lastLocatorJson,
            progress = domain.progress
        )
    }
}