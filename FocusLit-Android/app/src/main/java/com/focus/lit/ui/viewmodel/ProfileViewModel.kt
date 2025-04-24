package com.focus.lit.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.focus.lit.R
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() : ViewModel() {

    var userName by mutableStateOf("Jane Doe")

    var email by mutableStateOf("janedoe@gmail.com")

    var changeComponentState by mutableStateOf(false)

    var achievementImages = mutableStateListOf<Int>(
        R.drawable.trophy,
        R.drawable.trophy,
        R.drawable.trophy,
        R.drawable.trophy,
        R.drawable.trophy,
        R.drawable.trophy
    )
    private set

    fun changeComponentState(){
        changeComponentState = !changeComponentState
    }

}
