package com.focus.lit.data.repository

import android.util.Log
import com.focus.lit.data.model.LoginRequest
import com.focus.lit.data.model.LoginResponse
import com.focus.lit.data.remote.ApiService

class AuthRepository(private val apiService: ApiService) {

    suspend fun login(email: String, password: String): LoginResponse {
        val request = LoginRequest(email, password)
        val response = apiService.login(request)
        return response
    }
}
