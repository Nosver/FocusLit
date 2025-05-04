package com.focus.lit.data.model

data class UserAnalyticsResponse(
    val id: Int,
    val score: Double,
    val streak: Int,
    val totalWorkDuration: Double,
    val userRank: Int
)