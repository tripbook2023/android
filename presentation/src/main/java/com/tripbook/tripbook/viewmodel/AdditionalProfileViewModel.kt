package com.tripbook.tripbook.viewmodel

import android.view.View
import androidx.lifecycle.viewModelScope
import com.tripbook.base.BaseViewModel
import com.tripbook.tripbook.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class AdditionalProfileViewModel : BaseViewModel() {

    private var _femaleButton = MutableStateFlow(false)

    private var _maleButton = MutableStateFlow(false)

    val femaleButton: StateFlow<Boolean> get() = _femaleButton
    val maleButton: StateFlow<Boolean> get() = _maleButton

    private var year = MutableStateFlow(false)

    private var month = MutableStateFlow(false)

    private var day = MutableStateFlow(false)

    private var _initBirth = MutableStateFlow(true)

    val initBirth: StateFlow<Boolean> get() = _initBirth

    val allSpinnerChecked: StateFlow<Boolean> = combine(
        year, month, day
    ) { year, month, day ->
        year && month && day
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        false
    )

    fun setBirth(str: String) {
        when (str) {
            "year" ->  year.value = true
            "month" -> month.value = true
            "day" -> day.value = true
        }
        _initBirth.value = false
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