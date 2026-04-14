package com.firmfreez.app.home.impl.domain

import com.firmfreez.app.home.impl.ui.models.BookUiModel
import kotlinx.coroutines.flow.Flow

interface ObserveBooksUseCase {

    operator fun invoke(): Flow<List<BookUiModel>>
}
