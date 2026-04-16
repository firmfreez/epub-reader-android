package com.firmfreez.app.home.impl.mappers

import android.content.Context
import com.firmfreez.app.book.importer.api.models.EpubUriAnalysis
import com.firmfreez.app.common.domain.models.Book
import com.firmfreez.app.common.ui.mappers.FileSizeUiMapper
import com.firmfreez.app.home.impl.R
import com.firmfreez.app.home.impl.ui.models.BookUiModel
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Single
import java.util.UUID
import kotlin.math.roundToInt

@Single(binds = [BookUiMapper::class])
class BookUiMapperImpl(
    @Provided private val fileSizeUiMapper: FileSizeUiMapper,
    @Provided private val context: Context,
) : BookUiMapper {

    override fun mapFromDomain(dto: Book): BookUiModel {
        return BookUiModel(
            id = dto.id,
            title = dto.title,
            author = dto.author,
            coverUri = dto.coverUri,
            subtitle = buildSubtitle(
                author = dto.author,
                fileSizeBytes = dto.fileSizeBytes,
            ),
            isAvailable = dto.isAvailable,
            progressState = when(val progress = dto.progress) {
                null -> BookUiModel.ProgressState.New
                1f -> BookUiModel.ProgressState.Finished
                else -> BookUiModel.ProgressState.InProgress((progress * 100).roundToInt())
            }
        )
    }

    override fun mapFromDomain(list: List<Book>): List<BookUiModel> {
        return list.map(::mapFromDomain)
    }

    override fun mapDomainFromAnalysis(dto: EpubUriAnalysis): Book = with (dto) {
        val now = System.currentTimeMillis()

        return Book(
            id = UUID.randomUUID().toString(),
            title = title ?: displayName?.substringBeforeLast('.') ?: context.resources.getString(R.string.home_screen_book_empty_title_placeholder),
            author = author,
            description = description,
            coverUri = null,
            bookUri = uri,
            fileName = displayName,
            fileSizeBytes = sizeBytes,
            isAvailable = isReadable && isEpub,
            addedAtMillis = now,
            lastOpenedAtMillis = null,
            lastLocatorJson = null,
            progress = null
        )
    }

    private fun buildSubtitle(
        author: String?,
        fileSizeBytes: Long?,
    ): String? {
        val parts = buildList {
            author?.takeIf { it.isNotBlank() }?.let(::add)
            fileSizeBytes
                ?.takeIf { it > 0 }
                ?.let(fileSizeUiMapper::mapToString)
                ?.let(::add)
        }

        return parts.takeIf { it.isNotEmpty() }?.joinToString(separator = " • ")
    }
}