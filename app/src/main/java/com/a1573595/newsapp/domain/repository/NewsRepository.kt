package com.a1573595.newsapp.domain.repository

import androidx.paging.PagingData
import com.a1573595.newsapp.data.model.ArticleRaw
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getTopHeadlineList(): Flow<PagingData<ArticleRaw>>

    fun getEverythingList(query: String): Flow<PagingData<ArticleRaw>>
}