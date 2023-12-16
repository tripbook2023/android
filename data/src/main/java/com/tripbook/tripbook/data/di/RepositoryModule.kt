package com.tripbook.tripbook.data.di

import com.tripbook.tripbook.data.repository.ArticleRepositoryImpl
import com.tripbook.tripbook.data.repository.AuthRepositoryImpl
import com.tripbook.tripbook.data.repository.CommonRepositoryImpl
import com.tripbook.tripbook.data.repository.LocationRepositoryImpl
import com.tripbook.tripbook.data.repository.MemberNoAuthRepositoryImpl
import com.tripbook.tripbook.data.repository.MemberRepositoryImpl
import com.tripbook.tripbook.data.repository.SearchRepositoryImpl
import com.tripbook.tripbook.domain.repository.ArticleRepository
import com.tripbook.tripbook.domain.repository.AuthRepository
import com.tripbook.tripbook.domain.repository.CommonRepository
import com.tripbook.tripbook.domain.repository.LocationRepository
import com.tripbook.tripbook.domain.repository.MemberNoAuthRepository
import com.tripbook.tripbook.domain.repository.MemberRepository
import com.tripbook.tripbook.domain.repository.SearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
@Suppress("UNUSED")
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindsAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    fun bindsMemberRepository(impl: MemberRepositoryImpl): MemberRepository

    @Binds
    @Singleton
    fun bindLocationRepository(impl: LocationRepositoryImpl): LocationRepository

    @Binds
    @Singleton
    fun bindsArticleRepository(impl: ArticleRepositoryImpl): ArticleRepository

    @Binds
    @Singleton
    fun bindsMemberNoAuthRepository(impl: MemberNoAuthRepositoryImpl): MemberNoAuthRepository

    @Binds
    @Singleton
    fun bindsCommonRepository(impl: CommonRepositoryImpl): CommonRepository

    @Binds
    @Singleton
    fun bindsSearchRepository(impl: SearchRepositoryImpl): SearchRepository
}