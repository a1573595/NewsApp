package com.a1573595.newsapp.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.a1573595.newsapp.domain.model.Article
import com.a1573595.newsapp.ui.screen.detail.DetailScreen
import com.a1573595.newsapp.ui.screen.favorite.FavoriteScreen
import com.a1573595.newsapp.ui.screen.search.SearchScreen
import com.a1573595.newsapp.ui.screen.topHeadline.TopHeadlinesScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val backStackState = navController.currentBackStackEntryAsState().value

    val isBottomBarVisible = remember(key1 = backStackState) {
        backStackState?.destination?.route == NavRoute.TopHeadline.route ||
                backStackState?.destination?.route == NavRoute.Search.route ||
                backStackState?.destination?.route == NavRoute.Favorite.route
    }

    val onArticleItemClick: (Article) -> Unit = {
        navController.navigate(NavRoute.Detail.passArticle(it))
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (isBottomBarVisible) {
                BottomNavigationBar(navController)
            }
        },
    ) { innerPadding ->
        NavHost(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            navController = navController,
            startDestination = NavRoute.TopHeadline.route,
//            enterTransition = { EnterTransition.None },
            popEnterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            popExitTransition = { ExitTransition.None },
        ) {
            composable(NavRoute.TopHeadline.route) {
                TopHeadlinesScreen(onArticleItemClick = onArticleItemClick)
            }
            composable(NavRoute.Search.route) {
                SearchScreen(onArticleItemClick = onArticleItemClick)
            }
            composable(NavRoute.Favorite.route) {
                FavoriteScreen(onArticleItemClick = onArticleItemClick)
            }
            composable(NavRoute.Detail.route,) {
                DetailScreen(onBackClick = { navController.navigateUp() })
            }
        }
    }
}