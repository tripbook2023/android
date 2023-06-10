package com.tripbook.tripbook.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProfileViewModel : ViewModel() {

    private val _isNicknameValid = MutableStateFlow(false)
    val isNicknameValid: StateFlow<Boolean> = _isNicknameValid

    private val _isProfileValid = MutableStateFlow(false)
    val isProfileValid: StateFlow<Boolean> = _isProfileValid

    private var uri: Uri = Uri.parse("android.resource://com.tripbook.tripbook/drawable/ic_picture")
    private val _profileUri = MutableStateFlow(uri)
    val profileUri: StateFlow<Uri> = _profileUri

    private var _nickname = MutableStateFlow("")
    val nickname: StateFlow<String> get() = _nickname

    private var _errorMsg = MutableStateFlow("")
    val errorMsg: StateFlow<String> get() = _errorMsg

    private var _isKeyboardUp = MutableStateFlow(false)
    val isKeyboardUp: StateFlow<Boolean> get() = _isKeyboardUp

    fun setNicknameValid(errorMsg: String?) {
        errorMsg?.let { str ->
            _errorMsg.value = str
            _isNicknameValid.value = false
        } ?: run {
            _errorMsg.value = ""
            _isNicknameValid.value = true
        }
    }

    fun setProfileUri(uri: Uri?) {
        uri?.let {
            _profileUri.value = it
            _isProfileValid.value = true
        } ?: run {
            _isProfileValid.value = false
        }
    }

    fun setNickname(nickname: String) {
        _nickname.value = nickname
    }

    fun setKeyboard(up: Boolean) {
        _isKeyboardUp.value = up
    }
}