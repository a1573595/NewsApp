package com.a1573595.newsapp.ui.screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a1573595.newsapp.common.AsyncValue
import com.a1573595.newsapp.domain.model.Article
import com.a1573595.newsapp.domain.usecase.ArticleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val articleUseCase: ArticleUseCase
) : ViewModel() {
    private val _articlesState: MutableStateFlow<AsyncValue<List<Article>>> =
        MutableStateFlow(AsyncValue.Loading)

    val articlesState: StateFlow<AsyncValue<List<Article>>> get() = _articlesState

    private val _isRefresh: MutableState<Boolean> = mutableStateOf(false)

    val isRefresh: State<Boolean> get() = _isRefresh

    init {
        getTopHeadlines()
    }

    private fun getTopHeadlines() = viewModelScope.launch {
//        articleUseCase().collect {
//            _articlesState.value = it
//        }

        articleUseCase.getTopHeadlines().collect {
            _articlesState.value = it
        }
    }

    fun refreshTopHeadlines() = viewModelScope.launch {
        articleUseCase.getTopHeadlines().collect {
            _isRefresh.value = true
//            delay(2000)

            if (it != AsyncValue.Loading) {
                _articlesState.value = it
                _isRefresh.value = false
            }
        }
    }
}