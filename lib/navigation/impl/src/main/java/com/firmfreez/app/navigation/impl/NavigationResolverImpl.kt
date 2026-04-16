package com.firmfreez.app.navigation.impl

import com.firmfreez.app.home.api.HomeScreenProvider
import com.firmfreez.app.navigation.api.NavigationProvider
import com.firmfreez.app.navigation.api.NavigationResolver
import com.firmfreez.app.navigation.api.routes.AppNavigation
import com.firmfreez.app.navigation.api.routes.NavRoute
import com.firmfreez.app.reader.api.ReaderScreenProvider
import com.firmfreez.app.splash.api.SplashScreenProvider
import org.koin.core.annotation.Single
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

@Single(binds = [NavigationResolver::class])
internal class NavigationResolverImpl : NavigationResolver, KoinComponent {

    override fun resolve(route: NavRoute): NavigationProvider {
        return when(route) {
            AppNavigation.Splash -> get() as SplashScreenProvider
            AppNavigation.Home -> get() as HomeScreenProvider
            is AppNavigation.Reader -> get {
                parametersOf(route.bookId)
            } as ReaderScreenProvider
        }
    }
}
