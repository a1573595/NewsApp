package com.a1573595.newsapp.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.map
import app.cash.turbine.test
import com.a1573595.newsapp.data.local.NewsDao
import com.a1573595.newsapp.data.model.NewsResponse
import com.a1573595.newsapp.data.network.NewsApi
import com.a1573595.newsapp.data.repository.NewsRepositoryImpl
import com.a1573595.newsapp.domain.model.Article
import com.a1573595.newsapp.domain.repository.NewsRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

class NewsRepositoryImplTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var newsApi: NewsApi
    private lateinit var newsDao: NewsDao
    private lateinit var newsRepository: NewsRepository

    @Before
    fun setup() {
        newsApi = mockk()
        newsDao = mockk()
        newsRepository = NewsRepositoryImpl(newsApi, newsDao)
    }

    @Test
    fun `test getTopHeadlines`() = runTest {
        val page = 1
        val pageSize = 30
        val expectedResponse: Response<NewsResponse> = mockk()

        coEvery { newsApi.getTopHeadlines(page, pageSize) } returns expectedResponse

        val result = newsRepository.getTopHeadlineList()

        result.test {
            val emission = awaitItem()

            val collectedArticles = mutableListOf<Article>()
            emission.map { article ->
                collectedArticles.add(article)
            }

            assertEquals(emptyList<Article>(), collectedArticles)
//            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `test getArticle`() = runTest {
        val url = "testUrl"
        val expectedResponse: Article = mockk()

        coEvery { newsDao.getArticle(url) } returns expectedResponse

        val result = newsRepository.getArticle(url)

        assertEquals(expectedResponse, result)
    }

    @Test
    fun `test getArticleList`() {
        val expectedResponse: Flow<List<Article>> = mockk()

        coEvery { newsDao.getArticleList() } returns expectedResponse

        val result = newsRepository.getArticleList()

        assertEquals(expectedResponse, result)
    }
}