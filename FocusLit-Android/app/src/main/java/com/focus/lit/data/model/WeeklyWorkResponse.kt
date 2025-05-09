package com.focus.lit.data.model

data class WeeklyWorkResponse(
    val sessions: List<Session>,
    val startDate: String,
    val endDate: String
)
