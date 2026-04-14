package com.firmfreez.app.book.importer.api

import com.firmfreez.app.book.importer.api.models.EpubUriAnalysis

interface EpubUriAnalyzer {
    suspend fun analyze(uri: String): EpubUriAnalysis
}