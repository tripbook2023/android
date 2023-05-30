package com.tripbook.tripbook.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TermsViewModel : ViewModel() {

    //이용 동의 타이틀
    private var _title = MutableStateFlow("")
    val termsTitle: StateFlow<String> get() = _title

    //전체 동의
    private val _allTermsChecked = MutableStateFlow(false)
    val allTermsChecked : StateFlow<Boolean> get() = _allTermsChecked

    //서비스 동의
    private val _serviceChecked  = MutableStateFlow(false)
    val serviceChecked  : StateFlow<Boolean> get() = _serviceChecked

    //개인정보 동의
    private val _personalInfoChecked  = MutableStateFlow(false)
    val personalInfoChecked  : StateFlow<Boolean> get() = _personalInfoChecked

    //위치정보 동의
    private val _locationChecked  = MutableStateFlow(false)
    val locationChecked  : StateFlow<Boolean> get() = _locationChecked

    //마케팅
    private val _marketingChecked  = MutableStateFlow(false)
    val marketingChecked  : StateFlow<Boolean> get() = _marketingChecked

    fun setTermsTitle(title : String) {
            _title.value = title.substring(5)
    }

    fun onAllTermsCheckedChanged(isChecked: Boolean) {
        _allTermsChecked.value = isChecked
        if (isChecked) {
            _serviceChecked.value = true
            _personalInfoChecked.value = true
            _locationChecked.value = true
            _marketingChecked.value = true
        } else {
            _serviceChecked.value = false
            _personalInfoChecked.value = false
            _locationChecked.value = false
            _marketingChecked.value = false
        }
    }

    fun setServiceChecked(isChecked: Boolean) {
        _serviceChecked.value = isChecked
    }

    fun setPersonalInfoChecked(isChecked: Boolean) {
        _personalInfoChecked.value = isChecked
    }

    fun setLocationChecked(isChecked: Boolean) {
        _locationChecked.value = isChecked
    }

    fun setMarketingChecked(isChecked: Boolean) {
        _marketingChecked.value = isChecked
    }

    fun areAllTermsChecked(): Boolean {
        return _serviceChecked.value && _personalInfoChecked.value && _locationChecked.value
    }

}