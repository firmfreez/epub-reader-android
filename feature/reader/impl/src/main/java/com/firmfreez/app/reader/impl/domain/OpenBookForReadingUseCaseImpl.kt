package com.firmfreez.app.reader.impl.domain

import android.content.ContentResolver
import android.content.Context
import androidx.core.net.toUri
import com.firmfreez.app.common.domain.models.errors.AppThrowable
import com.firmfreez.app.common.domain.models.result_wrapper.ResultOf
import com.firmfreez.app.common.domain.models.result_wrapper.toSuccessOrNull
import com.firmfreez.app.common.domain.repositories.BooksRepository
import com.firmfreez.app.reader.impl.R
import com.firmfreez.app.reader.impl.data.sources.PublicationsLocalSource
import com.firmfreez.app.reader.impl.ui.models.OpenBookForReadingResult
import org.json.JSONObject
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Single
import org.readium.r2.shared.publication.Locator
import org.readium.r2.shared.util.AbsoluteUrl
import org.readium.r2.shared.util.asset.AssetRetriever
import org.readium.r2.shared.util.getOrElse
import org.readium.r2.shared.util.http.DefaultHttpClient
import org.readium.r2.shared.util.toUrl
import org.readium.r2.streamer.PublicationOpener
import org.readium.r2.streamer.parser.DefaultPublicationParser
import kotlin.uuid.ExperimentalUuidApi

@Single(binds = [OpenBookForReadingUseCase::class])
class OpenBookForReadingUseCaseImpl(
    @Provided private val context: Context,
    @Provided private val localSource: PublicationsLocalSource,
    @Provided private val booksRepository: BooksRepository,
    @Provided private val getBookUri: GetBookUriUseCase,
) : OpenBookForReadingUseCase {

    private val contentResolver: ContentResolver
        get() = context.contentResolver

    private val httpClient by lazy { DefaultHttpClient() }

    private val assetRetriever by lazy {
        AssetRetriever(
            contentResolver = contentResolver,
            httpClient = httpClient,
        )
    }

    private val publicationParser by lazy {
        DefaultPublicationParser(
            context = context,
            httpClient = httpClient,
            assetRetriever = assetRetriever,
            pdfFactory = null,
        )
    }

    private val publicationOpener by lazy {
        PublicationOpener(
            publicationParser = publicationParser,
            contentProtections = emptyList(),
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun invoke(bookId: String): ResultOf<OpenBookForReadingResult> {
        return try {
            val locator = booksRepository.getLastLocatorInfo(id = bookId).toSuccessOrNull {}?.toLocatorOrNull()

            val bookUri = getBookUri(bookId = bookId).toSuccessOrNull { throw it } ?: throw AppThrowable.unknown()

            val absoluteUrl = bookUri.toUri().toUrl() as? AbsoluteUrl
                ?: throw AppThrowable.custom(message = context.getString(R.string.reader_open_error_invalid_uri))


            val asset = assetRetriever.retrieve(absoluteUrl).getOrElse { error ->
                throw AppThrowable.custom(message = mapAssetRetrieveErrorToText(error))
            }

            val publication = publicationOpener
                .open(
                    asset = asset,
                    allowUserInteraction = true,
                )
                .getOrElse { error ->
                    throw AppThrowable.custom(message = mapPublicationOpenErrorToText(error))
                }

            localSource.put(
                id = bookId,
                publication = publication,
            )

            ResultOf.Success(
                OpenBookForReadingResult(
                    bookId = bookId,
                    locator = locator
                )
            )
        } catch (error: AppThrowable) {
            ResultOf.Fail(error)
        } catch (_: Throwable) {
            ResultOf.Fail(
                AppThrowable.custom(
                    message = context.getString(R.string.reader_open_error_unknown)
                )
            )
        }
    }

    fun String.toLocatorOrNull(): Locator? {
        return runCatching {
            Locator.fromJSON(JSONObject(this))
        }.getOrNull()
    }

    private fun mapAssetRetrieveErrorToText(error: AssetRetriever.RetrieveUrlError): String {
        return when (error) {
            is AssetRetriever.RetrieveUrlError.FormatNotSupported -> {
                context.getString(R.string.reader_open_error_unsupported_format)
            }

            is AssetRetriever.RetrieveUrlError.Reading -> {
                context.getString(R.string.reader_open_error_asset)
            }

            is AssetRetriever.RetrieveUrlError.SchemeNotSupported -> {
                context.getString(R.string.reader_open_error_invalid_uri)
            }
        }
    }

    private fun mapPublicationOpenErrorToText(error: PublicationOpener.OpenError): String {
        return when (error) {
            is PublicationOpener.OpenError.FormatNotSupported -> {
                context.getString(R.string.reader_open_error_unsupported_format)
            }

            is PublicationOpener.OpenError.Reading -> {
                context.getString(R.string.reader_open_error_publication)
            }
        }
    }
}