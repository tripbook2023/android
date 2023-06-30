package com.tripbook.libs.network

import android.util.Log
import com.squareup.moshi.Moshi
import com.tripbook.libs.network.model.response.UnitResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception

fun <T: Any> safeApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T
): Flow<NetworkResult<T>> =
    flow {
        try {
            val response = apiCall.invoke()
            Log.d("MyTagResponse", response.toString())
            emit(NetworkResult.Success(response))
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            when (throwable) {
                is IOException -> emit(NetworkResult.NetworkError)
                is HttpException -> {
                    val errorResponse = convertErrorBody(throwable)
                    emit(
                        NetworkResult.GeneralError(
                            errorResponse?.status,
                            errorResponse?.message,
                            errorResponse?.code
                        )
                    )
                }

                else -> {
                    NetworkResult.GeneralError(null, null, null)
                }
            }
        }
    }.flowOn(dispatcher)


private fun convertErrorBody(throwable: HttpException): UnitResponse? =
    try {
        throwable.response()?.errorBody()?.source()?.let {
            val moshiAdapter = Moshi.Builder().build().adapter(UnitResponse::class.java)
            moshiAdapter.fromJson(it)
        }
    } catch (exception: Exception) {
        null
    }
