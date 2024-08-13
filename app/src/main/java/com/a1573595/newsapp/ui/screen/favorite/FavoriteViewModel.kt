package com.a1573595.newsapp.ui.screen.favorite

import androidx.lifecycle.ViewModel
import com.a1573595.newsapp.domain.usecase.FavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoriteUseCase: FavoriteUseCase,
) : ViewModel() {
}