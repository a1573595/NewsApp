package com.a1573595.newsapp.ui.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.a1573595.newsapp.R
import com.a1573595.newsapp.common.safeNavigate
import com.a1573595.newsapp.ui.Dimens

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
) {
    val colors = NavigationBarItemDefaults.colors(
        selectedIconColor = MaterialTheme.colorScheme.primary,
        selectedTextColor = MaterialTheme.colorScheme.primary,
        indicatorColor = MaterialTheme.colorScheme.background,
    )

    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.background,
        tonalElevation = Dimens.dp8,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        NavigationBarItem(
            colors = colors,
            selected = currentDestination?.hierarchy?.any {
                it.route == NavRoute.TopHeadline.route
            } == true,
            icon = {
                Column {
                    Icon(
                        Icons.Filled.Star,
                        contentDescription = null,
                    )
                    Spacer(
                        modifier = Modifier.height(Dimens.dp4)
                    )
                    Text(
                        stringResource(R.string.top),
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            },
            onClick = {
                navController.safeNavigate(NavRoute.TopHeadline.route)
            },
        )
        NavigationBarItem(
            colors = colors,
            selected = currentDestination?.hierarchy?.any {
                it.route == NavRoute.Search.route
            } == true,
            icon = {
                Column {
                    Icon(
                        Icons.Filled.Search,
                        contentDescription = null,
                    )
                    Spacer(
                        modifier = Modifier.height(Dimens.dp4)
                    )
                    Text(
                        stringResource(R.string.search),
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            },
            onClick = {
                navController.safeNavigate(NavRoute.Search.route)
            },
        )
    }
}