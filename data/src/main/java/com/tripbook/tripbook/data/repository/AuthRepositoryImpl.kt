package com.tripbook.tripbook.data.repository

import com.tripbook.database.TokenDataStore
import com.tripbook.database.TokenEntity
import com.tripbook.libs.network.NetworkResult
import com.tripbook.libs.network.safeApiCall
import com.tripbook.libs.network.service.AuthService
import com.tripbook.tripbook.data.mapper.toUserAuth
import com.tripbook.tripbook.domain.model.UserAuth
import com.tripbook.tripbook.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService,
    private val dataStore: TokenDataStore
) : AuthRepository {
    override fun validateToken(token: String): Flow<UserAuth?> =
        safeApiCall(Dispatchers.IO) {
            authService.validateToken("Bearer $token")
        }.map { response ->
            when (response) {
                is NetworkResult.Success -> {
                    response.value.toUserAuth()
                }
                else -> null
            }
        }

    override suspend fun setCurrentToken(accessToken: String, refreshToken: String) {
        dataStore.setToken(
            TokenEntity(accessToken, refreshToken)
        )
    }
}