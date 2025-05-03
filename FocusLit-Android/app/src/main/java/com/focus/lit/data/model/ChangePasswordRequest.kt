package com.focus.lit.data.model

data class ChangePasswordRequest(
    val name: String,
    val password: String,
    val newPassword: String,
    val mail: String
)
