package com.focus.lit.data.model

import java.time.LocalDateTime

data class Session(
    val userId: Int,
    val tagId: Int,
    val startTime:LocalDateTime,
    val workDuration: Long,          // in seconds or minutes
    val waitDuration: Long,
    val score: Int,
    val scoreMultiplier: Double,
    val completedWorkDuration: Long, // in same units as workDuration
    val isCompleted: Boolean
)
