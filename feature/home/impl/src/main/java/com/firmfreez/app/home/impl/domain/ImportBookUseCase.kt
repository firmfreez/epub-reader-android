package com.firmfreez.app.home.impl.domain

import android.net.Uri
import com.firmfreez.app.common.domain.models.result_wrapper.ResultOf
import com.firmfreez.app.home.impl.ui.models.BookUiModel

interface ImportBookUseCase {

    suspend operator fun invoke(uri: Uri): ResultOf<BookUiModel>
}