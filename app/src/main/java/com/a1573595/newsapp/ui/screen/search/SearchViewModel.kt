package com.a1573595.newsapp.ui.screen.search

import androidx.collection.LruCache
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

    private val _cachePagingData = LruCache<String, Flow<PagingData<Article>>>(10)

    fun clearQuery() {
        _query.value = ""
    }

    fun updateQuery(value: String) {
        _query.value = value
    }

    fun searchNews() {
        val query = _query.value

        if (query.isNotEmpty()) {
            val cachePagingData = _cachePagingData[query]

            if (cachePagingData != null) {
                _articlePagingData.value = cachePagingData
            } else {
                everythingUseCase(
                    query = query,
                ).cachedIn(viewModelScope).let {
                    _articlePagingData.value = it
                    _cachePagingData.put(query, it)
                }
            }
        } else {
            _articlePagingData.value = null
        }
    }

    override fun onCleared() {
        super.onCleared()
        _cachePagingData.evictAll()
    }
}