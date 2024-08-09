package com.a1573595.newsapp.ui.screen.topHeadline

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.a1573595.newsapp.domain.usecase.TopHeadlineUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TopHeadlineViewModel @Inject constructor(
    private val topHeadlineUseCase: TopHeadlineUseCase
) : ViewModel() {
    val articlePagingData = topHeadlineUseCase().cachedIn(viewModelScope)

//    private val _articlesState: MutableStateFlow<AsyncValue<List<Article>>> =
//        MutableStateFlow(AsyncValue.Loading)
//
//    val articlesState: StateFlow<AsyncValue<List<Article>>> get() = _articlesState
//
//    private val _isRefresh: MutableState<Boolean> = mutableStateOf(false)
//
//    val isRefresh: State<Boolean> get() = _isRefresh
//
//    init {
//        getTopHeadlines()
//    }
//
//    private fun getTopHeadlines() = viewModelScope.launch {
//        topHeadlineUseCase.getTopHeadlines().collect {
//            _articlesState.value = it
//        }
//    }
//
//    fun refreshTopHeadlines() = viewModelScope.launch {
//        topHeadlineUseCase.getTopHeadlines().collect {
//            _isRefresh.value = true
////            delay(2000)
//
//            if (it != AsyncValue.Loading) {
//                _articlesState.value = it
//                _isRefresh.value = false
//            }
//        }
//    }
}