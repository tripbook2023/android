package com.tripbook.tripbook.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.tripbook.base.BaseViewModel
import com.tripbook.tripbook.domain.model.MemberInfo
import com.tripbook.tripbook.domain.usecase.MemberUseCase
import com.tripbook.tripbook.domain.usecase.UpdateMemberUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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

    private val _email: MutableStateFlow<String?> = MutableStateFlow(null)
    val email: StateFlow<String?> = _email

    private val _version: MutableStateFlow<String?> = MutableStateFlow(null)
    val version: StateFlow<String?> = _version

    // 프로필 사진
    private val _profileUri = MutableStateFlow<Uri?>(null)
    val profileUri: StateFlow<Uri?> = _profileUri

    private val profilePath = MutableStateFlow<String?>("")

    private val profileDefault = MutableStateFlow(false)

    fun getMemberInfo() = memberUseCase().onEach {
        _memberInfo.emit(it)

        _nickname.value = it?.name
        _email.value = it?.email

        val profileUri: Uri? = it?.profile?.let { Uri.parse(it) }
        _profileUri.emit(profileUri)

    }.launchIn(viewModelScope)

    fun updateProfile(name: String, imgFile: String?): Flow<Boolean> {
        val imageFile: File? = if (profileUri.value == null || profileDefault.value) {
            null
        } else {
            File(imgFile!!)
        }

        val gender = memberInfo.value?.gender ?: ""
        val serviceChecked = memberInfo.value?.termsOfService ?: false
        val personalInfoChecked = memberInfo.value?.termsOfPrivacy ?: false
        val locationChecked = memberInfo.value?.termsOfLocation ?: false
        val marketingChecked = memberInfo.value?.marketingConsent ?: false
        val birth = memberInfo.value?.birth ?: ""

        Log.d("profile change", "nickname::" + name)
        Log.d("profile change", "imageFile::" + imageFile)
        Log.d("profile change", "gender::" + gender)
        Log.d("profile change", "serviceChecked::" + serviceChecked)
        Log.d("profile change", "personalInfoChecked::" + personalInfoChecked)
        Log.d("profile change", "locationChecked::" + locationChecked)
        Log.d("profile change", "marketingChecked::" + marketingChecked)
        Log.d("profile change", "birth::" + birth)

        return updateMemberUseCase(
            name,
            imageFile,
            serviceChecked,
            personalInfoChecked,
            locationChecked,
            marketingChecked,
            gender,
            birth
        )
    }

}