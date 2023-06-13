package com.tripbook.tripbook.viewmodel

import android.net.Uri
import com.tripbook.base.BaseViewModel
import com.tripbook.tripbook.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProfileViewModel : BaseViewModel() {

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

    private var _icon = MutableStateFlow(0)
    val icon: StateFlow<Int> get() = _icon

    fun setIcon(value: Int) {
        _icon.value = value
    }

    fun setNicknameValid(errorMsg: String?) {
        _icon.value = R.drawable.ic_clear
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

}