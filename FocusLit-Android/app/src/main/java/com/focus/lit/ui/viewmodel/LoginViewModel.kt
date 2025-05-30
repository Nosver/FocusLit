package com.focus.lit.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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

    var loading by mutableStateOf(false)

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
            loading = true;
            try {
                val response = apiService.login(
                    LoginRequest(_email.value, _password.value)
                )

                tokenManager.saveToken(response.token)
                tokenManager.saveId(response.userId)
                _loginResult.value = response
                onSuccess()

            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()

                val errorMessage = try {
                    val json = JSONObject(errorBody ?: "")
                    Log.d("LOGIN_ERROR", json.toString())
                    json.getString("message")
                } catch (jsonException: Exception) {
                    "Unknown server error"
                }

                onError(errorMessage)

            } catch (e: Exception) {
                onError(e.message ?: "Unknown error")
            }finally {
                loading = false
            }
        }
    }


}
