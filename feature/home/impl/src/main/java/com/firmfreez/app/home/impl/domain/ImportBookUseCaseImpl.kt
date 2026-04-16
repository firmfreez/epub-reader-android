package com.firmfreez.app.home.impl.domain

import android.content.Context
import android.net.Uri
import com.firmfreez.app.book.importer.api.EpubCoverExtractor
import com.firmfreez.app.book.importer.api.EpubUriAnalyzer
import com.firmfreez.app.book.importer.api.models.EpubUriAnalysisError
import com.firmfreez.app.common.domain.models.errors.AppThrowable
import com.firmfreez.app.common.domain.models.result_wrapper.ResultOf
import com.firmfreez.app.common.domain.models.result_wrapper.mapSuccess
import com.firmfreez.app.common.domain.models.result_wrapper.toSuccessOrNull
import com.firmfreez.app.common.domain.repositories.BooksRepository
import com.firmfreez.app.home.impl.R
import com.firmfreez.app.home.impl.mappers.BookUiMapper
import com.firmfreez.app.home.impl.ui.models.BookUiModel
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Single

@Single(binds = [ImportBookUseCase::class])
class ImportBookUseCaseImpl(
    @Provided private val booksRepository: BooksRepository,
    @Provided private val bookImporter: EpubUriAnalyzer,
    @Provided private val bookCoverExtractor: EpubCoverExtractor,
    @Provided private val bookUiMapper: BookUiMapper,
    @Provided private val context: Context,
) : ImportBookUseCase {


    override suspend fun invoke(uri: Uri, failIfExists: Boolean): ResultOf<BookUiModel> {
        val analyzeData = bookImporter.analyze(uri.toString())

        analyzeData.error?.let {
            val errorString = when (it) {
                EpubUriAnalysisError.FILE_NOT_FOUND -> context.resources.getString(R.string.home_screen_picker_file_not_found_error)
                EpubUriAnalysisError.ACCESS_DENIED -> context.resources.getString(R.string.home_screen_picker_file_access_denied_error)
                EpubUriAnalysisError.INVALID_EPUB -> context.resources.getString(R.string.home_screen_picker_file_invalid_file_error)
                EpubUriAnalysisError.UNKNOWN -> context.resources.getString(R.string.home_screen_picker_file_unknown_error)
            }

            return ResultOf.Fail(AppThrowable.custom(errorString))
        }

        val fileHash = analyzeData.fileHash ?: return ResultOf.Fail(AppThrowable.unknown())
        val isBookExists = booksRepository.isBookExists(fileHash = fileHash).toSuccessOrNull { return ResultOf.Fail(it) } == true
        if (isBookExists) {
            if (failIfExists) {
                return ResultOf.Fail(AppThrowable.custom(context.resources.getString(R.string.home_screen_picker_file_exists_error)))
            }

            return booksRepository.getBookByFileHash(fileHash = fileHash)
                .mapSuccess {
                    if (it == null) {
                        return ResultOf.Fail(AppThrowable.unknown())
                    }
                    bookUiMapper.mapFromDomain(it)
                }
        }


        val book = bookUiMapper.mapDomainFromAnalysis(dto = analyzeData).let {
            it.copy(
                coverUri = bookCoverExtractor.extractCover(bookId = it.id, bookUri = it.bookUri)
            )
        }

        return booksRepository.appendBook(book).mapSuccess { bookUiMapper.mapFromDomain(book) }
    }
}