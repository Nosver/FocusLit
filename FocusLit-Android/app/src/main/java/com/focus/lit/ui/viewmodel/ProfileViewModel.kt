package com.focus.lit.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.focus.lit.R
import com.focus.lit.data.local.TokenManager
import com.focus.lit.data.model.UserInfo
import com.focus.lit.data.model.UserProfileChangeBody
import com.focus.lit.data.remote.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val apiService: ApiService,
    private val tokenManager: TokenManager
) : ViewModel() {

    var userName by mutableStateOf("")

    var email by mutableStateOf("")

    var changedUsername by mutableStateOf("")

    var changedEmail by mutableStateOf("")

    var changeComponentState by mutableStateOf(false)

    var achievementImages = mutableStateListOf<Int>(
        R.drawable.trophy,
        R.drawable.trophy,
        R.drawable.trophy,
        R.drawable.trophy,
        R.drawable.trophy,
        R.drawable.trophy
    )

    var loading by mutableStateOf(false)

    private set

    public fun fetchUserInfo() {
        viewModelScope.launch {
            loading = true
            try {
                val userId = tokenManager.getId();
                val userInfo = apiService.getUser(userId)
                userName = userInfo.name
                email = userInfo.mail
                changedEmail = userInfo.mail
                changedUsername = userInfo.name
            } catch (e: Exception) {
                e.printStackTrace()
            }finally{
                loading = false
            }
        }
    }

    public fun adjustProfile(){
        if(!changeComponentState){
            changeComponentState = !changeComponentState
            return;
        };
        viewModelScope.launch {
            loading = true
            try {
                if (changedEmail == email && changedUsername == userName) {
                    changeComponentState = !changeComponentState
                    return@launch;
                }
                val body = UserProfileChangeBody(tokenManager.getId(), changedEmail, changedUsername);
                apiService.updateUser(body);
                fetchUserInfo()
            }catch (e: Exception){
                e.printStackTrace()
            }finally {
                loading = false
            }
            changeComponentState = !changeComponentState
        }
    }

}
