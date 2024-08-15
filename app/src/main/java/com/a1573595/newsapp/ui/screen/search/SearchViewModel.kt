package com.a1573595.newsapp.ui.screen.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.a1573595.newsapp.domain.model.Article
import com.a1573595.newsapp.domain.usecase.EverythingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val everythingUseCase: EverythingUseCase,
) : ViewModel() {
    private val _query = mutableStateOf("")

    val query: State<String> = _query

    private var _articlePagingData = mutableStateOf<Flow<PagingData<Article>>?>(null)

    val articlePagingData: State<Flow<PagingData<Article>>?> get() = _articlePagingData

    fun updateQuery(value: String) {
        _query.value = value
    }

    fun searchNews() {
        if (_query.value.isNotEmpty()) {
            _articlePagingData.value = everythingUseCase(
                query = _query.value,
            ).cachedIn(viewModelScope)
        } else {
            _articlePagingData.value = null
        }
    }
}