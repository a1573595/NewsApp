package com.a1573595.newsapp.ui.navigation

import com.a1573595.newsapp.common.Base64EncodeDecode.encodeToBase64
import com.a1573595.newsapp.domain.model.Article
import kotlinx.serialization.json.Json

sealed class NavRoute(val route: String) {
    data object Home : NavRoute("home")
    data object Detail : NavRoute("detail/{article}") {
        fun passArticle(article: Article): String {
            return this.route.replace(
                oldValue = "{article}",
                newValue = Json.encodeToString(Article.serializer(), article).encodeToBase64()
            )
        }
    }
}