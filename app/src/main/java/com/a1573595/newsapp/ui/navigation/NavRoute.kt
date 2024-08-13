package com.a1573595.newsapp.ui.navigation

import com.a1573595.newsapp.domain.model.Article

sealed class NavRoute(val route: String) {
    data object TopHeadline : NavRoute("topHeadline")
    data object Search : NavRoute("search")
    data object Favorite : NavRoute("favorite")
    data class Detail(
        val articleJsonString: String = ""
    ) : NavRoute("detail/{article}"){
        override val navCommand: String
            get() = route.replace("{article}", articleJsonString)
    }

    open val navCommand = route
//    data object Detail : NavRoute("DetailsScreen/{article}") {
//        fun passArticle(article: Article): String {
//            return this.route.replace(oldValue = "{article}", newValue = article.toString())
//        }
//        fun passArticle(id: Int): String {
//            return this.route.replace(oldValue = "{article}", newValue = id.toString())
//        }
//    }
}