package com.a1573595.newsapp.common

import androidx.navigation.NavHostController
import androidx.navigation.navOptions
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn
import kotlin.math.abs

fun String.getDaysAgoString(): String {
    val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
    val days = today.daysUntil(
        Instant.parse(this).toLocalDateTime(TimeZone.currentSystemDefault()).date
    )

    return when {
        abs(days) > 1 -> "${abs(days)} days ago"
        abs(days) == 1 -> "Yesterday"
        else -> "Today"
    }
}

fun NavHostController.safeNavigate(
    route: String,
) {
    if (currentDestination?.route !== route) {
        navigate(
            route,
            navOptions {
                popUpTo(graph.startDestinationId) { inclusive = true }
                launchSingleTop = true
            },
        )
    }
}