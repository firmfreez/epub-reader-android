package com.firmfreez.app.home.impl.ui.models

import androidx.compose.runtime.Immutable

data class UiState(
    val books: List<BookUiModel>?,
) {
    val isLoading: Boolean = books == null

    @Immutable
    sealed interface HomeContentState {
        data object Loading : HomeContentState
        data object Empty : HomeContentState
        data class Content(val books: List<BookUiModel>) : HomeContentState
    }

    val contentState: HomeContentState get() {
        return when {
            isLoading -> HomeContentState.Loading
            books.isNullOrEmpty() -> HomeContentState.Empty
            else -> HomeContentState.Content(books = books)
        }
    }

    companion object {
        fun empty(): UiState = UiState(
            books = null
        )
    }
}
