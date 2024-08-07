package com.a1573595.newsapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Article(
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val imageUrl: String,
    val date: String,
)