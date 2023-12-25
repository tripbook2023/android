package com.tripbook.tripbook.data.repository


import com.tripbook.libs.network.NetworkResult
import com.tripbook.libs.network.safeApiCall
import com.tripbook.libs.network.service.MemberService
import com.tripbook.tripbook.data.mapper.toMemberInfo
import com.tripbook.tripbook.data.mapper.toTempArticle
import com.tripbook.tripbook.domain.model.MemberInfo
import com.tripbook.tripbook.domain.model.TempArticle
import com.tripbook.tripbook.domain.repository.MemberRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

class MemberRepositoryImpl @Inject constructor(
    private val memberService: MemberService
) : MemberRepository {


    override fun validateName(name: String): Flow<Boolean> = safeApiCall(Dispatchers.IO) {
        memberService.validateNickname(name)
    }.map {
        when (it) {
            is NetworkResult.Success -> {
                true
            }

            else -> false
        }
    }

    override fun getTempArticleList(): Flow<List<TempArticle>?> = safeApiCall(Dispatchers.IO) {
        memberService.getTempArticleList()
    }.map {
        when (it) {
            is NetworkResult.Success -> {
                it.value.map { article ->
                    article.toTempArticle()
                }
            }

            else -> null
        }
    }

    override fun getMember(): Flow<MemberInfo?> =
        safeApiCall(Dispatchers.IO) {
            memberService.getMember()
        }.map { response ->
            when (response) {
                is NetworkResult.Success -> {
                    response.value.toMemberInfo()
                }
                else -> null
            }
        }

    override fun updateMember(
        name: String?,
        file: File?,
        profile: String?,
        termsOfService: Boolean,
        termsOfPrivacy: Boolean,
        termsOfLocation: Boolean,
        marketingConsent: Boolean,
        gender: String,
        birth: String
    ): Flow<Boolean> = safeApiCall(Dispatchers.IO) {
        val params = mutableMapOf<String, RequestBody>()

        if (!name.isNullOrBlank()) {
            params["name"] = name.toRequestBody("text/plain".toMediaTypeOrNull())
        }

        val profileBody = profile?.toRequestBody("text/plain".toMediaTypeOrNull())?: "".toRequestBody("text/plain".toMediaTypeOrNull())
        val serviceTerms = termsOfService.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val privacyTerms = termsOfPrivacy.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val locationTerms = termsOfLocation.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val marketing = marketingConsent.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val genderBody = gender.toRequestBody("text/plain".toMediaTypeOrNull())
        val birthBody = birth.toRequestBody("text/plain".toMediaTypeOrNull())

        params["profile"] = profileBody
        params["termsOfService"] = serviceTerms
        params["termsOfPrivacy"] = privacyTerms
        params["termsOfLocation"] = locationTerms
        params["marketingConsent"] = marketing
        params["gender"] = genderBody
        params["birth"] = birthBody

        val fileBody = file?.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val filePart =
            fileBody?.let { MultipartBody.Part.createFormData("imageFile", "photo.jpg", it) }

        memberService.updateMember(filePart, params)
    }.map {
        when (it) {
            is NetworkResult.Success -> {
                true
            }
            else -> false
        }
    }

}