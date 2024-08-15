package com.a1573595.newsapp.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.a1573595.newsapp.data.local.NewsDao
import com.a1573595.newsapp.data.local.NewsDatabase
import com.a1573595.newsapp.domain.fakeArticle1
import com.a1573595.newsapp.domain.fakeArticle2
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/// https://github.com/google-developer-training/basic-android-kotlin-compose-training-inventory-app/blob/main/app/src/androidTest/java/com/example/inventory/ItemDaoTest.kt
@RunWith(AndroidJUnit4::class)
class NewsDaoTest {
    private lateinit var newsDatabase: NewsDatabase
    private lateinit var newsDao: NewsDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        newsDatabase = Room.inMemoryDatabaseBuilder(context, NewsDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        newsDao = newsDatabase.newsDao
    }

    @After
    fun closeDb() {
        newsDatabase.close()
    }

    @Test
    fun test_getArticle() = runBlocking {
        addTwoItemToDb()

        val article1 = newsDao.getArticle(fakeArticle1.url)
        val article2 = newsDao.getArticle(fakeArticle2.url)

        assertEquals(fakeArticle1, article1)
        assertEquals(fakeArticle2, article2)
    }

    @Test
    fun test_getArticleList() = runBlocking {
        addTwoItemToDb()

        val articleList = newsDao.getArticleList().first()

        assertEquals(listOf(fakeArticle2, fakeArticle1), articleList)
    }

    @Test
    fun test_upsertArticle() = runBlocking {
        addOneItemToDb()

        val article = newsDao.getArticleList().first().first()

        assertEquals(fakeArticle1, article)
    }

    @Test
    fun test_deleteArticle() = runBlocking {
        addTwoItemToDb()

        newsDao.deleteArticle(fakeArticle1)
        val article = newsDao.getArticleList().first().first()

        assertEquals(fakeArticle2, article)
    }

    private suspend fun addOneItemToDb() = newsDao.upsertArticle(fakeArticle1)

    private suspend fun addTwoItemToDb() {
        newsDao.upsertArticle(fakeArticle1)
        newsDao.upsertArticle(fakeArticle2)
    }
}