package com.a1573595.newsapp.data.repository

import com.a1573595.newsapp.data.model.NewsResponse
import com.a1573595.newsapp.data.network.NewsApi
import com.a1573595.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi,
) : NewsRepository {
    //    override suspend fun getTopHeadlines(country: String): Response<NewsResponse> = withContext(ioDispatcher) {
//        newsApi.getTopHeadlines(country)
//    }
    override suspend fun getTopHeadlines(page: Int): Response<NewsResponse> = newsApi.getTopHeadlines(page)

    override suspend fun getEverything(query: String, page: Int): Response<NewsResponse> =
        newsApi.getEverything(query = query, page = page)
}