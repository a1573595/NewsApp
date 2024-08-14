package com.a1573595.newsapp.ui.navigation

import com.a1573595.newsapp.common.Base64EncodeDecode.encodeToBase64
import com.a1573595.newsapp.domain.model.Article
import kotlinx.serialization.json.Json

sealed class NavRoute(val route: String) {
    data object TopHeadline : NavRoute("topHeadline")
    data object Search : NavRoute("search")
    data object Favorite : NavRoute("favorite")
    data object Detail : NavRoute("detail/{article}") {
        fun passArticle(article: Article): String {
            return this.route.replace(
                oldValue = "{article}",
                newValue = Json.encodeToString(Article.serializer(), article).encodeToBase64()
            )
        }
    }
}