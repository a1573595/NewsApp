package com.a1573595.newsapp.ui.screen.topHeadline

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import com.a1573595.newsapp.R
import com.a1573595.newsapp.domain.model.Article
import com.a1573595.newsapp.ui.Dimens
import com.a1573595.newsapp.ui.component.NoMoreFooter
import com.a1573595.newsapp.ui.component.ErrorBody
import com.a1573595.newsapp.ui.component.LoadMoreFooter
import com.a1573595.newsapp.ui.component.LoadingBody
import com.a1573595.newsapp.ui.screen.NewsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopHeadlinesScreen(
    viewModel: NewsViewModel = hiltViewModel()
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
            val articleList = viewModel.articlePagingData.collectAsLazyPagingItems()

            articleList.loadState.apply {
                if (refresh is LoadState.Loading) {
                    LoadingBody()
                } else if (refresh is LoadState.Error) {
                    ErrorBody((refresh as LoadState.Error).error)
                }
            }
            TopHeadlinesListBody(articleList)
        }
    }

//    val scope = rememberCoroutineScope()
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = {
//                    Text(
//                        stringResource(R.string.top_headlines),
//                        style = MaterialTheme.typography.headlineMedium,
//                    )
//                }
//            )
//        }
//    ) { innerPadding ->
//        val articlesState by viewModel.articlesState.collectAsState()
//        val isRefreshing by viewModel.isRefresh
//
//        Box(modifier = Modifier.padding(innerPadding)) {
//            when (articlesState) {
//                is AsyncValue.Loading -> LoadingBody()
//                is AsyncValue.Error -> ErrorBody(articlesState.requireError)
//                is AsyncValue.Data -> NewsListBody(articlesState.requireValue, isRefreshing, onRefresh = {
//                    scope.launch {
//                        viewModel.refreshTopHeadlines()
//                    }
//                })
//            }
//        }
//    }
}

@Composable
fun TopHeadlinesListBody(
    articleList: LazyPagingItems<Article>,
    lazyListState: LazyListState = rememberLazyListState()
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
                ArticleItem(it)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsListBody(
    articleList: List<Article>,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    lazyListState: LazyListState = rememberLazyListState()
) {
    val pullToRefreshState = rememberPullToRefreshState()

    if (pullToRefreshState.isRefreshing) {
        LaunchedEffect(key1 = true) {
            onRefresh()
        }
    }

    if (!isRefreshing) {
        pullToRefreshState.endRefresh()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(pullToRefreshState.nestedScrollConnection)
    ) {
        LazyColumn(
            state = lazyListState,
            modifier = Modifier
                .fillMaxSize(),
        ) {
            items(articleList, key = { it.url }) { article ->
                ArticleItem(article)
            }
        }

        PullToRefreshContainer(
            state = pullToRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@Composable
fun ArticleItem(article: Article) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimens.dp12)
            .clickable { expanded = !expanded },
        shape = RoundedCornerShape(Dimens.dp8),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AsyncImage(
                model = article.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .aspectRatio(16f / 9f)
                    .fillMaxWidth(),
                placeholder = rememberVectorPainter(image = Icons.Outlined.Image),
            )
            Text(
                text = article.author,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimens.dp8),
                style = MaterialTheme.typography.titleMedium,
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                    .padding(horizontal = Dimens.dp8),
            ) {
                Box(
                    modifier = Modifier
                        .width(Dimens.dp4)
                        .fillMaxHeight()
                        .align(Alignment.CenterVertically)
                        .background(MaterialTheme.colorScheme.primary)
                )
                Text(
                    text = article.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = Dimens.dp8),
                    style = MaterialTheme.typography.titleMedium
                        .copy(
                            fontWeight = FontWeight.Bold,
                        ),
                    maxLines = if (expanded) Int.MAX_VALUE else 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            if (expanded)
                Text(
                    text = article.description,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = Dimens.dp12,
                            horizontal = Dimens.dp8,
                        ),
                    style = MaterialTheme.typography.bodyMedium,
                )
            Text(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(
                        horizontal = Dimens.dp8,
                        vertical = Dimens.dp4,
                    ),
                text = article.date,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color.Gray
                ),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArticleItemPreview() {
    ArticleItem(
        Article(
            author = "Author",
            title = "Title",
            description = "Descritpion",
            url = "",
            imageUrl = "",
            date = "Today",
            content = ""
        )
    )
}