package com.a1573595.newsapp.ui.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.a1573595.newsapp.ui.Dimens
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BottomNavigationBar(
    pagerState: PagerState,
) {
    val coroutineScope = rememberCoroutineScope()

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
        navigationItemList.forEachIndexed { index, navigationItem ->
            NavigationBarItem(
                colors = colors,
                selected = pagerState.currentPage == index,
                icon = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            navigationItem.icon,
                            contentDescription = null,
                        )
                        Spacer(
                            modifier = Modifier.height(Dimens.dp4)
                        )
                        Text(
                            stringResource(navigationItem.labelStringRes),
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                },
                onClick = {
                    coroutineScope.launch { pagerState.scrollToPage(index) }
                },
            )
        }
    }
}