package com.firmfreez.app.reader.impl.ui.models

import org.readium.r2.shared.publication.Locator

sealed interface Action {

    data object OnBackButtonClick : Action

    data class InitBookId(val bookId: String) : Action

    data class SetBookIsReady(val isReady: Boolean) : Action

    data class UpdatePageData(
        val currentPage: Int,
        val totalPages: Int,
        val locator: Locator
    ) : Action

    data class FailedToOpenPublication(val message: String) : Action

    data object OnNextPageButtonClicked : Action

    data object OnPrevPageButtonClicked : Action

    data object OnPageClicked : Action
}