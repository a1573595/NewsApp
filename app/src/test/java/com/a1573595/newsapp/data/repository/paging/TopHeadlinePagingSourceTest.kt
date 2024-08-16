package com.a1573595.newsapp.data.repository.paging

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingSource
import com.a1573595.newsapp.data.model.ArticleRaw
import com.a1573595.newsapp.data.model.NewsResponse
import com.a1573595.newsapp.data.model.fakeArticleRaw
import com.a1573595.newsapp.data.model.fakeNewsResponse
import com.a1573595.newsapp.data.network.NewsApi
import com.a1573595.newsapp.domain.model.Article
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

/// Reference https://medium.com/@mohamed.gamal.elsayed/android-how-to-test-paging-3-pagingsource-433251ade028
@ExperimentalCoroutinesApi
class TopHeadlinePagingSourceTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var newsApi: NewsApi

    private lateinit var topHeadlinePagingSource: TopHeadlinePagingSource

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        topHeadlinePagingSource = TopHeadlinePagingSource(newsApi)
    }

    @Test
    fun `paging source refresh throw exception`() = runBlocking {
        val exception = RuntimeException("404", Throwable())
        val expectedResult = PagingSource.LoadResult.Error<Int, Article>(exception)

        coEvery { newsApi.getTopHeadlines(any(), any()) } throws exception

        val actualResult = topHeadlinePagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 0,
                loadSize = 1,
                placeholdersEnabled = false,
            )
        )

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `paging source refresh Success`() = runBlocking {
        val fakeArticle = Article.fromRaw(fakeArticleRaw)
        val expectedResult = PagingSource.LoadResult.Page(
            data = listOf(fakeArticle),
            prevKey = -1,
            nextKey = 1,
        )

        coEvery { newsApi.getTopHeadlines(any(), any()) } returns Response.success(fakeNewsResponse)

        val actualResult = topHeadlinePagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 0,
                loadSize = 1,
                placeholdersEnabled = false,
            )
        )

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `paging source prepend Success`() = runBlocking {
        val fakeArticle = Article.fromRaw(fakeArticleRaw)
        val expectedResult = PagingSource.LoadResult.Page(
            data = listOf(fakeArticle),
            prevKey = -1,
            nextKey = 1,
        )

        coEvery { newsApi.getTopHeadlines(any(), any()) } returns Response.success(fakeNewsResponse)

        val actualResult = topHeadlinePagingSource.load(
            PagingSource.LoadParams.Prepend(
                key = 0,
                loadSize = 1,
                placeholdersEnabled = false,
            )
        )

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `paging source append Success`() = runBlocking {
        val fakeArticle = Article.fromRaw(fakeArticleRaw)
        val expectedResult = PagingSource.LoadResult.Page(
            data = listOf(fakeArticle),
            prevKey = 1,
            nextKey = 3,
        )

        coEvery { newsApi.getTopHeadlines(any(), any()) } returns Response.success(fakeNewsResponse)

        val actualResult = topHeadlinePagingSource.load(
            PagingSource.LoadParams.Append(
                key = 2,
                loadSize = 1,
                placeholdersEnabled = false,
            )
        )

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `paging source append Success no more`() = runBlocking {
        val expectedResult = PagingSource.LoadResult.Page(
            data = emptyList(),
            prevKey = 1,
            nextKey = null,
        )

        coEvery { newsApi.getTopHeadlines(any(), any()) } returns Response.success(fakeNewsResponse.copy(articles = emptyList(),))

        val actualResult = topHeadlinePagingSource.load(
            PagingSource.LoadParams.Append(
                key = 2,
                loadSize = 1,
                placeholdersEnabled = false,
            )
        )

        assertEquals(expectedResult, actualResult)
    }
}