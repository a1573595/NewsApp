package com.a1573595.newsapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.a1573595.newsapp.data.model.ArticleRaw
import com.a1573595.newsapp.data.model.NewsResponse
import com.a1573595.newsapp.data.network.NewsApi
import com.a1573595.newsapp.data.repository.pagingSource.ArticleRawPagingSource
import com.a1573595.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi,
) : NewsRepository {
    override fun getTopHeadlines(): Flow<PagingData<ArticleRaw>> = Pager(
        config = PagingConfig(pageSize = 30, prefetchDistance = 2),
        pagingSourceFactory = {
            ArticleRawPagingSource(newsApi = newsApi)
        }
    ).flow

    override suspend fun getEverything(query: String, page: Int): Response<NewsResponse> =
        newsApi.getEverything(query = query, page = page)
}