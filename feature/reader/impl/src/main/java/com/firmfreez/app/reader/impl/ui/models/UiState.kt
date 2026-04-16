package com.firmfreez.app.reader.impl.ui.models

import org.readium.r2.shared.publication.Locator

data class UiState(
    val currentBookId: String?,
    val currentPage: Int,
    val totalPages: Int,
    val isOverlayShown: Boolean,
    val initialLocator: Locator?,
    val bookIsReady: Boolean,
) {

    val canGoBackward = currentPage > 1
    val canGoForward = currentPage < totalPages

    companion object {
        fun empty() = UiState(
            currentBookId = null,
            isOverlayShown = false,
            currentPage = 0,
            totalPages = 0,
            bookIsReady = false,
            initialLocator = null
        )
    }
}
