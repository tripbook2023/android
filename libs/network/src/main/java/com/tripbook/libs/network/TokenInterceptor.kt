package com.tripbook.libs.network

import com.tripbook.database.DataStoreManager
import com.tripbook.database.Token
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class TokenInterceptor @Inject constructor(
    private val tokenService: TokenService,
    private val dataStoreManager: DataStoreManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = runBlocking {
        val token = dataStoreManager.tokenFlow.first()
        val tokenAddedRequest = chain.request().putAuthorizationHeader(token)

        val response = chain.proceed(tokenAddedRequest)
        return@runBlocking when (response.code()) {
            401, 500 -> {
                val refreshedToken = tokenService.refreshToken().toToken()
                val refreshRequest = chain.request().putAuthorizationHeader(refreshedToken)
                chain.proceed(refreshRequest).also {
                    if (it.isSuccessful) {
                        dataStoreManager.setToken(refreshedToken)
                    }
                }
            }
            else -> {
                response
            }
        }
    }

    private fun Request.putAuthorizationHeader(token: Token): Request = this.newBuilder()
        .addHeader(AUTHORIZATION, token.accessToken)
        .build()

    companion object {
        private const val AUTHORIZATION = "authorization"
    }

}