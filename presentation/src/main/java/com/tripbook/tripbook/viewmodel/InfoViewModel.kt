package com.tripbook.tripbook.viewmodel

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.tripbook.base.BaseViewModel
import com.tripbook.tripbook.R
import com.tripbook.tripbook.domain.model.MemberInfo
import com.tripbook.tripbook.domain.usecase.MemberUseCase
import com.tripbook.tripbook.domain.usecase.UpdateMemberUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class InfoViewModel @Inject constructor(
    private val memberUseCase: MemberUseCase,
    private val updateMemberUseCase : UpdateMemberUseCase
) : BaseViewModel() {

    private val _memberInfo: MutableStateFlow<MemberInfo?> = MutableStateFlow(null)
    val memberInfo: StateFlow<MemberInfo?> = _memberInfo

    private val _nickname: MutableStateFlow<String?> = MutableStateFlow(null)
    val nickname: StateFlow<String?> = _nickname

    private var _nicknameChecked = MutableStateFlow(false)
    val nicknameChecked: StateFlow<Boolean> get() = _nicknameChecked

    private val _email: MutableStateFlow<String?> = MutableStateFlow(null)
    val email: StateFlow<String?> = _email

    private val _version: MutableStateFlow<String?> = MutableStateFlow(null)
    val version: StateFlow<String?> = _version

    fun setProfileUri(uri: Uri?, fullPath: String?, default: Boolean) {
        uri?.let {
            _profileUri.value = it
        }
        fullPath?.let {
            profilePath.value = it
        }
        profileDefault.value = default

    }

    fun setVersion(ver: String){
        _version.value = ver
    }
    // 프로필 사진
    private val _profileUri = MutableStateFlow<Uri?>(null)
    val profileUri: StateFlow<Uri?> = _profileUri

    private val profilePath = MutableStateFlow<String?>("")

    private val profileDefault = MutableStateFlow(false)

    fun getMemberInformation(){
        viewModelScope.launch{
            memberUseCase().collect{
                _memberInfo.value = it
                it?.let{
                    _nickname.value = it.name
                    _email.value= it.email

                    _profileUri.value = if (it.profile != null) {
                        Uri.parse(it.profile)
                    } else {
                        Uri.parse("android.resource://com.tripbook.tripbook/" + R.drawable.tripbook_image)
                    }
                }
            }
        }
    }

    fun nickCheck(nick : String) {
        _nicknameChecked.value = _memberInfo.value?.name != nick
    }

    fun updateProfile(name: String, path: String?): Flow<Boolean> {
        val imageFile: File? = if (path == null) {
            null
        } else {
            File(path)
        }

        var profileChk : String? = null

        if(profileDefault.value) { //true -> 프로필 이미지 따로 있음
            profileChk = ""
        }

        val gender = memberInfo.value?.gender ?: ""
        val serviceChecked = memberInfo.value?.termsOfService ?: false
        val personalInfoChecked = memberInfo.value?.termsOfPrivacy ?: false
        val locationChecked = memberInfo.value?.termsOfLocation ?: false
        val marketingChecked = memberInfo.value?.marketingConsent ?: false
        val birth = memberInfo.value?.birth ?: ""

        return updateMemberUseCase(
            name,
            imageFile,
            profileChk,
            serviceChecked,
            personalInfoChecked,
            locationChecked,
            marketingChecked,
            gender,
            birth
        )
    }

}