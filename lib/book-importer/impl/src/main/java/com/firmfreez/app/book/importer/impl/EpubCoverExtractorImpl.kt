package com.firmfreez.app.book.importer.impl

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import com.firmfreez.app.book.importer.api.EpubCoverExtractor
import com.firmfreez.app.book.importer.impl.parser.EpubParser
import com.firmfreez.app.di.domain.CoroutineDispatchersType
import com.firmfreez.app.di.domain.CoroutineQualifiers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Single
import java.io.File

@Single(binds = [EpubCoverExtractor::class])
class EpubCoverExtractorImpl(
    @Provided @param:CoroutineQualifiers(CoroutineDispatchersType.Io)
    private val dispatcherIo: CoroutineDispatcher,
    @Provided private val context: Context,
) : EpubCoverExtractor {

    private val contentResolver: ContentResolver
        get() = context.contentResolver

    private val parser = EpubParser()

    override suspend fun extractCover(
        bookId: String,
        bookUri: String,
    ): String? = withContext(dispatcherIo) {
        val uri = bookUri.toUri()

        val parsedEpub = contentResolver.openInputStream(uri)?.use { input ->
            parser.parse(input)
        } ?: return@withContext null

        val coverPath = parsedEpub.coverPathInZip ?: return@withContext null
        val coverBytes = parsedEpub.entries[coverPath] ?: return@withContext null

        val extension = coverPath.substringAfterLast('.', "jpg")
        val file = saveCoverToCache(
            bookId = bookId,
            bytes = coverBytes,
            extension = extension,
        )

        Uri.fromFile(file).toString()
    }

    private fun saveCoverToCache(
        bookId: String,
        bytes: ByteArray,
        extension: String,
    ): File {
        val dir = File(context.cacheDir, BOOK_COVERS_DIR_NAME).apply {
            mkdirs()
        }

        val file = File(dir, "$bookId.$extension")
        file.writeBytes(bytes)
        return file
    }

    override suspend fun removeCover(bookId: String) {
        val dir = File(context.cacheDir, BOOK_COVERS_DIR_NAME)
        dir.listFiles()
            ?.firstOrNull { it.nameWithoutExtension == bookId }
            ?.delete()
    }

    private companion object {
        const val BOOK_COVERS_DIR_NAME = "book_covers"
    }
}