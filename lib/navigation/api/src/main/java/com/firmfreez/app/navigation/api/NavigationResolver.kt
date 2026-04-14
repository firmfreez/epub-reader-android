package com.firmfreez.app.navigation.api

import cafe.adriel.voyager.core.screen.Screen
import com.firmfreez.app.navigation.api.routes.NavRoute

/**
 * Interface for resolving navigation routes to their corresponding navigation providers.
 *
 * @see NavRoute
 * @see NavigationProvider
 */
interface NavigationResolver {

    fun resolve(route: NavRoute): NavigationProvider
}

/**
 * Extension function to resolve a navigation route to its corresponding screen.
 *
 * @param route The navigation route to resolve.
 * @return The screen corresponding to the provided navigation route.
 */
fun NavigationResolver.screenOf(route: NavRoute): Screen {
    return resolve(route).get()
}
