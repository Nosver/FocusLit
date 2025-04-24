package com.focus.lit.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.focus.lit.data.model.LoginRequest
import com.focus.lit.data.model.LoginResponse
import com.focus.lit.data.remote.ApiClient 
import org.json.JSONObject
import retrofit2.HttpException

class LoginViewModel() : ViewModel() {

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
                val response = ApiClient.apiService.login(
                    LoginRequest(_email.value, _password.value)
                )
                _loginResult.value = response
                onSuccess()
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errorMessage = try {
                    val json = JSONObject(errorBody ?: "")
                    json.getString("message")
                } catch (jsonException: Exception) {
                    "Unknown server error"
                }
                onError(errorMessage)
            } catch (e: Exception) {
                onError(e.message ?: "Unknown error")
            }
        }
    }

}
