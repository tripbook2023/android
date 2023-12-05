package com.tripbook.tripbook.data.repository

import com.tripbook.libs.network.NetworkResult
import com.tripbook.libs.network.safeApiCall
import com.tripbook.libs.network.service.TripNewsService
import com.tripbook.tripbook.domain.repository.TripNewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class TripNewsRepositoryImpl @Inject constructor(
    private val tripArticlesService: TripNewsService,
) : TripNewsRepository {

    override fun saveTripNews(
        id: Long?,
        title: String,
        content: String,
        thumbnail: String,
        imageList: List<Int>,
        tagList: List<String>?
    ): Flow<Boolean> = safeApiCall(Dispatchers.IO) {
        val titleBody = title.toRequestBody("text/plain".toMediaTypeOrNull())
        val contentBody = content.toRequestBody("text/plain".toMediaTypeOrNull())
        val thumbnailBody = thumbnail.toRequestBody("text/plain".toMediaTypeOrNull())
        val tagListBody: MutableList<RequestBody> = mutableListOf()

        tagList?.map {
            val tagBody = it.toRequestBody("text/plain".toMediaTypeOrNull())
            tagListBody.add(tagBody)
        }

        tripArticlesService.saveTripNews(
            mapOf(
                "title" to titleBody,
                "content" to contentBody,
                "thumbnail" to thumbnailBody
            ),
            imageList,
            tagListBody,
            id
        )
    }.map {
        when (it) {
            is NetworkResult.Success -> {
                true
            }

            else -> {
                false
            }
        }
    }
}