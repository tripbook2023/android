package com.tripbook.tripbook.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tripbook.tripbook.data.model.TermsURL
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class TermsViewModel : ViewModel() {

    //이용 동의 타이틀
    private var _title = MutableStateFlow("")
    val termsTitle: StateFlow<String> get() = _title

    //전체 동의
    private val _allTermsChecked = MutableStateFlow(false)
    val allTermsChecked: StateFlow<Boolean> get() = _allTermsChecked

    //서비스 동의
    val serviceChecked: MutableStateFlow<Boolean> = MutableStateFlow(false)

    //개인정보 동의
    val personalInfoChecked: MutableStateFlow<Boolean> = MutableStateFlow(false)

    //위치정보 동의
    val locationChecked: MutableStateFlow<Boolean> = MutableStateFlow(false)

    //마케팅 동의
    val marketingChecked: MutableStateFlow<Boolean> = MutableStateFlow(false)

    fun setTermsTitle(title: String) {
        _title.value = title.substring(5)
    }

    fun onAllTermsCheckedChanged(isChecked: Boolean) {
        _allTermsChecked.value = isChecked
        if (isChecked) {
            serviceChecked.update { true }
            personalInfoChecked.update { true }
            locationChecked.update { true }
            marketingChecked.update { true }
        } else {
            serviceChecked.update { false }
            personalInfoChecked.update { false }
            locationChecked.update { false }
            marketingChecked.update { false }
        }
    }

    val allItemsBtnChecked: StateFlow<Boolean> = combine(
        serviceChecked, personalInfoChecked, locationChecked
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
            "개인정보 수집 및 이용 동의" -> TermsURL(termsTitle, "https://www.youtube.com/")
            "위치정보수집 및 이용동의" -> TermsURL(
                termsTitle,
                "https://www.google.com/webhp?hl=ko&sa=X&ved=0ahUKEwiK9-vYnJ3_AhXOCd4KHaFFByoQPAgI"
            )

            else -> TermsURL(termsTitle, "https://www.daum.net/")
        }
    }
}