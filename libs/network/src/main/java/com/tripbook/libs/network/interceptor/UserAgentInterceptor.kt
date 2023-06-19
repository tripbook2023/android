package com.tripbook.libs.network.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class UserAgentInterceptor : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response =
        chain.proceed(chain.request().addUserAgent())

    private fun Request.addUserAgent(): Request = newBuilder()
        .addHeader(USER_AGENT, "ANDROID_APP")
        .build()

    companion object {
        private const val USER_AGENT = "User-Agent"
    }
}