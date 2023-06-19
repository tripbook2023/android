package com.tripbook.database

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataServiceModule {

    @Binds
    @Singleton
    fun providesTokenDataStore(
        impl: TokenDataStoreImpl
    ): TokenDataStore
}