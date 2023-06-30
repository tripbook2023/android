package com.tripbook.libs.network.di

import com.squareup.moshi.Moshi
import com.tripbook.database.DataStoreManager
import com.tripbook.libs.network.AuthNetworkQualifier
import com.tripbook.libs.network.AuthServiceScope
import com.tripbook.libs.network.MemberServiceScope
import com.tripbook.libs.network.NoAuthNetworkQualifier
import com.tripbook.libs.network.TokenServiceScope
import com.tripbook.libs.network.interceptor.TokenInterceptor
import com.tripbook.libs.network.interceptor.UserAgentInterceptor
import com.tripbook.libs.network.service.TokenService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "http://152.69.235.171:9000"
    // FIXME: 서버 도메인 변경 시 같이 변경이 필요합니다!

    @Provides
    @Singleton
    fun providesMoshi(): Moshi = Moshi.Builder()
        .build()

    @Provides
    @Singleton
    @NoAuthNetworkQualifier
    fun providesNoAuthOkhttpClient(): OkHttpClient = getBaseOkhttpBuilder()
        .addInterceptor(UserAgentInterceptor())
        .build()

    @Provides
    @Singleton
    @AuthNetworkQualifier
    fun providesAuthOkhttpClient(
        tokenService: TokenService,
        dataStoreManager: DataStoreManager,
    ): OkHttpClient = getBaseOkhttpBuilder()
        .addInterceptor(UserAgentInterceptor())
        .addInterceptor(
            TokenInterceptor(
                tokenService,
                dataStoreManager,
            )
        )
        .build()


    @Provides
    @Singleton
    @TokenServiceScope
    fun providesTokenRetrofit(
        moshi: Moshi,
        @NoAuthNetworkQualifier client: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl("$BASE_URL/token/")
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    @Provides
    @Singleton
    @AuthServiceScope
    fun providesAuthRetrofit(
        moshi: Moshi,
        @NoAuthNetworkQualifier client: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl("$BASE_URL/login/")
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    @Provides
    @Singleton
    @MemberServiceScope
    fun providesMemberRetrofit(
        moshi: Moshi,
        @NoAuthNetworkQualifier client: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl("${BASE_URL}/member/")
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()


    private fun getBaseOkhttpBuilder(): OkHttpClient.Builder = OkHttpClient.Builder()
        .callTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
}