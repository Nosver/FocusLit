package com.focus.lit.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddTagViewModel @Inject constructor() : ViewModel() {

    var parentTag by mutableStateOf("")

    var newTag by mutableStateOf("")

    var selectedTopic by mutableStateOf("")

    var searchQuery by mutableStateOf("")

    val allTags = listOf("YKS", "ALES", "YDS")

    var filteredTags by mutableStateOf(allTags)
        private set

    fun updateSearchQuery(query: String) {
        searchQuery = query
        filteredTags = allTags.filter {
            it.contains(query, ignoreCase = true)
        }
    }

}

