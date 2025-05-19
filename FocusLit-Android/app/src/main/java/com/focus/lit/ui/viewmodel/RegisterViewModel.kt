package com.focus.lit.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.focus.lit.data.local.TokenManager
import com.focus.lit.data.model.LoginRequest
import com.focus.lit.data.model.RegisterRequest
import com.focus.lit.data.remote.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException
import java.lang.Error
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val tokenManager: TokenManager,
    private val apiService: ApiService
): ViewModel() {

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _passwordConfirm = MutableStateFlow("")
    val passwordConfirm: StateFlow<String> = _passwordConfirm

    var loading by mutableStateOf(false)

    fun onEmailChanged(value: String) {
        _email.value = value
    }

    fun onPasswordChanged(value: String) {
        _password.value = value
    }

    fun onUsernameChanged(value: String){
        _username.value=value
    }

    fun onPasswordConfirmChange(value: String){
        _passwordConfirm.value=value
    }

    fun register(onSuccess: () -> Unit,onError: (String) -> Unit ){
        val username = _username.value
        val password = _password.value
        val confirmPassword = _passwordConfirm.value
        val email = _email.value

        // Check if passwords match
        if (password != confirmPassword) {
            onError("Passwords do not match")
            return
        }

        // Check password strength
//        if (!isPasswordStrong(password)) {
//            onError("Password must be at least 8 characters and include a number, a special character, and an uppercase letter")
//            return
//        }

        viewModelScope.launch {
            loading = true
            try {
                val response = apiService.register(
                    RegisterRequest(_username.value,_password.value,_email.value)
                )
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
            } finally{
                loading = false
            }
        }
    }

    private fun isPasswordStrong(password: String): Boolean {
        val lengthRequirement = password.length >= 8
        val hasDigit = password.any { it.isDigit() }
        val hasUpperCase = password.any { it.isUpperCase() }
        val hasSpecialChar = password.any { !it.isLetterOrDigit() }

        return lengthRequirement && hasDigit && hasUpperCase && hasSpecialChar
    }
}