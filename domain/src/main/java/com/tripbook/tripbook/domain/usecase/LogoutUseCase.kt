package com.tripbook.tripbook.domain.usecase

import com.tripbook.tripbook.domain.repository.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke() = authRepository.clearToken()
}