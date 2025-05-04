package com.focus.lit.data.model

data class CreateSessionRequest(
    val userId:Int,
    val tagId:Int,
    val workDuration: Int,
    val waitDuration:Int
)
