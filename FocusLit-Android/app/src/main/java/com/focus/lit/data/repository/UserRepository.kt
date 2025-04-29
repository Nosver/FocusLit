package com.focus.lit.data.repository

import com.focus.lit.data.model.UserInfo
import com.focus.lit.data.remote.ApiService

class UserRepository(private val apiService: ApiService) {
    suspend fun getUser(userId: Int): UserInfo {
        return apiService.getUser(userId)
    }
}
