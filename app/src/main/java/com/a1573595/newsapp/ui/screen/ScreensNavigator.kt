package com.a1573595.newsapp.ui.screen

import androidx.navigation.NavHostController
import com.a1573595.newsapp.ui.navigation.NavRoute
import com.hitrust.oobdemo.common.base64.Base64EncodeDecode.decodeFromBase64
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ScreensNavigator {
    private val scope = CoroutineScope(Dispatchers.Main.immediate)
    private lateinit var parentNavController: NavHostController
    val currentRoute = MutableStateFlow<NavRoute?>(null)
    private var parentNavControllerObserveJob: Job? = null

    fun setParentNavController(navController: NavHostController) {
        parentNavController = navController

        parentNavControllerObserveJob?.cancel()
        parentNavControllerObserveJob = scope.launch {
            navController.currentBackStackEntryFlow.map { backStackEntry ->
                val route = when (val routeName = backStackEntry.destination.route) {
                    NavRoute.Detail().route -> {
                        val args = backStackEntry.arguments
                        NavRoute.Detail(
                            args?.getString("article")!!.decodeFromBase64(),
                        )
                    }
                    null -> null
                    else -> null
//                    else -> throw RuntimeException("unsupported bottom tab: $routeName")
                }
                currentRoute.value = route
            }.collect()
        }
    }

    fun toRoute(route: NavRoute) {
        parentNavController.navigate(route.navCommand)
    }

}