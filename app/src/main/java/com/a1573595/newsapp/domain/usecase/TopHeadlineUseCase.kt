package com.a1573595.newsapp.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import com.a1573595.newsapp.domain.model.Article
import com.a1573595.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TopHeadlineUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(): Flow<PagingData<Article>> = newsRepository.getTopHeadlineList()
        .map { pagingData -> pagingData.map { raw -> Article.fromRaw(raw) } }
        .flowOn(Dispatchers.IO)
}