package com.tripbook.libs.network.di.module

import com.squareup.moshi.Moshi
import com.tripbook.database.TokenDataStore
import com.tripbook.libs.network.di.qualifier.AuthNetworkQualifier
import com.tripbook.libs.network.di.qualifier.AuthServiceScope
import com.tripbook.libs.network.di.qualifier.LocationServiceScope
import com.tripbook.libs.network.di.qualifier.MemberServiceScope
import com.tripbook.libs.network.di.qualifier.NoAuthNetworkQualifier
import com.tripbook.libs.network.di.qualifier.NoAuthNetworkQualifierNoAgent
import com.tripbook.libs.network.di.qualifier.TokenServiceScope
import com.tripbook.libs.network.di.qualifier.TripNewsServiceScope
import com.tripbook.libs.network.interceptor.TokenInterceptor
import com.tripbook.libs.network.interceptor.UserAgentInterceptor
import com.tripbook.libs.network.service.TokenService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "http://13.124.98.251:9000"
    private const val KAKAO_MAP_URL = "https://dapi.kakao.com/"
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
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    @Provides
    @Singleton
    @NoAuthNetworkQualifierNoAgent
    fun providesNoAuthOkhttpClientNoAgent(): OkHttpClient = getBaseOkhttpBuilder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    @Provides
    @Singleton
    @AuthNetworkQualifier
    fun providesAuthOkhttpClient(
        tokenService: TokenService,
        dataStoreManager: TokenDataStore,
    ): OkHttpClient = getBaseOkhttpBuilder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
        .addInterceptor(UserAgentInterceptor())
        .addInterceptor(
            TokenInterceptor(
                tokenService,
                dataStoreManager
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
        .baseUrl("$BASE_URL/member/")
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    @Provides
    @Singleton
    @TripNewsServiceScope
    fun providesTripNewsRetrofit(
        moshi: Moshi,
        @AuthNetworkQualifier client: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl("$BASE_URL/")
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    @Provides
    @Singleton
    @LocationServiceScope
    fun providesKakaoMapRetrofit(
        moshi: Moshi,
        @NoAuthNetworkQualifierNoAgent client: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl("$KAKAO_MAP_URL/")
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    private fun getBaseOkhttpBuilder(): OkHttpClient.Builder = OkHttpClient.Builder()
        .callTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
}