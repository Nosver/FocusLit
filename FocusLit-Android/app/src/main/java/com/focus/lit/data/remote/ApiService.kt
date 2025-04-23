package com.focus.lit.data.remote

import com.focus.lit.data.model.LoginRequest
import com.focus.lit.data.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

}