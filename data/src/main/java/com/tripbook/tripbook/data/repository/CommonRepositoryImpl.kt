package com.tripbook.tripbook.data.repository

import com.tripbook.libs.network.NetworkResult
import com.tripbook.libs.network.safeApiCall
import com.tripbook.libs.network.service.CommonService
import com.tripbook.tripbook.data.mapper.toImage
import com.tripbook.tripbook.domain.model.Image
import com.tripbook.tripbook.domain.repository.CommonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class CommonRepositoryImpl @Inject constructor(
    private val service: CommonService
) : CommonRepository {
    override fun uploadImage(image: File): Flow<Image?> = safeApiCall(Dispatchers.IO){
        val imageTemp = image.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val imageBody = imageTemp.let {
            MultipartBody.Part.createFormData("image", "thumbnail.jpg", it)
        }
        service.uploadImage(
            imageBody,
            "BOARD_A" // 여행소식 게시글 이미지 업로드 시에만 해당 문자열 -> 다른 옵션은 없어서 일단 직접 넣어줌
        )
    }.map {
        when(it){
            is NetworkResult.Success -> it.value.toImage()
            else -> null
        }
    }
}