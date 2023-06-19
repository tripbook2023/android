package com.tripbook.tripbook.data.di

import com.tripbook.tripbook.data.repository.AuthRepositoryImpl
import com.tripbook.tripbook.data.repository.MemberRepositoryImpl
import com.tripbook.tripbook.domain.repository.AuthRepository
import com.tripbook.tripbook.domain.repository.MemberRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindsAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    fun bindsMemberRepository(impl: MemberRepositoryImpl): MemberRepository
}