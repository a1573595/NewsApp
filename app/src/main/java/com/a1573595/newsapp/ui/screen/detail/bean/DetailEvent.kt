package com.a1573595.newsapp.ui.screen.detail.bean

sealed class DetailEvent {
    data object UpdateFavorite : DetailEvent()
}