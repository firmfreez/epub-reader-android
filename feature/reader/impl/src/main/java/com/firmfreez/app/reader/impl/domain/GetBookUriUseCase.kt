package com.firmfreez.app.reader.impl.domain

import com.firmfreez.app.common.domain.models.result_wrapper.ResultOf

interface GetBookUriUseCase {

    suspend operator fun invoke(bookId: String): ResultOf<String>
}