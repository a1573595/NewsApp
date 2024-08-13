package com.a1573595.newsapp.ui.screen.topHeadline

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.a1573595.newsapp.domain.usecase.TopHeadlineUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TopHeadlineViewModel @Inject constructor(
    private val topHeadlineUseCase: TopHeadlineUseCase,
) : ViewModel() {
    val articlePagingData = topHeadlineUseCase().cachedIn(viewModelScope)
}