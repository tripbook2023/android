package com.tripbook.tripbook.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tripbook.tripbook.data.model.TermsURL
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

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
            setServiceChecked(true)
            setPersonalInfoChecked(true)
            setLocationChecked(true)
            setMarketingChecked(true)
        } else {
            setServiceChecked(false)
            setPersonalInfoChecked(false)
            setLocationChecked(false)
            setMarketingChecked(false)
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

    val allItemsBtnChecked : StateFlow<Boolean> = combine(
        _serviceChecked, _personalInfoChecked, _locationChecked
    ) { service, personalInfo, location ->
        service && personalInfo && location
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        false
    )
    //이용동의별 URL 가져오기
    fun getTermsURL(termsTitle: String): TermsURL {
        return when (termsTitle) {
            "서비스 이용약관 동의" -> TermsURL(termsTitle, "https://www.naver.com/")
            "개인정보 수집 및 이용 동의" -> TermsURL(termsTitle,"https://www.youtube.com/")
            "위치정보수집 및 이용동의" -> TermsURL(termsTitle, "https://www.google.com/webhp?hl=ko&sa=X&ved=0ahUKEwiK9-vYnJ3_AhXOCd4KHaFFByoQPAgI")
            else -> TermsURL(termsTitle,"https://www.daum.net/")
        }
    }
}