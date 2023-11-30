package com.tripbook.tripbook.data.repository

import com.tripbook.database.TokenDataStore
import com.tripbook.database.TokenEntity
import com.tripbook.libs.network.NetworkResult
import com.tripbook.libs.network.safeApiCall
import com.tripbook.libs.network.service.MemberNoAuthService
import com.tripbook.tripbook.domain.repository.MemberNoAuthRepository
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

class MemberNoAuthRepositoryImpl @Inject constructor(
    private val memberNoAuthService: MemberNoAuthService,
    private val tokenDataStore: TokenDataStore
) : MemberNoAuthRepository {

    override fun signUp(
        name: String,
        email: String,
        file: File?,
        termsOfService: Boolean,
        termsOfPrivacy: Boolean,
        termsOfLocation: Boolean,
        marketingConsent: Boolean,
        gender: String,
        birth: String
    ): Flow<Boolean> = safeApiCall(Dispatchers.IO) {
        val nameBody = name.toRequestBody("text/plain".toMediaTypeOrNull())
        val emailBody = email.toRequestBody("text/plain".toMediaTypeOrNull())
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

        memberNoAuthService.signUp(
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