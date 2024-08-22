package com.a1573595.newsapp.ui.screen.favorite

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VerticalAlignTop
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.a1573595.newsapp.R
import com.a1573595.newsapp.common.AsyncValue
import com.a1573595.newsapp.domain.model.Article
import com.a1573595.newsapp.ui.Dimens
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
            val scrollToTop = remember { mutableStateOf(false) }
            val lazyListState: LazyListState = rememberLazyListState()
            val articlesState by viewModel.articlesState.collectAsState()

            LaunchedEffect(key1 = scrollToTop.value) {
                if (scrollToTop.value) {
                    scrollToTop.value = false
                    lazyListState.scrollToItem(0)
                }
            }

            when (articlesState) {
                is AsyncValue.Loading -> LoadingBody()
                is AsyncValue.Error -> ErrorBody(articlesState.requireError)
                is AsyncValue.Data -> NewsListBody(
                    articleList = articlesState.requireValue,
                    onArticleItemClick = onArticleItemClick,
                )
            }
            FloatingActionButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(Dimens.dp32),
                onClick = {
                    scrollToTop.value = true
                },
            ) {
                Icon(
                    Icons.Filled.VerticalAlignTop,
                    modifier = Modifier.size(Dimens.dp32),
                    contentDescription = null,
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