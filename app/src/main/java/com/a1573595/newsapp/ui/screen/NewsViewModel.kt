package com.a1573595.newsapp.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a1573595.newsapp.common.AsyncValue
import com.a1573595.newsapp.domain.model.Article
import com.a1573595.newsapp.domain.usecase.ArticleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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

    init {
        getArticles()
    }

    fun getArticles(forceFetch: Boolean = false) = viewModelScope.launch {
//        articleUseCase().collect {
//            _articlesState.value = it
//        }

        articleUseCase.getTopHeadlines().collect() {
            _articlesState.value = it
        }
    }
}