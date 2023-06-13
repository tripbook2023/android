package com.tripbook.tripbook.viewmodel

import android.view.View
import com.tripbook.base.BaseViewModel
import com.tripbook.tripbook.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AdditionalProfileViewModel : BaseViewModel() {

    private var _isAgeValid = MutableStateFlow(false)
    val isAgeValid: StateFlow<Boolean> get() = _isAgeValid

    private var _errorMsg = MutableStateFlow("")
    val errorMsg: StateFlow<String> get() = _errorMsg

    private var _femaleButton = MutableStateFlow(false)
    private var _maleButton = MutableStateFlow(false)

    val femaleButton: StateFlow<Boolean> get() = _femaleButton
    val maleButton: StateFlow<Boolean> get() = _maleButton

    private var _icon = MutableStateFlow(0)
    val icon: StateFlow<Int> get() = _icon

    fun setIcon(value: Int) {
        _icon.value = value
    }

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
}