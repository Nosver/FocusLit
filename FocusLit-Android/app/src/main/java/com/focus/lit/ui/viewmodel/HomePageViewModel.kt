package com.focus.lit.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.State

class HomePageViewModel : ViewModel() {

    private val _streak = mutableStateOf(0)
    val streak: State<Int> = _streak

    private val _totalWorkDuration = mutableStateOf(0)
    val totalWorkDuration: State<Int> = _totalWorkDuration

    private val _score = mutableStateOf(0)
    val score: State<Int> = _score

    private val _userRank = mutableStateOf(0)
    val userRank: State<Int> = _userRank

    private val _achievements = mutableStateOf(listOf<String>())
    val achievements: State<List<String>> = _achievements

    init {
        fetchUserAnalytics()
    }

    private fun fetchUserAnalytics() {
        viewModelScope.launch {
            //
            // TODO: Handle API call
            //
            _streak.value = 10
            _totalWorkDuration.value = 500
            _score.value = 1500
            _userRank.value = 5
            _achievements.value = listOf("Achievement 1", "Achievement 2")
        }
    }
}