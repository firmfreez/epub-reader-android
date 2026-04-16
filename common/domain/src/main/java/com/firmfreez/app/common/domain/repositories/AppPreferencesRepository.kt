package com.firmfreez.app.common.domain.repositories

import kotlinx.coroutines.flow.Flow

interface AppPreferencesRepository {
    suspend fun isFirstAppLaunch(): Boolean
    suspend fun registerFirstAppLaunch()

    val uriToOpen: Flow<String?>
    suspend fun setUriToOpen(uri: String?)
}
