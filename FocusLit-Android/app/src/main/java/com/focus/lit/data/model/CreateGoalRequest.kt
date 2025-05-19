package com.focus.lit.data.model

data class CreateGoalRequest(
    val targetWorkDuration: Int,
    val tagId: Int,
    val userId: Int?
)
