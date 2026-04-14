package com.firmfreez.app.common.usecases.app

interface IsFirstAppLaunchUseCase {

    suspend operator fun invoke(): Boolean
}