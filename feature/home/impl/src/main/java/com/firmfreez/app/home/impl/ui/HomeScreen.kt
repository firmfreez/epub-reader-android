package com.firmfreez.app.home.impl.ui

import android.content.Intent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalResources
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.core.stack.StackEvent
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.transitions.ScreenTransition
import com.firmfreez.app.common.ui.compose.view_model.HandleUiEvents
import com.firmfreez.app.common.ui.screen_transitions.enterFade
import com.firmfreez.app.common.ui.screen_transitions.exitFade
import com.firmfreez.app.home.api.HomeScreenProvider
import com.firmfreez.app.home.impl.R
import com.firmfreez.app.home.impl.ui.components.ScreenContent
import com.firmfreez.app.home.impl.ui.models.Action
import com.firmfreez.app.home.impl.ui.models.UiEvent
import com.firmfreez.app.home.impl.ui.utils.rememberEpubPicker
import com.firmfreez.app.navigation.api.NavigationResolver
import com.firmfreez.app.navigation.api.routes.AppNavigation
import com.firmfreez.app.navigation.api.screenOf
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject
import org.koin.core.annotation.Factory

@OptIn(ExperimentalVoyagerApi::class)
@Factory(binds = [HomeScreenProvider::class])
class HomeScreen : Screen, HomeScreenProvider, ScreenTransition {

    override val key: ScreenKey = uniqueScreenKey

    override fun get(): Screen = this

    @Composable
    override fun Content() {
        val viewModel = koinViewModel<HomeScreenViewModel>()
        val uiState by viewModel.uiState.collectAsState()
        val navigator = LocalNavigator.currentOrThrow
        val navigationResolver: NavigationResolver = koinInject()
        val resources = LocalResources.current
        val context = LocalContext.current

        val openEpubPicker = rememberEpubPicker(
            onFilePicked = {
                runCatching {
                    context.contentResolver.takePersistableUriPermission(
                        it,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                }

                viewModel.onAction(Action.OnBookPicked(it))
            },
            onPickerUnavailable = {
                viewModel.onAction(Action.ShowError(text = resources.getString(R.string.home_screen_picker_unavailable_error_text)))
            }
        )

        HandleUiEvents(viewModel = viewModel) { event ->
            when (event) {
                is UiEvent.OpenDocumentPicker -> openEpubPicker()
                is UiEvent.OpenDocumentReader -> navigator.push(navigationResolver.screenOf(AppNavigation.Reader(bookId = event.bookId)))
            }
        }


        Scaffold { innerPaddings ->
            ScreenContent(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPaddings)
                    .imePadding(),
                uiState = uiState,
                onAction = viewModel::onAction
            )
        }
    }

    override fun enter(lastEvent: StackEvent): EnterTransition? {
        return enterFade(lastEvent = lastEvent)
    }

    override fun exit(lastEvent: StackEvent): ExitTransition? {
        return exitFade(lastEvent = lastEvent)
    }
}
