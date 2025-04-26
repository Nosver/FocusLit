package com.focus.lit.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.focus.lit.data.AppModule
import com.focus.lit.data.local.TokenManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.focus.lit.data.model.LoginRequest
import com.focus.lit.data.model.LoginResponse
import com.focus.lit.data.remote.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val tokenManager: TokenManager,
    private val apiService: ApiService
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _loginResult = MutableStateFlow<LoginResponse?>(null)
    val loginResult: StateFlow<LoginResponse?> = _loginResult

    fun onEmailChanged(value: String) {
        _email.value = value
    }

    fun onPasswordChanged(value: String) {
        _password.value = value
    }

    fun login(onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                // Perform the login API call
                val response = apiService.login(
                    LoginRequest(_email.value, _password.value)
                )

                // No need to check token here. If we got here, login was successful
                tokenManager.saveToken(response.token)  // Save the token
                _loginResult.value = response
                onSuccess()  // Proceed to the success path

            } catch (e: HttpException) {
                // Handle HTTP errors (e.g., 400 Bad Request from backend)
                val errorBody = e.response()?.errorBody()?.string()

                // Parse the error message from the server's response
                val errorMessage = try {
                    val json = JSONObject(errorBody ?: "")
                    Log.d("LOGIN_ERROR", json.toString()) // Log response for debugging
                    json.getString("message")  // Extract the error message sent from the backend
                } catch (jsonException: Exception) {
                    "Unknown server error"
                }

                // Show the error message to the user
                onError(errorMessage)

            } catch (e: Exception) {
                // Handle unexpected errors (e.g., network issues)
                onError(e.message ?: "Unknown error")
            }
        }
    }


}
