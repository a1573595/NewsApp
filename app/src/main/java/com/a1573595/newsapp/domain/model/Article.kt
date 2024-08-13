package com.a1573595.newsapp.domain.model

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.a1573595.newsapp.common.getDaysAgoString
import com.a1573595.newsapp.data.model.ArticleRaw
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.json.Json

@Entity
@Parcelize
@JsonClass(generateAdapter = true)
data class Article(
    val author: String,
    val title: String,
    val description: String,
    @PrimaryKey val url: String,
    val imageUrl: String,
    val date: String,
    val content: String,
) : Parcelable {
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

class AssetParamType : NavType<Article>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): Article? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelable(key, Article::class.java)
        } else {
            bundle.getParcelable(key)
        }
    }

    override fun parseValue(value: String): Article {
        val raw = Json { ignoreUnknownKeys = true }.decodeFromString<ArticleRaw>(value)
        return Article.fromRaw(raw)
//        Json { ignoreUnknownKeys = true }.encodeToString(ArticleRaw.serializer(), value)
//        return Gson().fromJson(value, Article::class.java)
    }

    override fun put(bundle: Bundle, key: String, value: Article) {
        bundle.putParcelable(key, value)
    }
}