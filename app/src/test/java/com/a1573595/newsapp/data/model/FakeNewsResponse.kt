package com.a1573595.newsapp.data.model

val fakeArticleRaw = ArticleRaw(
    author = "BBC News",
    title = "Disney+ terms prevent allergy death lawsuit, Disney says",
    description = "The entertainment giant says the terms of a free trial prevent it from being sued for wrongful death",
    url = "https://www.bbc.co.uk/news/articles/c8jl0ekjr0go",
    urlToImage = "https://ichef.bbci.co.uk/news/1024/branded_news/fe77/live/0358fd10-5a28-11ef-9d2d-89abc1f1e271.jpg",
    publishedAt = "2024-08-15T00:52:14.0828309Z",
    content = "Mr Piccolo alleges that the restaurant at Disney World - in Orlando, Florida - that he and his wife dined at did not take enough care over her severe allergies to dairy and nuts, despite being repeat… [+913 chars]",
)

val fakeNewsResponse = NewsResponse(
    status = "ok",
    totalResults = 1,
    articles = listOf(fakeArticleRaw)
)