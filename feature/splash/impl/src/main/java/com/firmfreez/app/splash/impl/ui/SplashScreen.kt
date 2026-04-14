package com.firmfreez.app.splash.impl.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.transitions.ScreenTransition
import com.firmfreez.app.common.ui.compose.view_model.HandleUiEvents
import com.firmfreez.app.common.ui.screen_transitions.FadeTransition
import com.firmfreez.app.core.style.R
import com.firmfreez.app.core.style_compose.AppTheme
import com.firmfreez.app.core.style_compose.dimensions.LocalSize
import com.firmfreez.app.navigation.api.NavigationResolver
import com.firmfreez.app.navigation.api.routes.AppNavigation
import com.firmfreez.app.navigation.api.screenOf
import com.firmfreez.app.splash.api.SplashScreenProvider
import com.firmfreez.app.splash.impl.ui.models.UiEvent
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject
import org.koin.core.annotation.Factory

@OptIn(ExperimentalVoyagerApi::class)
@Factory(binds = [SplashScreenProvider::class])
class SplashScreen : Screen, SplashScreenProvider, ScreenTransition by FadeTransition {

    override val key: ScreenKey = uniqueScreenKey

    override fun get(): Screen = this

    @Composable
    override fun Content() {
        val viewModel = koinViewModel<SplashViewModel>()
        val navigator = LocalNavigator.currentOrThrow
        val navigationResolver = koinInject<NavigationResolver>()


        HandleUiEvents(viewModel) { event ->
            when (event) {
                is UiEvent.OpenHomeScreen -> navigator.replaceAll(navigationResolver.screenOf(AppNavigation.Home))
            }
        }

        AndroidSplashScreen()
    }
}

@Composable
internal fun AndroidSplashScreen(
    modifier: Modifier = Modifier,
    logoRes: Int = R.drawable.ic_launcher_foreground,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.size(LocalSize.current.sizeX70),
            painter = painterResource(id = logoRes),
            contentDescription = null,
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun AndroidSplashScreenPreview() {
    AppTheme {
        AndroidSplashScreen()
    }
}