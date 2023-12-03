package com.tripbook.tripbook.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tripbook.libs.network.NetworkResult
import com.tripbook.libs.network.safeApiCall
import com.tripbook.libs.network.service.TripArticlesService
import com.tripbook.tripbook.domain.model.ArticleDetail
import com.tripbook.tripbook.domain.model.SortType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import toArticleDetail
import java.lang.Exception
import java.lang.IllegalStateException
import javax.inject.Inject

class ArticlePagingSource @Inject constructor(
    private val keyword: String,
    private val sortType: SortType,
    private val network: TripArticlesService
) : PagingSource<Int, ArticleDetail>() {
    override fun getRefreshKey(state: PagingState<Int, ArticleDetail>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            val page = state.closestPageToPosition(anchorPosition)
            page?.prevKey?.plus(1) ?: page?.nextKey?.minus(1)
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleDetail> = try {
        val nextPageParams = params.key ?: 1
        val response = safeApiCall(Dispatchers.IO) {
            network.getArticleList(
                word = keyword,
                nextPageParams,
                size = params.loadSize,
                sort = sortType.name
            )
        }.first()
        when(response) {
            is NetworkResult.Success -> LoadResult.Page(
                data = response.value.content.map { it.toArticleDetail() },
                prevKey = null,
                nextKey = response.value.number + 1
            )
            else -> LoadResult.Error(IllegalStateException("MUST BE NOT HERE."))
        }


    } catch (exception: Exception) {
        exception.printStackTrace()
        LoadResult.Error(exception)
    }
}