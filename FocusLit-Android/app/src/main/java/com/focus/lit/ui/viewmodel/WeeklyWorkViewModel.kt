package com.focus.lit.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.focus.lit.data.model.WeeklyWorkResponse
import com.focus.lit.data.remote.ApiService
import com.focus.lit.data.local.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeeklyWorkViewModel @Inject constructor(
    private val apiService: ApiService,
    private val tokenManager: TokenManager
) : ViewModel() {

    var weeklyWorkResponse by mutableStateOf<WeeklyWorkResponse?>(null)
        private set

    private var errorMessage by mutableStateOf("")

    private var loading by mutableStateOf(false)

    init {
        fetchWeeklyWork()
    }

    private fun fetchWeeklyWork() {
        viewModelScope.launch {
            loading = true
            try {
                val userId = tokenManager.getId()
                weeklyWorkResponse = userId?.let { apiService.getWeeklyWork(it) }
            } catch (e: Exception) {
                errorMessage = e.message ?: "An unknown error occurred"
            } finally {
                loading = false
            }
        }
    }
}