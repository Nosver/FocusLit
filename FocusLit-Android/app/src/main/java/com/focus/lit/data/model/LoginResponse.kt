package com.focus.lit.data.model

data class LoginResponse(
    val token: String,
    val message: String,
    val role: String
    )