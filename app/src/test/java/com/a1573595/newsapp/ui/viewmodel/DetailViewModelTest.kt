package com.a1573595.newsapp.ui.viewmodel

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.a1573595.newsapp.common.Base64EncodeDecode
import com.a1573595.newsapp.common.Base64EncodeDecode.decodeFromBase64
import com.a1573595.newsapp.common.Base64EncodeDecode.encodeToBase64
import com.a1573595.newsapp.domain.model.Article
import com.a1573595.newsapp.domain.model.fakeArticle
import com.a1573595.newsapp.domain.usecase.FavoriteUseCase
import com.a1573595.newsapp.ui.screen.detail.DetailViewModel
import com.a1573595.newsapp.ui.screen.detail.bean.DetailEvent
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.serialization.json.Json
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.Base64

@ExperimentalCoroutinesApi
class DetailViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var favoriteUseCase: FavoriteUseCase
    private lateinit var viewModel: DetailViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        favoriteUseCase = mockk()

        /// not work
//        mockkStatic(Build.VERSION::class)
//        every { Build.VERSION.SDK_INT } returns Build.VERSION_CODES.O

        mockkObject(Base64EncodeDecode)
        coEvery { any<String>().encodeToBase64() } answers {
            Base64.getUrlEncoder().encodeToString(firstArg<String>().toByteArray())
        }
        coEvery { any<String>().decodeFromBase64() } answers {
            String(Base64.getUrlDecoder().decode(firstArg<String>().toByteArray()))
        }

        val savedStateHandle = SavedStateHandle()
        savedStateHandle["article"] = Json.encodeToString(Article.serializer(), fakeArticle).encodeToBase64()

        coEvery { favoriteUseCase.getArticle(fakeArticle.url) } returns fakeArticle

        viewModel = DetailViewModel(savedStateHandle, favoriteUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test state emits favorite from useCase`() = runTest {
        assertEquals(true, viewModel.state.value.isFavorite)
    }

    @Test
    fun `test onEvent UpdateFavorite`() {
        val isFavorite = viewModel.state.value.isFavorite

        coEvery { favoriteUseCase.upsertArticle(any()) } returns Unit
        coEvery { favoriteUseCase.deleteArticle(any()) } returns Unit

        viewModel.onEvent(DetailEvent.UpdateFavorite)

        assertEquals(!isFavorite, viewModel.state.value.isFavorite)

        viewModel.onEvent(DetailEvent.UpdateFavorite)

        assertEquals(isFavorite, viewModel.state.value.isFavorite)
    }
}