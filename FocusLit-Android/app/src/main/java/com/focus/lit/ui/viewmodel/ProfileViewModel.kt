package com.focus.lit.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.focus.lit.R

class ProfileViewModel : ViewModel() {

    var userName by mutableStateOf("John Doe")
        private set  // Encapsulating the state

    var email by mutableStateOf("johndoe@gmail.com")
        private set

    var achievementImages = mutableStateListOf<Int>(
        R.drawable.trophy,
        R.drawable.trophy,
        R.drawable.trophy,
        R.drawable.trophy,
        R.drawable.trophy,
        R.drawable.trophy
    )
    private set

    fun updateProfile(newName: String, newEmail: String) {
        userName = newName
        email = newEmail
    }

}
