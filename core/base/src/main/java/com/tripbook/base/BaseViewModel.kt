package com.tripbook.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel: ViewModel() {

    private var _isKeyboardUp = MutableStateFlow(false)
    val isKeyboardUp: StateFlow<Boolean> get() = _isKeyboardUp

    fun setKeyboard(up: Boolean) {
        _isKeyboardUp.value = up
    }
}