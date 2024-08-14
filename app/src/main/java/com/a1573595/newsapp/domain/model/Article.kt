package com.a1573595.newsapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.a1573595.newsapp.common.getDaysAgoString
import com.a1573595.newsapp.data.model.ArticleRaw
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class Article(
    val author: String,
    val title: String,
    val description: String,
    @PrimaryKey val url: String,
    val imageUrl: String,
    val date: String,
    val content: String,
) {
    companion object {
        fun fromRaw(articleRaw: ArticleRaw): Article {
            return Article(
                author = articleRaw.author ?: "Unknown Author",
                title = articleRaw.title,
                description = articleRaw.description ?: "No Description Available",
                url = articleRaw.url,
                imageUrl = articleRaw.urlToImage ?: "https://via.placeholder.com/1920x1080",
                date = articleRaw.publishedAt.getDaysAgoString(),
                content = articleRaw.content ?: "No Content Available",
            )
        }
    }
}