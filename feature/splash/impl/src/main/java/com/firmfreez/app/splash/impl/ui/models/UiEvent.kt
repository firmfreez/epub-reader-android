package com.firmfreez.app.splash.impl.ui.models

sealed interface UiEvent {

    data object OpenHomeScreen : UiEvent
}