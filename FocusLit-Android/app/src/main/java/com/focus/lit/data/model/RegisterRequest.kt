package com.focus.lit.data.model

data class RegisterRequest(
    val name: String,
    val password: String,
    val mail: String
)