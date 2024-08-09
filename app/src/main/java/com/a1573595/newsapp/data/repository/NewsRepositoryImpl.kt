package com.a1573595.newsapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.a1573595.newsapp.data.model.ArticleRaw
import com.a1573595.newsapp.data.network.NewsApi
import com.a1573595.newsapp.data.repository.pagingSource.EverythingPagingSource
import com.a1573595.newsapp.data.repository.pagingSource.TopHeadlinePagingSource
import com.a1573595.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi,
) : NewsRepository {
    override fun getTopHeadlineList(): Flow<PagingData<ArticleRaw>> = Pager(
        config = PagingConfig(pageSize = 30, prefetchDistance = 2),
        pagingSourceFactory = {
            TopHeadlinePagingSource(newsApi = newsApi)
        }
    ).flow

    override fun getEverythingList(query: String): Flow<PagingData<ArticleRaw>> = Pager(
        config = PagingConfig(pageSize = 30, prefetchDistance = 2),
        pagingSourceFactory = {
            EverythingPagingSource(
                newsApi = newsApi,
                query = query,
            )
        }
    ).flow
}