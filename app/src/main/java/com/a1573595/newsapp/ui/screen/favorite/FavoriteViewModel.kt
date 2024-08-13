package com.a1573595.newsapp.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a1573595.newsapp.common.AsyncValue
import com.a1573595.newsapp.domain.model.Article
import com.a1573595.newsapp.domain.usecase.FavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoriteUseCase: FavoriteUseCase,
) : ViewModel() {
    private val _articlesState: MutableStateFlow<AsyncValue<List<Article>>> = MutableStateFlow(AsyncValue.Loading)

    val articlesState: StateFlow<AsyncValue<List<Article>>> get() = _articlesState

    init {
        getFavoriteList()
    }

    private fun getFavoriteList() = viewModelScope.launch {
        favoriteUseCase().collect {
            _articlesState.value = AsyncValue.Data(it)
        }
    }
}