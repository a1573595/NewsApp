package com.a1573595.newsapp.di

import com.a1573595.newsapp.data.repository.NewsRepositoryImpl
import com.a1573595.newsapp.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun provideNewsRepository(newsRepositoryImpl: NewsRepositoryImpl): NewsRepository = newsRepositoryImpl
}