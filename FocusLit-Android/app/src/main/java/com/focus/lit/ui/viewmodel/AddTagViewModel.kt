package com.focus.lit.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.focus.lit.data.model.AddTagRequest
import com.focus.lit.data.model.Tag
import com.focus.lit.data.remote.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTagViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {

    var parentTag by mutableStateOf("")

    var newTag by mutableStateOf("")

    var selectedParentId: Int? = null

    var errorMessage by mutableStateOf<String?>(null)

    var searchQuery by mutableStateOf("")

    var allTags by mutableStateOf(listOf<Tag>())
        private set

    var filteredTags by mutableStateOf(listOf<String>())
        private set

    init {
        fetchTags()
    }

    fun fetchTags() {
        viewModelScope.launch {
            try {
                val tags = apiService.getAllTags()
                allTags = tags
                filteredTags = tags.map { it.name }
            } catch (e: Exception) {
                e.printStackTrace()
                errorMessage = "Failed to load tags: ${e.message}"
            }
        }
    }

    fun updateSearchQuery(query: String) {
        searchQuery = query
        filteredTags = allTags.map { it.name }.filter {
            it.contains(query, ignoreCase = true)
        }
    }

    fun selectParentTag(tagName: String) {
        parentTag = tagName
        searchQuery = tagName
        selectedParentId = allTags.find { it.name.equals(tagName, ignoreCase = true) }?.id
    }

    fun createTag(onSuccess: () -> Unit) {
        val parentId = selectedParentId ?: return
        val tagName = newTag.trim()
        if (tagName.isEmpty()) return

        viewModelScope.launch {
            try {
                apiService.createTag(AddTagRequest(tagName, parentId))
                newTag = ""
                parentTag = ""
                searchQuery = ""
                fetchTags()
                onSuccess()
            } catch (e: Exception) {
                e.printStackTrace()
                errorMessage = "Failed to create tag: ${e.message}"
            }
        }
    }
}

