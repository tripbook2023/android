package com.tripbook.database

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val ACCESS_TOKEN_KEY = "access_token"
private const val REFRESH_TOKEN_KEY = "refresh_token"

class DataStoreManager @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    private val accessTokenKey = stringPreferencesKey(ACCESS_TOKEN_KEY)
    private val refreshTokenKey = stringPreferencesKey(REFRESH_TOKEN_KEY)

    private val accessTokenFlow: Flow<String> = dataStore.data.map { preferences ->
        preferences[accessTokenKey] ?: ""
    }
    private val refreshTokenFlow: Flow<String> = dataStore.data.map { preferences ->
        preferences[refreshTokenKey] ?: ""
    }

    val tokenFlow: Flow<Token> =
        combine(accessTokenFlow, refreshTokenFlow) { accessToken, refreshToken ->
            Token(accessToken, refreshToken)
        }

    suspend fun setToken(token: Token) = dataStore.edit { preferences ->
        preferences[accessTokenKey] = token.accessToken
        preferences[refreshTokenKey] = token.refreshToken
    }
}

