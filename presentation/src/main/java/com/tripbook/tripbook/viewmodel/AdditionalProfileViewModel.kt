package com.tripbook.tripbook.viewmodel

import android.view.View
import androidx.lifecycle.ViewModel
import com.tripbook.tripbook.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AdditionalProfileViewModel : ViewModel() {

    private var _isAgeValid = MutableStateFlow(false)
    val isAgeValid: StateFlow<Boolean> = _isAgeValid

    private var _errorMsg = MutableStateFlow("")
    val errorMsg: StateFlow<String> get() = _errorMsg

    private var _isKeyboardUp = MutableStateFlow(false)
    val isKeyboardUp: StateFlow<Boolean> get() = _isKeyboardUp

    private var _femaleButton = MutableStateFlow(false)
    private var _maleButton = MutableStateFlow(false)

    val femaleButton: StateFlow<Boolean> get() = _femaleButton
    val maleButton: StateFlow<Boolean> get() = _maleButton

    private var _icon = MutableStateFlow(R.drawable.ic_transparent)
    val icon: StateFlow<Int> get() = _icon

    fun setAgeValid(errorMsg: String?) {
        errorMsg?.let { str ->
            _errorMsg.value = str
            _isAgeValid.value = false
            _icon.value = R.drawable.ic_clear
        } ?: run {
            _errorMsg.value = ""
            _isAgeValid.value = true
            _icon.value = R.drawable.ic_check
        }
    }

    fun buttonClicked(view: View) {
        when (view.id) {
            R.id.button_female -> {
                if (_maleButton.value) _maleButton.value = !_maleButton.value
                _femaleButton.value = !_femaleButton.value
            }

            R.id.button_male -> {
                if (_femaleButton.value) _femaleButton.value = !_femaleButton.value
                _maleButton.value = !_maleButton.value
            }
        }
    }

    fun setKeyboard(up: Boolean) {
        _isKeyboardUp.value = up
    }
}