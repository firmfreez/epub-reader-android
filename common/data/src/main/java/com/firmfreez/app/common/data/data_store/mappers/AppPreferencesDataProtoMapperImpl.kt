package com.firmfreez.app.common.data.data_store.mappers

import com.firmfreez.app.common.domain.models.data_store.AppPreferencesData
import org.koin.core.annotation.Factory
import com.firmfreez.app.datastore.AppPreferences as AppPreferencesProto

@Factory(binds = [AppPreferencesDataProtoMapper::class])
class AppPreferencesDataProtoMapperImpl : AppPreferencesDataProtoMapper {

    override fun mapToDomain(proto: AppPreferencesProto) = with (proto) {
        AppPreferencesData(
            isFirstAppLaunch = isFirstAppLaunch,
            uriToOpen = uriToOpen
        )
    }

    override fun mapFromDomain(domain: AppPreferencesData): AppPreferencesProto = with (domain) {
        val builder = AppPreferencesProto.newBuilder()
            .setIsFirstAppLaunch(isFirstAppLaunch)

        if (domain.uriToOpen != null) {
            builder.setUriToOpen(domain.uriToOpen)
        }

        builder.build()
    }
}
