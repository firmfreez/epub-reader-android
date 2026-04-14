package com.firmfreez.app.navigation.api

import cafe.adriel.voyager.core.screen.Screen

/**
 * NavigationProvider is an interface that provides a method to get a Screen.
 * It is used to resolve navigation routes to their corresponding screens.
 */
interface NavigationProvider {

    fun get(): Screen
}