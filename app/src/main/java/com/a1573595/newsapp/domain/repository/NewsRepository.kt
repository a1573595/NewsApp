package com.a1573595.newsapp.domain.repository

import androidx.paging.PagingData
import com.a1573595.newsapp.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getTopHeadlineList(): Flow<PagingData<Article>>

    fun getEverythingList(query: String): Flow<PagingData<Article>>

    suspend fun getArticle(url: String): Article?

    fun getArticleList(): Flow<List<Article>>

    suspend fun upsertArticle(article: Article)

    suspend fun deleteArticle(article: Article)
}