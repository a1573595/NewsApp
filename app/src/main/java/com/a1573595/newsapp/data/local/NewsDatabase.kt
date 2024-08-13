package com.a1573595.newsapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.a1573595.newsapp.domain.model.Article

@Database(
    version = 1,
    entities = [Article::class],
//    exportSchema = false,
)
abstract class NewsDatabase : RoomDatabase() {
    abstract val newsDao: NewsDao
}