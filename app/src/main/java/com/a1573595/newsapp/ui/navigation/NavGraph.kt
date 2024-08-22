package com.a1573595.newsapp.ui.navigation

import android.util.Log
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.a1573595.newsapp.domain.model.Article
import com.a1573595.newsapp.ui.screen.detail.DetailScreen
import com.a1573595.newsapp.ui.screen.favorite.FavoriteScreen
import com.a1573595.newsapp.ui.screen.search.SearchScreen
import com.a1573595.newsapp.ui.screen.topHeadline.TopHeadlinesScreen

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val backStackState = navController.currentBackStackEntryAsState().value

    val isBottomBarVisible = remember(key1 = backStackState) {
        backStackState?.destination?.route == NavRoute.Home.route
    }

    val pagerState = rememberPagerState(pageCount = { 3 })

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        NavHost(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            navController = navController,
            startDestination = NavRoute.Home.route,
//            enterTransition = { EnterTransition.None },
//            popEnterTransition = { EnterTransition.None },
//            exitTransition = { ExitTransition.None },
//            popExitTransition = { ExitTransition.None },
        ) {
            composable(NavRoute.Home.route) {
                Pager(pagerState, navController)
            }
            composable(NavRoute.Detail.route) {
                DetailScreen(onBackClick = { navController.navigateUp() })
            }
        }
        if (isBottomBarVisible) {
            BottomNavigationBar(pagerState)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Pager(
    pagerState: PagerState,
    navController: NavHostController,
) {
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            Log.d("Page change", "Page changed to $page")
        }
    }

    val onArticleItemClick: (Article) -> Unit = {
        navController.navigate(NavRoute.Detail.passArticle(it))
    }

    VerticalPager(state = pagerState) { page ->
        when (page) {
            0 -> TopHeadlinesScreen(onArticleItemClick = onArticleItemClick)
            1 -> SearchScreen(onArticleItemClick = onArticleItemClick)
            2 -> FavoriteScreen(onArticleItemClick = onArticleItemClick)
        }
    }
}
