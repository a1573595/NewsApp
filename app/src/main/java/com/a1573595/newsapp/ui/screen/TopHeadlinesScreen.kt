package com.a1573595.newsapp.ui.screen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.a1573595.newsapp.common.AsyncValue
import com.a1573595.newsapp.domain.model.Article
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopHeadlinesScreen(
    viewModel: NewsViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Top Headlines",
                        style = MaterialTheme.typography.headlineMedium,
                    )
                }
            )
        }
    ) { innerPadding ->
        val articlesState by viewModel.articlesState.collectAsState()
        val isRefreshing by viewModel.isRefresh

        Box(modifier = Modifier.padding(innerPadding)) {
            when (articlesState) {
                is AsyncValue.Loading -> LoadingBody()
                is AsyncValue.Error -> ErrorBody(articlesState.requireError)
                is AsyncValue.Data -> NewsListBody(articlesState.requireValue, isRefreshing, onRefresh = {
                    scope.launch {
                        viewModel.refreshTopHeadlines()
                    }
                })
            }
        }
    }
}

@Composable
fun LoadingBody() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        CircularProgressIndicator(modifier = Modifier.fillMaxWidth(.2f))
    }
}

/// todo paging3
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
            .padding(12.dp)
            .clickable { expanded = !expanded },
        shape = RoundedCornerShape(5.dp),
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
                    .padding(
                        horizontal = 8.dp
                    ),
                style = MaterialTheme.typography.titleMedium,
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                    .padding(
                        horizontal = 8.dp
                    ),
            ) {
                Box(
                    modifier = Modifier
                        .width(4.dp)
                        .fillMaxHeight()
                        .align(Alignment.CenterVertically)
                        .background(MaterialTheme.colorScheme.primary)
                )
                Text(
                    text = article.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp),
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
                            vertical = 12.dp,
                            horizontal = 8.dp
                        ),
                    style = MaterialTheme.typography.bodyMedium,
                )
            Text(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(
                        horizontal = 8.dp,
                        vertical = 4.dp,
                    ),
                text = article.date,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color.Gray
                ),
            )
        }
    }
}

@Composable
fun ErrorBody(throwable: Throwable) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = painterResource(id = android.R.drawable.ic_delete),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(.3f),
        )
        Text(
            text = throwable.message ?: "Unknown Error",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun LoadingBodyPreview() {
    LoadingBody()
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
        )
    )
}

@Preview(showSystemUi = true)
@Composable
fun ErrorBodyPreview() {
    ErrorBody(Exception("Preview"))
}

