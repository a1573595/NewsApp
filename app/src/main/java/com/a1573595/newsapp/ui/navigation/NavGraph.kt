package com.a1573595.newsapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.a1573595.newsapp.ui.screen.TopHeadlinesScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavRoute.NewsList.route
    ) {
        composable(NavRoute.NewsList.route) {
            TopHeadlinesScreen()
        }
    }
}