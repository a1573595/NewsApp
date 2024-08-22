package com.a1573595.newsapp.ui.screen.topHeadline

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.a1573595.newsapp.R
import com.a1573595.newsapp.domain.model.Article
import com.a1573595.newsapp.ui.Dimens
import com.a1573595.newsapp.ui.component.ArticleItem
import com.a1573595.newsapp.ui.component.NoMoreFooter
import com.a1573595.newsapp.ui.component.ErrorBody
import com.a1573595.newsapp.ui.component.LoadMoreFooter
import com.a1573595.newsapp.ui.component.LoadingBody

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopHeadlinesScreen(
    onArticleItemClick: (Article) -> Unit,
    viewModel: TopHeadlineViewModel = hiltViewModel(),
) {
    Column {
        TopAppBar(
            title = {
                Text(
                    stringResource(R.string.top_headlines),
                    style = MaterialTheme.typography.headlineMedium,
                )
            }
        )
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            val scrollToTop = remember { mutableStateOf(false) }
            val lazyListState: LazyListState = rememberLazyListState()
            val articleList = viewModel.articlePagingData.collectAsLazyPagingItems()

            LaunchedEffect(key1 = scrollToTop.value) {
                if (scrollToTop.value) {
                    scrollToTop.value = false
                    lazyListState.scrollToItem(0)
                }
            }

            articleList.loadState.apply {
                if (refresh is LoadState.Loading) {
                    LoadingBody()
                } else if (refresh is LoadState.Error) {
                    ErrorBody((refresh as LoadState.Error).error)
                }
            }
            TopHeadlinesListBody(lazyListState, articleList, onArticleItemClick)
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
fun TopHeadlinesListBody(
    lazyListState: LazyListState,
    articleList: LazyPagingItems<Article>,
    onArticleItemClick: (Article) -> Unit,
) {
    LazyColumn(
        state = lazyListState,
        modifier = Modifier
            .fillMaxSize(),
    ) {
        items(
            count = articleList.itemCount,
            key = articleList.itemKey { it.url }
        ) { index ->
            articleList[index]?.let {
                ArticleItem(it, onArticleItemClick)
            }
        }
        articleList.loadState.apply {
            when {
                append is LoadState.Loading -> item { LoadMoreFooter() }
                refresh is LoadState.NotLoading && append is LoadState.NotLoading -> item { NoMoreFooter() }
            }
        }
    }
}