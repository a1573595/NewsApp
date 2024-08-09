package com.a1573595.newsapp.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.a1573595.newsapp.ui.screen.search.SearchScreen
import com.a1573595.newsapp.ui.screen.topHeadline.TopHeadlinesScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    innerPadding: PaddingValues,
) {
    NavHost(
        navController = navController,
        startDestination = NavRoute.TopHeadline.route,
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        composable(NavRoute.TopHeadline.route) {
            TopHeadlinesScreen()
        }
        composable(NavRoute.Search.route) {
            SearchScreen()
        }
    }
}