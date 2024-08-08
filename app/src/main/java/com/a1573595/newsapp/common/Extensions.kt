package com.a1573595.newsapp.common

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