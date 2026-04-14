package com.firmfreez.app.navigation.api.routes

/**
 * Represents a navigation route in the application.
 */
sealed interface NavRoute


/**
 * Represents the main navigation routes in the application.
 */
sealed interface AppNavigation : NavRoute {
    data object Splash : AppNavigation
    data object Home : AppNavigation
}

