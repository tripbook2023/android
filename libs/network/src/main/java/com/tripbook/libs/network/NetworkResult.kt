package com.tripbook.libs.network

sealed class NetworkResult<out T> {
    data class Success<out T>(val value: T) : NetworkResult<T>()
    data class GeneralError(
        val status: String? = null,
        val message: List<String>? = emptyList(),
        val code: Int? = 0
    ): NetworkResult<Nothing>()
    data object NetworkError : NetworkResult<Nothing>()
}