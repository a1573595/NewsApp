package com.a1573595.newsapp.ui.navigation

sealed class NavRoute(val route: String) {
    data object NewsList : NavRoute("newsList")
}