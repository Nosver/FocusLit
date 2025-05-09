package com.focus.lit.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.State
import com.focus.lit.data.local.TokenManager
import com.focus.lit.data.remote.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.http.Query
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor(
    private val tokenManager: TokenManager,
    private val apiService: ApiService
) : ViewModel() {

    private val _streak = mutableStateOf(0)
    val streak: State<Int> = _streak

    private val _totalWorkDuration = mutableStateOf(0.0)
    val totalWorkDuration: State<Double> = _totalWorkDuration

    private val _score = mutableStateOf(0.0)
    val score: State<Double> = _score

    private val _userRank = mutableStateOf(0)
    val userRank: State<Int> = _userRank

    private val _achievements = mutableStateOf(listOf<String>())
    val achievements: State<List<String>> = _achievements

    init{
        fetchUserAnalytics()
    }

    private fun fetchUserAnalytics() {
        viewModelScope.launch {
            try {
                val userId = tokenManager.getId()
                val response = apiService.getUserAnalytics(userId)
                _streak.value = response.streak
                _totalWorkDuration.value = response.totalWorkDuration
                _score.value = response.score
                _userRank.value = response.userRank
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}