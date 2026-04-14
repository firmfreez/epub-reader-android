package com.firmfreez.app.home.impl.domain

import com.firmfreez.app.common.domain.models.result_wrapper.ResultOf

interface DeleteBookUseCase {

    suspend operator fun invoke(id: String): ResultOf<Unit>
}
