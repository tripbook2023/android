package com.tripbook.tripbook.viewmodel

import androidx.lifecycle.viewModelScope
import com.tripbook.base.BaseViewModel
import com.tripbook.tripbook.domain.model.UserLoginStatus
import com.tripbook.tripbook.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
): BaseViewModel() {

    private val _isValidatedUser: MutableStateFlow<UserLoginStatus?> = MutableStateFlow(null)
    val isValidatedUser: StateFlow<UserLoginStatus?> = _isValidatedUser

    fun validateToken(accessToken: String) = loginUseCase(accessToken).onEach {
        _isValidatedUser.emit(it)
    }.launchIn(viewModelScope)
}