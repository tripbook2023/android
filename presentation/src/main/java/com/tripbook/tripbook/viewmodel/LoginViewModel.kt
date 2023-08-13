package com.tripbook.tripbook.viewmodel

import android.net.Uri
import android.view.View
import androidx.lifecycle.viewModelScope
import com.tripbook.base.BaseViewModel
import com.tripbook.tripbook.R
import com.tripbook.tripbook.data.model.TermsURL
import com.tripbook.tripbook.domain.model.UserLoginStatus
import com.tripbook.tripbook.domain.usecase.LoginUseCase
import com.tripbook.tripbook.domain.usecase.SignUpUseCase
import com.tripbook.tripbook.domain.usecase.ValidateUserNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import java.io.File
import javax.inject.Inject


enum class Gender {
    FEMALE, MALE
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val validateUserNameUseCase: ValidateUserNameUseCase,
    private val signUpUseCase: SignUpUseCase
) : BaseViewModel() {

    // 토큰 확인
    private val _isValidatedUser: MutableStateFlow<UserLoginStatus?> = MutableStateFlow(null)
    val isValidatedUser: StateFlow<UserLoginStatus?> = _isValidatedUser

    // 이용약관 관련
    private var _title = MutableStateFlow("")
    val termsTitle: StateFlow<String> get() = _title

    private val _allTermsChecked = MutableStateFlow(false) //전체 동의
    val allTermsChecked: StateFlow<Boolean> get() = _allTermsChecked

    val serviceChecked: MutableStateFlow<Boolean> = MutableStateFlow(false)  //서비스 동의

    val personalInfoChecked: MutableStateFlow<Boolean> = MutableStateFlow(false) //개인정보 동의

    val locationChecked: MutableStateFlow<Boolean> = MutableStateFlow(false) //위치정보 동의

    val marketingChecked: MutableStateFlow<Boolean> = MutableStateFlow(false) //마케팅 동의

    val allItemsBtnChecked: StateFlow<Boolean> = combine(
        serviceChecked, personalInfoChecked, locationChecked
    ) { service, personalInfo, location ->
        service && personalInfo && location
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        false
    )

    // 닉네임
    private val _isNicknameValid = MutableStateFlow(false)
    val isNicknameValid: StateFlow<Boolean> = _isNicknameValid

    private var _nickname = MutableStateFlow("")
    val nickname: StateFlow<String> get() = _nickname

    private var _errorMsg = MutableStateFlow("")
    val errorMsg: StateFlow<String> get() = _errorMsg

    // 프로필 사진
    private val _profileUri = MutableStateFlow<Uri?>(null)
    val profileUri: StateFlow<Uri?> = _profileUri

    private val profilePath = MutableStateFlow<String?>("")

    private val profileDefault = MutableStateFlow(false)

    // 추가정보 기입
    private var _femaleButton = MutableStateFlow(false)
    val femaleButton: StateFlow<Boolean> get() = _femaleButton

    private var _maleButton = MutableStateFlow(false)
    val maleButton: StateFlow<Boolean> get() = _maleButton

    private var birth = MutableStateFlow("")

    private val _isBirthValid = MutableStateFlow(false)
    val isBirthValid: StateFlow<Boolean> get() = _isBirthValid

    // 회원가입
    fun signUp(): Flow<Boolean> {
        val gender = if (femaleButton.value) Gender.FEMALE.name else Gender.MALE.name
        val imageFile: File? = if (profileUri.value == null || profileDefault.value) {
            null
        } else {
            profilePath.value?.let { File(it) }
        }

        return signUpUseCase(
            nickname.value,
            loginUseCase.email.value,
            imageFile,
            serviceChecked.value,
            personalInfoChecked.value,
            locationChecked.value,
            marketingChecked.value,
            gender,
            birth.value
        )
    }

    fun validateToken(accessToken: String) = loginUseCase(accessToken).onEach {
        _isValidatedUser.emit(it)
    }.launchIn(viewModelScope)

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

    fun validateUserName(name: String) = validateUserNameUseCase(name)

    fun setNicknameValid(errorMsg: String?) {
        errorMsg?.let { str ->
            _errorMsg.value = str
            _isNicknameValid.value = false
        } ?: run {
            _errorMsg.value = ""
            _isNicknameValid.value = true
        }
    }

    fun setNickname(nickname: String) {
        _nickname.value = nickname
    }

    fun setProfileUri(uri: Uri?, fullPath: String?, default: Boolean) {
        uri?.let {
            _profileUri.value = it
        }
        fullPath?.let {
            profilePath.value = it
        }
        profileDefault.value = default
    }

    fun setBirthday(str: String) {
        birth.value = str
        _isBirthValid.value = true
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