package com.tripbook.tripbook.data.repository

import com.tripbook.database.LatestSearchKeyword
import com.tripbook.database.SearchDao
import com.tripbook.tripbook.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val keywordDao: SearchDao
): SearchRepository {
    override fun getCurrentSearchHistory(): Flow<List<String>> = keywordDao.getAll()

    override fun addSearchKeyword(keyword: String): Flow<Boolean> = flow {
        keywordDao.insertItem(LatestSearchKeyword(keyword))
        emit(true)
    }.catch {
        it.printStackTrace()
        emit(false)
    }

    override fun deleteSearchKeyword(keyword: String): Flow<Boolean> = flow {
        keywordDao.deleteItemByKeyword(keyword)
        emit(true)
    }.catch {
        it.printStackTrace()
        emit(false)
    }

    override fun clearSearchKeywords(): Flow<Boolean> = flow {
        keywordDao.clearAllItemsInTable()
        emit(true)
    }.catch {
        it.printStackTrace()
        emit(false)
    }
}