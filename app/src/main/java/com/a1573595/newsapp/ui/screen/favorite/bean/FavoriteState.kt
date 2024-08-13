package com.a1573595.newsapp.ui.screen.favorite.bean

import com.a1573595.newsapp.domain.model.Article

data class FavoriteState(
    var isFavorite: Boolean,
    val article: Article,
)