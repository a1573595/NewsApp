package com.a1573595.newsapp.domain.usecase

import androidx.paging.PagingData
import com.a1573595.newsapp.domain.model.Article
import com.a1573595.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class EverythingUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(query: String): Flow<PagingData<Article>> = newsRepository.getEverythingList(query)
        .flowOn(Dispatchers.IO)
}