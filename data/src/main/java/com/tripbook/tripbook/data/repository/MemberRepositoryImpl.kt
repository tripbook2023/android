package com.tripbook.tripbook.data.repository

import com.tripbook.database.TokenDataStore
import com.tripbook.database.TokenEntity
import com.tripbook.libs.network.NetworkResult
import com.tripbook.libs.network.safeApiCall
import com.tripbook.libs.network.service.MemberService
import com.tripbook.tripbook.domain.repository.MemberRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

class MemberRepositoryImpl @Inject constructor(
    private val memberService: MemberService,
    private val tokenDataStore: TokenDataStore
) : MemberRepository {
    override fun signUp(
        name: String,
        email: String,
        file: File,
        termsOfService: Boolean,
        termsOfPrivacy: Boolean,
        termsOfLocation: Boolean,
        marketingConsent: Boolean,
        gender: String,
        birth: String
    ): Flow<Boolean> = safeApiCall(Dispatchers.IO) {
        val nameBody = RequestBody.create(MediaType.parse("text/plain"), name)
        val emailBody = RequestBody.create(MediaType.parse("text/plain"), email)
        val serviceTerms =
            RequestBody.create(MediaType.parse("text/plain"), termsOfService.toString())
        val privacyTerms =
            RequestBody.create(MediaType.parse("text/plain"), termsOfPrivacy.toString())
        val locationTerms =
            RequestBody.create(MediaType.parse("text/plain"), termsOfLocation.toString())
        val marketing =
            RequestBody.create(MediaType.parse("text/plain"), marketingConsent.toString())
        val genderBody = RequestBody.create(MediaType.parse("text/plain"), gender)
        val birthBody = RequestBody.create(MediaType.parse("text/plain"), birth)

        val fileBody = RequestBody.create(MediaType.parse("image/jpeg"), file)
        val filePart = MultipartBody.Part.createFormData("photo", "photo.jpg", fileBody)

        memberService.signUp(
            filePart,
            mapOf(
                "name" to nameBody,
                "email" to emailBody,
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
                )
                true
            }
            else -> run {
                tokenDataStore.setToken(null)
                false
            }
        }
    }

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
}