package com.a1573595.newsapp.domain.model

import androidx.recyclerview.widget.DiffUtil

val fakeArticle = Article(
    author = "Fake author",
    title = "Fake title",
    description = "Fake description",
    url = "https://www.google.com",
    imageUrl = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png",
    date = "2024/08/08 06:00",
    content = "Fake content",
)

class ArticleDiffCallback : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.url == newItem.url
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }
}