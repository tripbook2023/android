package com.tripbook.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LatestSearchKeyword::class], version = 1)
abstract class KeywordDatabase : RoomDatabase() {
    abstract fun searchDao(): SearchDao
}