package com.a1573595.newsapp.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.a1573595.newsapp.domain.model.fakeArticle
import com.a1573595.newsapp.domain.repository.NewsRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FavoriteUseCaseTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var newsRepository: NewsRepository

    private lateinit var favoriteUseCase: FavoriteUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        favoriteUseCase = FavoriteUseCase(newsRepository)
    }

    @Test
    fun `FavoriteUseCase invoke getArticleList`() = runTest {
        val expectedResult = listOf(fakeArticle)

        coEvery { newsRepository.getArticleList() } returns flowOf(expectedResult)

        val actualResult = favoriteUseCase().first()

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `FavoriteUseCase getArticle`() = runTest {
        val expectedResult = fakeArticle

        coEvery { newsRepository.getArticle(any()) } returns expectedResult

        val actualResult = favoriteUseCase.getArticle("")

        assertEquals(expectedResult, actualResult)
    }
}