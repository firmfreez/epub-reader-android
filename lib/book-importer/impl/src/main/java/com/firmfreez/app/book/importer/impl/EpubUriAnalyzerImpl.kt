package com.firmfreez.app.book.importer.impl

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import androidx.core.net.toUri
import com.firmfreez.app.book.importer.api.EpubUriAnalyzer
import com.firmfreez.app.book.importer.api.models.EpubUriAnalysis
import com.firmfreez.app.book.importer.api.models.EpubUriAnalysisError
import com.firmfreez.app.book.importer.impl.parser.EpubParser
import com.firmfreez.app.di.domain.CoroutineDispatchersType
import com.firmfreez.app.di.domain.CoroutineQualifiers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Single
import java.io.FileNotFoundException

@Single(binds = [EpubUriAnalyzer::class])
class EpubUriAnalyzerImpl(
    @Provided @param:CoroutineQualifiers(CoroutineDispatchersType.Io)
    private val dispatcherIo: CoroutineDispatcher,
    @Provided private val context: Context,
) : EpubUriAnalyzer {

    private val contentResolver: ContentResolver
        get() = context.contentResolver

    private val parser = EpubParser()

    override suspend fun analyze(uri: String): EpubUriAnalysis = withContext(dispatcherIo) {
        val parsedUri = uri.toUri()
        val meta = readMeta(parsedUri)
        val mimeType = contentResolver.getType(parsedUri)

        val baseResult = EpubUriAnalysis(
            uri = uri,
            displayName = meta.displayName,
            sizeBytes = meta.sizeBytes,
            mimeType = mimeType,
            isReadable = false,
            isEpub = false,
            error = null,
            title = null,
            author = null,
            description = null,
            coverPathInZip = null,
        )

        try {
            val inputStream = contentResolver.openInputStream(parsedUri)
                ?: return@withContext baseResult.withError(EpubUriAnalysisError.FILE_NOT_FOUND)

            inputStream.use { stream ->
                val parsedEpub = parser.parse(stream)
                    ?: return@withContext baseResult.withError(EpubUriAnalysisError.INVALID_EPUB)

                baseResult.copy(
                    isReadable = true,
                    isEpub = true,
                    error = null,
                    title = parsedEpub.title,
                    author = parsedEpub.author,
                    description = parsedEpub.description,
                    coverPathInZip = parsedEpub.coverPathInZip,
                )
            }
        } catch (_: SecurityException) {
            baseResult.withError(EpubUriAnalysisError.ACCESS_DENIED)
        } catch (_: FileNotFoundException) {
            baseResult.withError(EpubUriAnalysisError.FILE_NOT_FOUND)
        } catch (_: Throwable) {
            baseResult.withError(EpubUriAnalysisError.UNKNOWN)
        }
    }

    private fun readMeta(uri: Uri): UriMeta {
        val projection = arrayOf(
            OpenableColumns.DISPLAY_NAME,
            OpenableColumns.SIZE,
        )

        return runCatching {
            contentResolver.query(uri, projection, null, null, null)?.use { cursor ->
                cursor.toUriMeta()
            } ?: UriMeta()
        }.getOrDefault(UriMeta())
    }

    private fun Cursor.toUriMeta(): UriMeta {
        if (!moveToFirst()) return UriMeta()

        val nameIndex = getColumnIndex(OpenableColumns.DISPLAY_NAME)
        val sizeIndex = getColumnIndex(OpenableColumns.SIZE)

        val displayName = if (nameIndex != -1 && !isNull(nameIndex)) getString(nameIndex) else null
        val sizeBytes = if (sizeIndex != -1 && !isNull(sizeIndex)) getLong(sizeIndex) else null

        return UriMeta(
            displayName = displayName,
            sizeBytes = sizeBytes,
        )
    }

    private fun EpubUriAnalysis.withError(error: EpubUriAnalysisError): EpubUriAnalysis {
        return copy(
            isReadable = false,
            isEpub = false,
            error = error,
            title = null,
            author = null,
            description = null,
            coverPathInZip = null,
        )
    }

    private data class UriMeta(
        val displayName: String? = null,
        val sizeBytes: Long? = null,
    )
}