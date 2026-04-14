package com.firmfreez.app.common.domain.repositories

interface AppPreferencesRepository {
    suspend fun isFirstAppLaunch(): Boolean
    suspend fun registerFirstAppLaunch()
}
