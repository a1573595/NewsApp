package com.a1573595.newsapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.a1573595.newsapp.data.local.NewsDao
import com.a1573595.newsapp.data.network.NewsApi
import com.a1573595.newsapp.data.repository.paging.EverythingPagingSource
import com.a1573595.newsapp.data.repository.paging.TopHeadlinePagingSource
import com.a1573595.newsapp.domain.model.Article
import com.a1573595.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi,
    private val newsDao: NewsDao,
) : NewsRepository {
    override fun getTopHeadlineList(): Flow<PagingData<Article>> = Pager(
        config = PagingConfig(pageSize = 30, prefetchDistance = 2),
        pagingSourceFactory = {
            TopHeadlinePagingSource(newsApi = newsApi)
        }
    ).flow

    override fun getEverythingList(query: String): Flow<PagingData<Article>> = Pager(
        config = PagingConfig(pageSize = 30, prefetchDistance = 2),
        pagingSourceFactory = {
            EverythingPagingSource(
                newsApi = newsApi,
                query = query,
            )
        }
    ).flow

    override suspend fun getArticle(url: String): Article? = newsDao.getArticle(url)

    override fun getArticleList(): Flow<List<Article>> = newsDao.getArticleList()

    override suspend fun upsertArticle(article: Article) = newsDao.upsertArticle(article)

    override suspend fun deleteArticle(article: Article) = newsDao.deleteArticle(article)
}