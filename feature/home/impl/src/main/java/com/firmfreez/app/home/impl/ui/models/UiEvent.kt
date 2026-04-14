package com.firmfreez.app.home.impl.ui.models

sealed interface UiEvent {

    data object OpenDocumentPicker : UiEvent
}
