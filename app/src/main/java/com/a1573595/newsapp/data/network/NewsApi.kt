package com.a1573595.newsapp.data.network

import com.a1573595.newsapp.common.Constants
import com.a1573595.newsapp.data.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Query("sources") sources: String = "abc-news, bbc-news, cbs-news, cnn, fox-news",
        @Query("sortBy") sortBy: String? = "publishedAt",
        @Query("apiKey") apiKey: String = Constants.API_KEY,
    ): Response<NewsResponse>

    @GET("everything")
    suspend fun getEverything(
        @Query("q") query: String?,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Query("sources") sources: String = "abc-news, bbc-news, cbs-news, cnn, fox-news",
        @Query("sortBy") sortBy: String? = "publishedAt",
        @Query("apiKey") apiKey: String = Constants.API_KEY,
    ): Response<NewsResponse>
}