package com.tripbook.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesKeywordDatabase(@ApplicationContext context: Context): KeywordDatabase =
        Room.databaseBuilder(context, KeywordDatabase::class.java, "keyword.db").build()

    @Provides
    @Singleton
    fun providesSearchDao(database: KeywordDatabase) : SearchDao = database.searchDao()
}