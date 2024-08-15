package com.a1573595.newsapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.a1573595.newsapp.domain.model.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Query("SELECT * FROM Article WHERE url=:url")
    suspend fun getArticle(url: String): Article?

    @Query("SELECT * FROM Article Order By rowid DESC")
    fun getArticleList(): Flow<List<Article>>

    @Upsert
    suspend fun upsertArticle(article: Article)

    @Delete
    suspend fun deleteArticle(article: Article)
}