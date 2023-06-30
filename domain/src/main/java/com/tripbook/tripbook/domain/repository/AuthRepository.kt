package com.tripbook.tripbook.domain.repository

import com.tripbook.tripbook.domain.model.UserAuth
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun validateToken(token: String): Flow<UserAuth?>

    suspend fun setCurrentToken(accessToken: String, refreshToken: String)
}