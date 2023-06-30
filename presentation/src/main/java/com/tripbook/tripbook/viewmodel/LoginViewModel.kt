package com.tripbook.tripbook.viewmodel

import androidx.lifecycle.viewModelScope
import com.tripbook.base.BaseViewModel
import com.tripbook.tripbook.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
): BaseViewModel() {

    private val _isValidatedUser: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val isValidatedUser: StateFlow<Boolean?> = _isValidatedUser

    fun validateToken(accessToken: String) = loginUseCase(accessToken).onEach {
        _isValidatedUser.update { it }
    }.launchIn(viewModelScope)
}