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
        val expectedResult1 = fakeArticle1
        val expectedResult2 = fakeArticle2
        addTwoItemToDb()

        val actualResult1 = newsDao.getArticle(fakeArticle1.url)
        val actualResult2 = newsDao.getArticle(fakeArticle2.url)

        assertEquals(expectedResult1, actualResult1)
        assertEquals(expectedResult2, actualResult2)
    }

    @Test
    fun test_getArticleList() = runBlocking {
        val expectedResult = listOf(fakeArticle2, fakeArticle1)
        addTwoItemToDb()

        val actualResult = newsDao.getArticleList().first()

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun test_upsertArticle() = runBlocking {
        val expectedResult = fakeArticle1
        addOneItemToDb()

        val actualResult = newsDao.getArticleList().first().first()

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun test_deleteArticle() = runBlocking {
        val expectedResult = fakeArticle2
        addTwoItemToDb()

        newsDao.deleteArticle(fakeArticle1)
        val actualResult = newsDao.getArticleList().first().first()

        assertEquals(expectedResult, actualResult)
    }

    private suspend fun addOneItemToDb() = newsDao.upsertArticle(fakeArticle1)

    private suspend fun addTwoItemToDb() {
        newsDao.upsertArticle(fakeArticle1)
        newsDao.upsertArticle(fakeArticle2)
    }
}