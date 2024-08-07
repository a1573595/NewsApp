package com.a1573595.newsapp.domain.repository

import com.a1573595.newsapp.data.model.NewsResponse
import retrofit2.Response

interface NewsRepository {
    suspend fun getTopHeadlines(page: Int): Response<NewsResponse>

    suspend fun getEverything(query: String, page: Int): Response<NewsResponse>
}