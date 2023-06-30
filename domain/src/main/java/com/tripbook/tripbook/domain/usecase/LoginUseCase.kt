package com.tripbook.tripbook.domain.usecase

import com.tripbook.tripbook.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(accessToken: String): Flow<Boolean> = repository.validateToken(accessToken)
}