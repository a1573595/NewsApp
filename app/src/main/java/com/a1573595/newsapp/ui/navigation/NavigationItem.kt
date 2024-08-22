package com.a1573595.newsapp.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.ui.graphics.vector.ImageVector
import com.a1573595.newsapp.R

data class NavigationItem(
    val icon: ImageVector,
    @StringRes val labelStringRes: Int,
)

val navigationItemList = listOf(
    NavigationItem(Icons.Outlined.StarOutline, R.string.top),
    NavigationItem(Icons.Outlined.Search, R.string.search),
    NavigationItem(Icons.Outlined.FavoriteBorder, R.string.favorite),
)