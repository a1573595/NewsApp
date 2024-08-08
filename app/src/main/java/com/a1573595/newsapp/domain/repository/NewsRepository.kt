package com.a1573595.newsapp.domain.repository

import androidx.paging.PagingData
import com.a1573595.newsapp.data.model.ArticleRaw
import com.a1573595.newsapp.data.model.NewsResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface NewsRepository {
    fun getTopHeadlines(): Flow<PagingData<ArticleRaw>>

    suspend fun getEverything(query: String, page: Int): Response<NewsResponse>
}