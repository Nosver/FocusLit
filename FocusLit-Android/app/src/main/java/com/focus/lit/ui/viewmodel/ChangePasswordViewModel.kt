package com.focus.lit.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.focus.lit.data.local.TokenManager
import com.focus.lit.data.model.ChangePasswordRequest
import com.focus.lit.data.remote.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val apiService: ApiService,
    private val tokenManager: TokenManager
) : ViewModel() {

    var currentPassword by mutableStateOf("")
    var newPassword by mutableStateOf("")
    var confirmPassword by mutableStateOf("")
    var errorMessage by mutableStateOf<String?>(null)
    var successMessage by mutableStateOf<String?>(null)

    fun onChangePasswordClick(onSuccess: () -> Unit) {
        if (newPassword != confirmPassword) {
            errorMessage = "New passwords do not match"
            return
        }

        if (newPassword.isBlank() || currentPassword.isBlank()) {
            errorMessage = "Fields must not be empty"
            return
        }

        viewModelScope.launch {
            try {
                errorMessage = null
                successMessage = null

                val user = apiService.getUser(tokenManager.getId())
                val request = ChangePasswordRequest(
                    name = user.name,
                    password = currentPassword,
                    newPassword = newPassword,
                    mail = user.mail
                )
                apiService.changePassword(request)

                successMessage = "Password changed successfully"
                onSuccess()
                currentPassword = ""
                newPassword = ""
                confirmPassword = ""
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()

                errorMessage = try {
                    val json = JSONObject(errorBody ?: "")
                    Log.e("CHANGE_PWD_ERROR", json.toString())
                    json.getString("message")
                } catch (jsonException: Exception) {
                    "Server error occurred"
                }

            } catch (e: Exception) {
                errorMessage = e.message ?: "Unexpected error occurred"
            }
        }
    }
}
