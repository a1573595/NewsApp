package com.a1573595.newsapp.ui.navigation

sealed class NavRoute(val route: String) {
    data object TopHeadline : NavRoute("topHeadline")
    data object Search : NavRoute("search")
    data object Detail : NavRoute("detail")
}