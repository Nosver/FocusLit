package com.focus.lit.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.focus.lit.data.model.EndSessionRequest
import com.focus.lit.data.remote.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EndSessionViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun endSession(sessionId: Int, completedMinutes: Int) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null
                
                val request = EndSessionRequest(
                    sessionId = sessionId,
                    completedMinutes = completedMinutes
                )
                
                apiService.endSession(request)
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to end session"
            } finally {
                _isLoading.value = false
            }
        }
    }
}