package com.firmfreez.app.common.data.data_store.mappers

import com.firmfreez.app.common.domain.models.data_store.AppPreferencesData
import com.firmfreez.app.datastore.AppPreferences as AppPreferencesProto

interface AppPreferencesDataProtoMapper {

    fun mapToDomain(proto: AppPreferencesProto): AppPreferencesData
    fun mapFromDomain(domain: AppPreferencesData): AppPreferencesProto
}
