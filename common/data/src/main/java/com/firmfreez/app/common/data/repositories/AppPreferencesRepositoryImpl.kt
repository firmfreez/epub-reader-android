package com.firmfreez.app.common.data.repositories

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.dataStore
import com.firmfreez.app.common.data.data_store.AppPreferencesSerializer
import com.firmfreez.app.common.data.data_store.mappers.AppPreferencesDataProtoMapper
import com.firmfreez.app.common.domain.repositories.AppPreferencesRepository
import com.firmfreez.app.common.domain.repositories.LoggerRepository
import com.firmfreez.app.datastore.AppPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Single

@Single(binds = [AppPreferencesRepository::class])
class AppPreferencesRepositoryImpl(
    @Provided private val context: Context,
    @Provided private val appPreferencesProtoMapper: AppPreferencesDataProtoMapper,
    @Provided val logger: LoggerRepository,
) : AppPreferencesRepository {

    private val appPreferences: Flow<AppPreferences>
        get() = context.appPreferencesStore.data.catch { exception ->
            if (exception is IOException) {
                logger.e(TAG, "Error reading preferences: ${exception.localizedMessage}")
                emit(AppPreferences.getDefaultInstance())
            } else {
                throw exception
            }
        }

    override suspend fun isFirstAppLaunch() = appPreferences.map { preferences ->
        appPreferencesProtoMapper.mapToDomain(preferences)
    }.first().isFirstAppLaunch


    override suspend fun registerFirstAppLaunch() {
        context.appPreferencesStore.updateData { preferences ->
            preferences.toBuilder()
                .setIsFirstAppLaunch(false)
                .build()
        }
    }

    override val uriToOpen: Flow<String?> = appPreferences.map { preferences ->
        appPreferencesProtoMapper.mapToDomain(preferences)
    }.map { it.uriToOpen }

    override suspend fun setUriToOpen(uri: String?) {
        context.appPreferencesStore.updateData { preferences ->
            val builder = preferences.toBuilder()
            if (uri == null) {
                builder.clearUriToOpen()
            } else {
                builder.setUriToOpen(uri)
            }
            builder.build()
        }
    }

    private companion object {
        const val TAG = "AppPreferencesRepository"

        private const val DATA_STORE_FILE_NAME = "app_preferences.pb"

        private val Context.appPreferencesStore: DataStore<AppPreferences> by dataStore(
            fileName = DATA_STORE_FILE_NAME,
            serializer = AppPreferencesSerializer
        )
    }
}