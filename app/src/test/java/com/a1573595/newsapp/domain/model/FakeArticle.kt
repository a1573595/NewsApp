package com.a1573595.newsapp.domain.model

import androidx.recyclerview.widget.DiffUtil
import com.a1573595.newsapp.data.model.fakeArticleRaw

val fakeArticle = Article.fromRaw(fakeArticleRaw)

class ArticleDiffCallback : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.url == newItem.url
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }
}