package com.firmfreez.app.common.ui.compose.view_model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.firmfreez.app.common.ui.view_model.BaseViewModel
import kotlinx.coroutines.flow.FlowCollector

@Composable
fun <E> HandleUiEvents(
    viewModel: BaseViewModel<*, E>,
    vararg keys: Any?,
    collector: FlowCollector<E>,
) {
    LaunchedEffect(viewModel, keys) {
        viewModel.debouncedUiEvents
            .collect(collector)
    }

    LaunchedEffect(viewModel, keys) {
        viewModel.uiEvents
            .collect(collector)
    }
}
