package com.a1573595.newsapp.domain.usecase

import com.a1573595.newsapp.domain.model.Article
import com.a1573595.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FavoriteUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(): Flow<List<Article>> = newsRepository.getArticleList()
        .flowOn(Dispatchers.IO)

    suspend fun getArticle(url: String) = newsRepository.getArticle(url)

    suspend fun upsertArticle(article: Article) = newsRepository.upsertArticle(article)

    suspend fun deleteArticle(article: Article) = newsRepository.deleteArticle(article)
}