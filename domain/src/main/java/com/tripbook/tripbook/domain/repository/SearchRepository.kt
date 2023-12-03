package com.tripbook.tripbook.domain.repository

import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    fun getCurrentSearchHistory(): Flow<List<String>>

    fun addSearchKeyword(keyword: String): Flow<Boolean>

    fun deleteSearchKeyword(keyword: String): Flow<Boolean>

    fun clearSearchKeywords(): Flow<Boolean>
}