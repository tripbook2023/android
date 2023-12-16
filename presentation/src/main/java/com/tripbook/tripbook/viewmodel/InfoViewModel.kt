package com.tripbook.tripbook.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.tripbook.base.BaseViewModel
import com.tripbook.tripbook.domain.model.MemberInfo
import com.tripbook.tripbook.domain.usecase.LogoutUseCase
import com.tripbook.tripbook.domain.usecase.MemberUseCase
import com.tripbook.tripbook.domain.usecase.UpdateMemberUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.io.File
import javax.inject.Inject

@HiltViewModel
class InfoViewModel @Inject constructor(
    memberUseCase: MemberUseCase,
    private val updateMemberUseCase : UpdateMemberUseCase,
    private val logoutUseCase: LogoutUseCase
) : BaseViewModel() {

    private val memberInfo: StateFlow<MemberInfo?> = memberUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    val nickname: StateFlow<String?> = memberInfo.filterNotNull().map {
        it.name
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ""
    )

    private var _nicknameChecked = MutableStateFlow(false)
    val nicknameChecked: StateFlow<Boolean> get() = _nicknameChecked

    val email: StateFlow<String?> = memberInfo.filterNotNull().map {
        it.email
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ""
    )

    val profileUrl: StateFlow<String?> = memberInfo.filterNotNull().map {
        it.profile ?: ""
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ""
    )

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

    // 프로필 사진
    private val _profileUri = MutableStateFlow<Uri?>(null)
    val profileUri: StateFlow<Uri?> = _profileUri

    private val profilePath = MutableStateFlow<String?>("")

    private val profileDefault = MutableStateFlow(false)

    fun nickCheck(nick : String) {
        _nicknameChecked.value = memberInfo.value?.name != nick
    }

    fun updateProfile(name: String, path: String?): Flow<Boolean> {
        val imageFile: File? = if (path == null) {
            null
        } else {
            File(path)
        }

        var profileChk : String? = null

        if(profileDefault.value) {
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

    fun logout() {
        viewModelScope.launch {
            logoutUseCase()
        }
    }

}