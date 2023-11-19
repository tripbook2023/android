package com.tripbook.tripbook.data.repository

import com.tripbook.libs.network.NetworkResult
import com.tripbook.libs.network.safeApiCall
import com.tripbook.libs.network.service.TripNewsService
import com.tripbook.tripbook.domain.repository.TripNewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import timber.log.Timber
import java.io.File
import javax.inject.Inject

// 여행소식과 관련된 기능 -> 저장, 조회 등 모두
class TripNewsRepositoryImpl @Inject constructor(
    private val tripNewsService: TripNewsService,
) : TripNewsRepository {

    override fun saveTripNews(
        title: String,
        content: String,
        thumbnail: File,
        imageList: List<File>,
        tagList: List<String>?
    ): Flow<Boolean> = safeApiCall(Dispatchers.IO) {
        val titleBody = title.toRequestBody("text/plain".toMediaTypeOrNull())
        val contentBody = content.toRequestBody("text/plain".toMediaTypeOrNull())
        val tagListBody: MutableList<RequestBody> = mutableListOf()

        val fileListBody: MutableList<MultipartBody.Part> = mutableListOf()
        imageList.mapIndexed { index, file ->
            val fileBody = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val filePart = fileBody.let {
                MultipartBody.Part.createFormData("imageList", "photo$index.jpg", it)
            }
            fileListBody.add(filePart)
        }

        tagList?.map {
            val tagBody = it.toRequestBody("text/plain".toMediaTypeOrNull())
            tagListBody.add(tagBody)
        }

        val thumbnailTemp = thumbnail.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val thumbnailBody = thumbnailTemp.let {
            it.let {
                MultipartBody.Part.createFormData("thumbnail", "thumbnail.jpg", it)
            }
        }

        tripNewsService.saveTripNews(
            mapOf(
                "title" to titleBody,
                "content" to contentBody
            ),
            thumbnailBody,
            fileListBody,
            tagListBody
        )
    }.map {
        when (it) {
            is NetworkResult.Success -> {
                Timber.tag("thumbnail").d(it.value.thumbnail.toString())
                Timber.tag("tagList").d(it.value.tagList.toString())
                Timber.tag("ImageList").d(it.value.imageList.toString())
                true
            }

            else -> {
                Timber.tag("TripNewsAdd Failure").d(it.toString())
                false
            }
        }
    }
}