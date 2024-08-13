package com.a1573595.newsapp.ui.screen.favorite

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.a1573595.newsapp.R
import com.a1573595.newsapp.common.AsyncValue
import com.a1573595.newsapp.domain.model.Article
import com.a1573595.newsapp.ui.component.ArticleItem
import com.a1573595.newsapp.ui.component.ErrorBody
import com.a1573595.newsapp.ui.component.LoadingBody

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(
    onArticleItemClick: (Article) -> Unit,
    viewModel: FavoriteViewModel = hiltViewModel(),
) {
    Column {
        TopAppBar(
            title = {
                Text(
                    stringResource(R.string.favorite),
                    style = MaterialTheme.typography.headlineMedium,
                )
            }
        )
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            val articlesState by viewModel.articlesState.collectAsState()

            when (articlesState) {
                is AsyncValue.Loading -> LoadingBody()
                is AsyncValue.Error -> ErrorBody(articlesState.requireError)
                is AsyncValue.Data -> NewsListBody(
                    articleList = articlesState.requireValue,
                    onArticleItemClick = onArticleItemClick,
                )
            }
        }
    }
}

@Composable
fun NewsListBody(
    articleList: List<Article>,
    onArticleItemClick: (Article) -> Unit,
    lazyListState: LazyListState = rememberLazyListState()
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            state = lazyListState,
            modifier = Modifier.fillMaxSize(),
        ) {
            items(articleList, key = { it.url }) { article ->
                ArticleItem(article, onArticleItemClick)
            }
        }
    }
}