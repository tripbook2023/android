package com.tripbook.tripbook.data.repository

import com.tripbook.libs.network.NetworkResult
import com.tripbook.libs.network.safeApiCall
import com.tripbook.libs.network.service.AuthService
import com.tripbook.tripbook.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService,
) : AuthRepository {
    override fun validateToken(token: String): Flow<Boolean> =
        safeApiCall(Dispatchers.IO) { authService.validateToken() }.map {
            when (it) {
                is NetworkResult.Success -> {
                    true
                }
                else -> false
            }
        }
}