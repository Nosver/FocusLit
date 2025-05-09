package com.focus.lit.data.remote

import com.focus.lit.data.model.AddTagRequest
import com.focus.lit.data.model.ChangePasswordRequest
import com.focus.lit.data.model.CreateSessionRequest
import com.focus.lit.data.model.LoginRequest
import com.focus.lit.data.model.LoginResponse
import com.focus.lit.data.model.RegisterRequest
import com.focus.lit.data.model.Tag
import com.focus.lit.data.model.UserAnalytics
import com.focus.lit.data.model.UserAnalyticsResponse
import com.focus.lit.data.model.UserInfo
import com.focus.lit.data.model.UserProfileChangeBody
import com.focus.lit.data.model.WeeklyWorkResponse
import com.focus.lit.ui.viewmodel.HomePageViewModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("register")
    suspend fun register(@Body request: RegisterRequest)

    @GET("user/get")
    suspend fun getUser(@Query("id") userId: Int?): UserInfo

    @POST("user/update")
    suspend fun updateUser(@Body request: UserProfileChangeBody)

    @POST("tag/create")
    suspend fun createTag(@Body request: AddTagRequest)

    @GET("tag/getAll")
    suspend fun getAllTags():List<Tag>

    @POST("user/changePassword")
    suspend fun changePassword(@Body request: ChangePasswordRequest)

    @GET("userAnalytics/getWeeklyWork")
    suspend fun getWeeklyWork(@Query("userId") userId: Int): WeeklyWorkResponse

    @GET("userAnalytics/get")
    suspend fun getUserAnalytics(@Query("userId") userId: Int): UserAnalytics

    @POST("session/create")
    suspend fun createSession(@Body request: CreateSessionRequest)


}