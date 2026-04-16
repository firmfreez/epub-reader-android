package com.firmfreez.app.reader.impl.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
import com.firmfreez.app.reader.api.ReaderScreenProvider
import com.firmfreez.app.reader.impl.ui.components.ScreenContent
import com.firmfreez.app.reader.impl.ui.models.Action
import com.firmfreez.app.reader.impl.ui.models.UiEvent
import org.koin.compose.viewmodel.koinActivityViewModel
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam

@OptIn(ExperimentalVoyagerApi::class)
@Factory(binds = [ReaderScreenProvider::class])
class ReaderScreen(
    @InjectedParam val bookId: String
) : Screen, ReaderScreenProvider, ScreenTransition {

    override val key: ScreenKey = uniqueScreenKey

    override fun get(): Screen = this

    @Composable
    override fun Content() {
        val viewModel = koinActivityViewModel<ReaderScreenViewModel>()
        val uiState by viewModel.uiState.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        BackHandler {
            viewModel.onAction(Action.OnBackButtonClick)
        }

        LaunchedEffect(viewModel, bookId) {
            viewModel.onAction(Action.InitBookId(bookId = bookId))
        }

        HandleUiEvents(viewModel = viewModel) { event ->
            when (event) {
                is UiEvent.ScreenUiEvent.NavigateUp -> navigator.pop()
                is UiEvent.ReaderUiEvent -> { /* Handled in EpubReaderFragment */ }
            }
        }


        Scaffold { innerPaddings ->
            ScreenContent(
                modifier = Modifier
                    .fillMaxSize(),
                uiState = uiState,
                innerPaddings = innerPaddings,
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
