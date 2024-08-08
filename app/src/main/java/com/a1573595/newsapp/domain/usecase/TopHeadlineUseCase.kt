package com.a1573595.newsapp.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import com.a1573595.newsapp.domain.model.Article
import com.a1573595.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TopHeadlineUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(): Flow<PagingData<Article>> = newsRepository.getTopHeadlines()
        .map { pagingData -> pagingData.map { raw -> Article.fromRaw(raw) } }
        .flowOn(Dispatchers.IO)

//    fun getTopHeadlines(): Flow<AsyncValue<List<Article>>> = flow {
//        emit(AsyncValue.Loading)
//        val newsResponse = newsRepository.getTopHeadlines(page = 1)
//
//        if (newsResponse.isSuccessful) {
//            val articleList = newsResponse.body()?.articles?.map { Article.fromRaw(it) } ?: emptyList()
//
//            if (articleList.isNotEmpty()) {
//                emit(AsyncValue.Data(articleList))
//            } else {
//                emit(AsyncValue.Error(Exception("Nothing to show")))
//            }
//        } else {
//            emit(AsyncValue.Error(Exception(newsResponse.errorBody()?.string() ?: "Fetch failed")))
//        }
//    }.catch {
//        emit(AsyncValue.Error(it))
//    }.flowOn(Dispatchers.IO)
}