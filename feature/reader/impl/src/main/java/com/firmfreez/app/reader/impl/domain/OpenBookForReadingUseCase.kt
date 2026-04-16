package com.firmfreez.app.reader.impl.domain

import com.firmfreez.app.common.domain.models.result_wrapper.ResultOf
import com.firmfreez.app.reader.impl.ui.models.OpenBookForReadingResult

interface OpenBookForReadingUseCase {

    suspend operator fun invoke(bookId: String): ResultOf<OpenBookForReadingResult>
}
