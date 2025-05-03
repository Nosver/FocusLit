package com.focus.lit.data.model

data class LoginResponse(
    val userId:Int,
    val token: String,
    val message: String,
    val role: String
    )