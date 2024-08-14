package com.a1573595.newsapp.ui.screen.detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a1573595.newsapp.common.Base64EncodeDecode.decodeFromBase64
import com.a1573595.newsapp.domain.model.Article
import com.a1573595.newsapp.domain.usecase.FavoriteUseCase
import com.a1573595.newsapp.ui.screen.detail.bean.DetailEvent
import com.a1573595.newsapp.ui.screen.detail.bean.DetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val handle: SavedStateHandle,
    private val favoriteUseCase: FavoriteUseCase,
) : ViewModel() {
    private var _state = mutableStateOf(
        DetailState(
            isFavorite = false,
            article = handle.get<String>("article")!!.decodeFromBase64().let {
                Json.decodeFromString<Article>(it)
            }
        ),
    )

    val state: State<DetailState> = _state

    init {
        viewModelScope.launch {
            if (favoriteUseCase.getArticle(_state.value.article.url) != null) {
                _state.value = _state.value.copy(isFavorite = true)
            }
        }
    }

    fun onEvent(event: DetailEvent) {
        when (event) {
            DetailEvent.UpdateFavorite -> {
                viewModelScope.launch {
                    if (_state.value.isFavorite) {
                        favoriteUseCase.deleteArticle(_state.value.article)
                    } else {
                        favoriteUseCase.upsertArticle(_state.value.article)
                    }

                    _state.value = _state.value.copy(isFavorite = !_state.value.isFavorite)
                }
            }
        }
    }
}