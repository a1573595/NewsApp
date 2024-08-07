package com.a1573595.newsapp.domain.usecase

import com.a1573595.newsapp.common.AsyncValue
import com.a1573595.newsapp.data.model.ArticleRaw
import com.a1573595.newsapp.domain.model.Article
import com.a1573595.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn
import javax.inject.Inject
import kotlin.math.abs

class ArticleUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
//    operator fun invoke(country: String = "us"): Flow<AsyncValue<List<Article>>>

    fun getTopHeadlines(country: String = "us"): Flow<AsyncValue<List<Article>>> = flow {
        emit(AsyncValue.Loading)
        val newsResponse = newsRepository.getTopHeadlines(country)

        if (newsResponse.isSuccessful) {
            val articleList = newsResponse.body()?.articles?.map { e -> mapArticleRawToArticle(e) } ?: emptyList()

            if (articleList.isNotEmpty()) {
                emit(AsyncValue.Data(articleList))
            } else {
                emit(AsyncValue.Error(Exception("Nothing to show")))
            }
        } else {
            emit(AsyncValue.Error(Exception("Fetch failed")))
            newsResponse.errorBody()
        }
    }.catch {
        emit(AsyncValue.Error(it))
    }.flowOn(Dispatchers.IO)

    private fun mapArticleRawToArticle(articleRaw: ArticleRaw): Article = Article(
        author = articleRaw.author,
        title = articleRaw.title,
        description = articleRaw.description ?: "No Description Available",
        url = articleRaw.url,
        imageUrl = articleRaw.urlToImage ?: "https://via.placeholder.com/1920x1080",
        date = getDaysAgoString(articleRaw.publishedAt),
    )

    private fun getDaysAgoString(date: String): String {
        val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
        val days = today.daysUntil(
            Instant.parse(date).toLocalDateTime(TimeZone.currentSystemDefault()).date
        )

        return when {
            abs(days) > 1 -> "${abs(days)} days ago"
            abs(days) == 1 -> "Yesterday"
            else -> "Today"
        }
    }
}