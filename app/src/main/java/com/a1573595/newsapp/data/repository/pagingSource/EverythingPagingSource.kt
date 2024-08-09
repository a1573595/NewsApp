package com.a1573595.newsapp.data.repository.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.a1573595.newsapp.data.model.ArticleRaw
import com.a1573595.newsapp.data.network.NewsApi

class EverythingPagingSource(
    private val newsApi: NewsApi,
    private val query: String,
) : PagingSource<Int, ArticleRaw>() {
    override fun getRefreshKey(state: PagingState<Int, ArticleRaw>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleRaw> {
        return try {
            val page = params.key ?: 1
            val pageSize = params.loadSize

            val respond = newsApi.getEverything(query = query, page = page, pageSize = pageSize)

            if (respond.isSuccessful) {
                val articleList = respond.body()?.articles ?: emptyList()

                LoadResult.Page(
                    data = articleList,
                    nextKey = if (articleList.isNotEmpty()) page + 1 else null,
                    prevKey = if (page != 1) page - 1 else null
                )
            } else {
                LoadResult.Error(Exception(respond.errorBody()?.string()))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}