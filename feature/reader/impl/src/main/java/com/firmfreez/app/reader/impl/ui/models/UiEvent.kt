package com.firmfreez.app.reader.impl.ui.models

sealed interface UiEvent {

    sealed interface ScreenUiEvent : UiEvent {

        data object NavigateUp : ScreenUiEvent
    }


    sealed interface ReaderUiEvent : UiEvent {

        data object GoNextPage : ReaderUiEvent

        data object GoPrevPage : ReaderUiEvent
    }
}
