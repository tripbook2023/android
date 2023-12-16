package com.tripbook.tripbook.data.repository


import com.tripbook.database.TokenDataStore
import com.tripbook.database.TokenEntity
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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

class MemberRepositoryImpl @Inject constructor(
    private val memberService: MemberService,
    private val tokenDataStore: TokenDataStore
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

    override fun getTempArticleList():Flow<List<TempArticle>?> = safeApiCall(Dispatchers.IO){
        memberService.getTempArticleList()
    }.map {
        when(it){
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

    override fun updateMember (
        name: String,
        file: File?,
        termsOfService: Boolean,
        termsOfPrivacy: Boolean,
        termsOfLocation: Boolean,
        marketingConsent: Boolean,
        gender: String,
        birth: String
    ): Flow<Boolean> = safeApiCall(Dispatchers.IO) {

        val nameBody = name.toRequestBody("text/plain".toMediaTypeOrNull())
        val serviceTerms =
            termsOfService.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val privacyTerms =
            termsOfPrivacy.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val locationTerms =
            termsOfLocation.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val marketing =
            marketingConsent.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val genderBody = gender.toRequestBody("text/plain".toMediaTypeOrNull())
        val birthBody = birth.toRequestBody("text/plain".toMediaTypeOrNull())

        val fileBody = file?.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val filePart = fileBody?.let { MultipartBody.Part.createFormData("imageFile", "photo.jpg", it) }

        memberService.updateMember(
            filePart,
            mapOf(
                "name" to nameBody,
                "termsOfService" to serviceTerms,
                "termsOfPrivacy" to privacyTerms,
                "termsOfLocation" to locationTerms,
                "marketingConsent" to marketing,
                "gender" to genderBody,
                "birth" to birthBody
            )
        )
    }.map {
        when (it) {
            is NetworkResult.Success -> {
                tokenDataStore.setToken(
                    TokenEntity(
                        it.value.accessToken,
                        it.value.refreshToken
                    )
                ).collect()
                true
            }
            else -> run {
                tokenDataStore.setToken(null)
                false
            }
        }
    }


}