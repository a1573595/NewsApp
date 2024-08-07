package com.a1573595.newsapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class NewsResponse(
    val articles: List<ArticleRaw>,
    val status: String,
    val totalResults: Int,
)

@Serializable
data class ArticleRaw(
    val author: String,
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String,
)