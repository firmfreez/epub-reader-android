package com.firmfreez.app.home.impl.domain

interface GetUriToOpenUseCase {

    suspend operator fun invoke(): String?
}