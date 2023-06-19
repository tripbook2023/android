package com.tripbook.database

import kotlinx.coroutines.flow.Flow

interface TokenDataStore {
    val tokenFlow: Flow<TokenEntity?>

    fun setToken(
        tokenEntity: TokenEntity?
    ): Flow<Boolean>
}