package com.tripbook.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchDao {
    @Query("SELECT keyword FROM LatestSearchKeyword ORDER BY searchedAt")
    fun getAll(): Flow<List<String>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(searchKeyword: LatestSearchKeyword)

    @Query("DELETE FROM LatestSearchKeyword WHERE keyword = :keyword")
    suspend fun deleteItemByKeyword(keyword: String)

    @Query("DELETE FROM LatestSearchKeyword")
    suspend fun clearAllItemsInTable()
}