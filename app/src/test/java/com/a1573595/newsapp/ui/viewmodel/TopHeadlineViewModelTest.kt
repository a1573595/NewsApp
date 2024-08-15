package com.a1573595.newsapp.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import app.cash.turbine.test
import com.a1573595.newsapp.common.NoopListCallback
import com.a1573595.newsapp.domain.model.Article
import com.a1573595.newsapp.domain.model.ArticleDiffCallback
import com.a1573595.newsapp.domain.model.fakeArticle
import com.a1573595.newsapp.domain.usecase.TopHeadlineUseCase
import com.a1573595.newsapp.ui.screen.topHeadline.TopHeadlineViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class TopHeadlineViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var topHeadlineUseCase: TopHeadlineUseCase
    private lateinit var viewModel: TopHeadlineViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        topHeadlineUseCase = mockk()

        coEvery { topHeadlineUseCase() } returns flowOf(PagingData.from(listOf(fakeArticle)))

        viewModel = TopHeadlineViewModel(topHeadlineUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test articlePagingData emits PagingData from useCase`() = runTest {
        val differ = AsyncPagingDataDiffer<Article>(
            diffCallback = ArticleDiffCallback(),
            updateCallback = NoopListCallback(),
            workerDispatcher = testDispatcher,
        )

        viewModel.articlePagingData.test {
            differ.submitData(awaitItem())
        }

        // Advance the test dispatcher to allow the differ to process the data
        advanceUntilIdle()

        assertEquals(listOf(fakeArticle), differ.snapshot().items)
    }
}