package com.tripbook.tripbook.domain.repository

import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun validateToken(token: String): Flow<Boolean>
}