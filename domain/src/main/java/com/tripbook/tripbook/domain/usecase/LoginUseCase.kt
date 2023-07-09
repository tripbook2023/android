package com.tripbook.tripbook.domain.usecase

import com.tripbook.tripbook.domain.model.UserLoginStatus
import com.tripbook.tripbook.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> get() = _email
    operator fun invoke(accessToken: String): Flow<UserLoginStatus> = repository.validateToken(accessToken).map {
        it?.let {
            if (it.accessToken != null && it.refreshToken != null) {
                repository.setCurrentToken(it.accessToken, it.refreshToken)
            }
            _email.value = it.email
            it.status
        } ?: UserLoginStatus.STATUS_REQUIRED_AUTH
    }
}