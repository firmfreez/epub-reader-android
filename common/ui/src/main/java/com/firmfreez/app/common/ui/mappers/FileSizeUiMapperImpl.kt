package com.firmfreez.app.common.ui.mappers

import org.koin.core.annotation.Single
import java.util.Locale

@Single(binds = [FileSizeUiMapper::class])
class FileSizeUiMapperImpl : FileSizeUiMapper {

    override fun mapToString(sizeBytes: Long): String {
        val kb = 1024.0
        val mb = kb * 1024.0

        return when {
            sizeBytes >= mb -> String.format(Locale.getDefault(), "%.1f MB", sizeBytes / mb)
            sizeBytes >= kb -> String.format(Locale.getDefault(), "%.1f KB", sizeBytes / kb)
            else -> "$sizeBytes B"
        }
    }
}
