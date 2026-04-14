package com.firmfreez.app.home.impl.ui.models

import android.net.Uri

sealed interface Action {
    data object OnOpenBookClick : Action

    data class OnBookPicked(val uri: Uri) : Action

    data class ShowError(val text: String) : Action
}
