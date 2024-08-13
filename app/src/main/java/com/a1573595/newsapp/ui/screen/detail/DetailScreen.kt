package com.a1573595.newsapp.ui.screen.detail

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.Public
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.a1573595.newsapp.domain.model.Article
import com.a1573595.newsapp.ui.Dimens
import com.a1573595.newsapp.ui.screen.detail.bean.DetailEvent
import com.a1573595.newsapp.ui.screen.detail.bean.DetailState

@Composable
fun DetailScreen(
    viewModel: DetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    article: Article,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        val detailState = DetailState(false, article)// viewModel.state.value

        DetailAppBar(
            detailState,
            onBackClick,
            onFavoriteClick = {
                viewModel.onEvent(DetailEvent.UpdateFavorite)
            },
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(Dimens.dp16)
        ) {
            item {
                AsyncImage(
                    model = detailState.article.imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .aspectRatio(16f / 9f)
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.medium),
                    placeholder = rememberVectorPainter(image = Icons.Outlined.Image),
                )
                Spacer(modifier = Modifier.height(Dimens.dp16))
                Text(
                    text = detailState.article.author,
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.titleMedium,
                )
                Spacer(modifier = Modifier.height(Dimens.dp8))
                Text(
                    text = detailState.article.title,
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.headlineSmall,
                )
                Spacer(modifier = Modifier.height(Dimens.dp16))
                Text(
                    text = detailState.article.content,
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailAppBar(
    detailState: DetailState,
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit,
) {
    val context = LocalContext.current

    TopAppBar(
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    Icons.Filled.ArrowBackIosNew,
                    contentDescription = null,
                )
            }
        },
        title = {},
        actions = {
            IconButton(onClick = onFavoriteClick) {
                Icon(
                    if (detailState.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = null,
                )
            }
            IconButton(onClick = {
                Intent(Intent.ACTION_SEND).also {
                    it.putExtra(Intent.EXTRA_TEXT, detailState.article.url)
                    it.type = "text/plain"
                    if (it.resolveActivity(context.packageManager) != null) {
                        context.startActivity(it)
                    }
                }
            }) {
                Icon(
                    Icons.Default.Share,
                    contentDescription = null
                )
            }
            IconButton(onClick = {
                Intent(Intent.ACTION_VIEW).also {
                    it.data = Uri.parse(detailState.article.url)
                    if (it.resolveActivity(context.packageManager) != null) {
                        context.startActivity(it)
                    }
                }
            }) {
                Icon(
                    Icons.Outlined.Public,
                    contentDescription = null
                )
            }
        },
    )
}