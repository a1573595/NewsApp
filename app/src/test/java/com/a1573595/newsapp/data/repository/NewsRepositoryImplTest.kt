package com.a1573595.newsapp.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.map
import app.cash.turbine.test
import com.a1573595.newsapp.data.local.NewsDao
import com.a1573595.newsapp.data.model.NewsResponse
import com.a1573595.newsapp.data.network.NewsApi
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
        val fakeResponse: Response<NewsResponse> = mockk()
        val expectedResult = emptyList<Article>()

        coEvery { newsApi.getTopHeadlines(any(), any()) } returns fakeResponse

        newsRepository.getTopHeadlineList().test {
            val emission = awaitItem()

            val actualResult = mutableListOf<Article>()
            emission.map { article ->
                actualResult.add(article)
            }

            assertEquals(expectedResult, actualResult)
//            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `test getArticle`() = runTest {
        val expectedResult: Article = mockk()

        coEvery { newsDao.getArticle(any()) } returns expectedResult

        val actualResult = newsRepository.getArticle("")

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `test getArticleList`() {
        val expectedResult: Flow<List<Article>> = mockk()

        coEvery { newsDao.getArticleList() } returns expectedResult

        val actualResult = newsRepository.getArticleList()

        assertEquals(expectedResult, actualResult)
    }
}