package com.a1573595.newsapp.ui.screen.detail.bean

import com.a1573595.newsapp.domain.model.Article

data class DetailState(
    var isFavorite: Boolean,
    val article: Article,
)