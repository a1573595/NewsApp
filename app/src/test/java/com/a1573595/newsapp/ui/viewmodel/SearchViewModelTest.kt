package com.a1573595.newsapp.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import app.cash.turbine.test
import com.a1573595.newsapp.common.NoopListCallback
import com.a1573595.newsapp.domain.model.Article
import com.a1573595.newsapp.domain.model.ArticleDiffCallback
import com.a1573595.newsapp.domain.model.fakeArticle
import com.a1573595.newsapp.domain.usecase.EverythingUseCase
import com.a1573595.newsapp.ui.screen.search.SearchViewModel
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
class SearchViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var everythingUseCase: EverythingUseCase
    private lateinit var viewModel: SearchViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        everythingUseCase = mockk()

        viewModel = SearchViewModel(everythingUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test updateQuery`() = runTest {
        val query = "test"

        viewModel.updateQuery(query)

        assertEquals(query, viewModel.query.value)
    }

    @Test
    fun `test searchNews`() = runTest {
        val query = "test"
        val mockData = PagingData.from(listOf(fakeArticle))
        val differ = AsyncPagingDataDiffer<Article>(
            diffCallback = ArticleDiffCallback(),
            updateCallback = NoopListCallback(),
            workerDispatcher = testDispatcher,
        )

        coEvery { everythingUseCase(query) } returns flowOf(mockData)

        viewModel.updateQuery(query)
        viewModel.searchNews()

        viewModel.articlePagingData.value?.test {
            differ.submitData(awaitItem())
        }

        // Advance the test dispatcher to allow the differ to process the data
        advanceUntilIdle()

        assertEquals(listOf(fakeArticle), differ.snapshot().items)
    }
}