package com.tripbook.database

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val ACCESS_TOKEN_KEY = "access_token"
private const val REFRESH_TOKEN_KEY = "refresh_token"

class TokenDataStoreImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
): TokenDataStore {
    private val accessTokenKey = stringPreferencesKey(ACCESS_TOKEN_KEY)
    private val refreshTokenKey = stringPreferencesKey(REFRESH_TOKEN_KEY)

    private val accessTokenFlow: Flow<String?> = dataStore.data.map { preferences ->
        preferences[accessTokenKey]
    }
    private val refreshTokenFlow: Flow<String?> = dataStore.data.map { preferences ->
        preferences[refreshTokenKey]
    }

    override val tokenFlow: Flow<TokenEntity?>
        get() = combine(accessTokenFlow, refreshTokenFlow) { accessToken, refreshToken ->
            if (accessToken != null && refreshToken != null) {
                TokenEntity(accessToken, refreshToken)
            } else {
                null
            }
        }

    override fun setToken(tokenEntity: TokenEntity?): Flow<Boolean> = flow {
        try {
            dataStore.edit { preferences ->
                preferences[accessTokenKey] = tokenEntity?.accessToken ?: ""
                preferences[refreshTokenKey] = tokenEntity?.refreshToken ?: ""
            }.also {
                emit(true)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(false)
        }
    }
}