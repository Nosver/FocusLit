package com.focus.lit.data.remote

import com.focus.lit.data.local.TokenManager
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val tokenManager: TokenManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestUrl = request.url.toString()
        val isAuthFree = requestUrl.contains("/login") || requestUrl.contains("/register")
        val newRequest = if (isAuthFree) {
            request
        } else {
            val token = runBlocking { tokenManager.getToken() }
            request.newBuilder().apply {
                token?.let {
                    addHeader("Authorization", "Bearer $it")
                }
            }.build()
        }

        return chain.proceed(newRequest)
    }
}

