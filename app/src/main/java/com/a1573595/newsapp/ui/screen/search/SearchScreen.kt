package com.a1573595.newsapp.ui.screen.search

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.a1573595.newsapp.R
import com.a1573595.newsapp.domain.model.Article
import com.a1573595.newsapp.ui.Dimens
import com.a1573595.newsapp.ui.component.ErrorBody
import com.a1573595.newsapp.ui.component.LoadMoreFooter
import com.a1573595.newsapp.ui.component.LoadingBody
import com.a1573595.newsapp.ui.component.NoMoreFooter
import com.a1573595.newsapp.ui.screen.topHeadline.ArticleItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(
            title = {
                Text(
                    stringResource(R.string.search),
                    style = MaterialTheme.typography.headlineMedium,
                )
            }
        )
        TextFieldSearchBar(
            viewModel.query.value,
            onValueChange = {
                viewModel.updateQuery(it)
            },
            onSearch = {
                viewModel.searchNews()
            }
        )
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            viewModel.articlePagingData.value?.let {
                val articleList = it.collectAsLazyPagingItems()

                articleList.loadState.apply {
                    if (refresh is LoadState.Loading) {
                        LoadingBody()
                    } else if (refresh is LoadState.Error) {
                        ErrorBody((refresh as LoadState.Error).error)
                    }
                }
                SearchListBody(articleList)
            }
        }
    }
}

@Composable
fun TextFieldSearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    onSearch: () -> Unit,
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }

    TextField(
        interactionSource = interactionSource,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.dp16)
            .border(
                width = Dimens.dp1,
                color = Color.Black,
                shape = MaterialTheme.shapes.large,
            ),
        shape = MaterialTheme.shapes.large,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        singleLine = true,
        leadingIcon = {
            Icon(
                Icons.Filled.Search,
                modifier = Modifier.size(Dimens.dp32),
                contentDescription = null,
            )
        },
        placeholder = {
            Text(
                text = stringResource(R.string.query),
                style = MaterialTheme.typography.bodyLarge,
            )
        },
        value = value,
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search,
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch()
            }
        ),
    )
}

@Composable
fun SearchListBody(
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