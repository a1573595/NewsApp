package com.a1573595.newsapp.ui.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.a1573595.newsapp.R
import com.a1573595.newsapp.domain.model.Article
import com.a1573595.newsapp.ui.Dimens

@Composable
fun ArticleItem(
    article: Article,
    onArticleItemClick: (Article) -> Unit,
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimens.dp12)
            .clickable { expanded = !expanded }
            .clip(RoundedCornerShape(Dimens.dp8)),
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
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .aspectRatio(16f / 9f)
                    .fillMaxWidth(),
                placeholder = rememberVectorPainter(image = Icons.Outlined.Image),
            )
            Spacer(modifier = Modifier.height(Dimens.dp8))
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
            Spacer(modifier = Modifier.height(Dimens.dp12))
            if (expanded) {
                Text(
                    text = article.description,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Dimens.dp8),
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.height(Dimens.dp24))
                Text(
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(
                            horizontal = Dimens.dp8,
                        )
                        .clickable {
                            onArticleItemClick(article)
                        },
                    text = stringResource(R.string.read_more),
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.primary
                    ),
                )
            }
            Spacer(modifier = Modifier.height(if (expanded) Dimens.dp16 else Dimens.dp4))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimens.dp8),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = article.author,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.width(Dimens.dp16))
                Text(
                    text = article.date,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color.Gray
                    ),
                )
            }
            Spacer(modifier = Modifier.height(Dimens.dp8))
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
        ),
        {},
    )
}