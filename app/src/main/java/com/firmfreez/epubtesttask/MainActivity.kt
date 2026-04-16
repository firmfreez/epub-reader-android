package com.firmfreez.epubtesttask

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.AnticipateOvershootInterpolator
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.NavigatorDisposeBehavior
import cafe.adriel.voyager.transitions.ScreenTransition
import com.firmfreez.app.common.ui.screen_transitions.SlideTransition
import com.firmfreez.app.common.ui_components.snackbars.ErrorSnackbar
import com.firmfreez.app.common.ui_components.snackbars.SnackbarErrorCloseAction
import com.firmfreez.app.core.style_compose.AppTheme
import com.firmfreez.app.navigation.api.NavigationResolver
import com.firmfreez.app.navigation.api.routes.AppNavigation
import com.firmfreez.app.navigation.api.screenOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.get
import org.koin.androidx.compose.koinViewModel

class MainActivity : AppCompatActivity() {
    @OptIn(ExperimentalVoyagerApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        configSplashScreen()

        super.onCreate(savedInstanceState)

        val navigationResolver: NavigationResolver = get()

        enableEdgeToEdge()

        setContent {
            val viewModel: MainActivityViewModel = koinViewModel()
            val snackbarHostState = remember { SnackbarHostState() }
            var localNavigator by remember { mutableStateOf<Navigator?>(null) }

            AppTheme {
                Navigator(
                    screen = navigationResolver.screenOf(AppNavigation.Splash),
                    disposeBehavior = NavigatorDisposeBehavior(disposeSteps = false)
                ) { navigator ->
                    ScreenTransition(
                        navigator = navigator,
                        defaultTransition = SlideTransition,
                        disposeScreenAfterTransitionEnd = true
                    )
                    localNavigator = navigator
                }
            }

            ErrorHandler(
                snackbarHostState = snackbarHostState,
                viewModel = viewModel
            )
        }
    }

    @Composable
    private fun ErrorHandler(
        snackbarHostState: SnackbarHostState,
        viewModel: MainActivityViewModel
    ) {
        LaunchedEffect(viewModel.errors, snackbarHostState) {
            viewModel.errors
                .onEach(snackbarHostState::showSnackbar)
                .launchIn(this)
        }

        ErrorSnackbar(
            modifier = Modifier.statusBarsPadding(),
            snackbarHostState = snackbarHostState
        ) {
            SnackbarErrorCloseAction(onClick = { snackbarHostState.currentSnackbarData?.dismiss() })
        }
    }

    private fun configSplashScreen() {
        val splashScreen = installSplashScreen()

        splashScreen.setOnExitAnimationListener { splash ->
            val scaleX = ObjectAnimator.ofFloat(splash.iconView, View.SCALE_X, 1f, 1.5f, 1f)
            val scaleY = ObjectAnimator.ofFloat(splash.iconView, View.SCALE_Y, 1f, 1.5f, 1f)

            AnimatorSet().apply {
                playTogether(scaleX, scaleY)
                interpolator = AnticipateOvershootInterpolator()
                duration = 1000L
                doOnEnd { splash.remove() }
                start()
            }
        }
    }
}
