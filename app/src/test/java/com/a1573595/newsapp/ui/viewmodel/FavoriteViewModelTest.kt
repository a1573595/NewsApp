package com.a1573595.newsapp.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.a1573595.newsapp.domain.model.Article
import com.a1573595.newsapp.domain.model.fakeArticle
import com.a1573595.newsapp.domain.usecase.FavoriteUseCase
import com.a1573595.newsapp.ui.screen.favorite.FavoriteViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class FavoriteViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var favoriteUseCase: FavoriteUseCase
    private lateinit var viewModel: FavoriteViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        favoriteUseCase = mockk()

        coEvery { favoriteUseCase() } returns flowOf(listOf(fakeArticle))

        viewModel = FavoriteViewModel(favoriteUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test articlesState emits ArticleList from useCase`() = runTest {
        viewModel.articlesState.test {
            val emission = awaitItem()

            assertEquals(listOf(fakeArticle), emission.value)
            cancelAndIgnoreRemainingEvents()
        }
    }
}