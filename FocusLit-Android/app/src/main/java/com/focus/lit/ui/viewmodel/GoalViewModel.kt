package com.focus.lit.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.focus.lit.data.local.TokenManager
import com.focus.lit.data.model.CreateGoalRequest
import com.focus.lit.data.model.Goal
import com.focus.lit.data.model.Tag
import com.focus.lit.data.remote.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoalViewModel @Inject constructor(
    private val tokenManager: TokenManager,
    private val apiService: ApiService
) : ViewModel() {
    var showAddGoalDialog by  mutableStateOf(false)

    var parentTag by mutableStateOf("")

    var selectedParentId by mutableIntStateOf(0)

    var targetMinutes by mutableStateOf("")

    var searchQueryTag by mutableStateOf("")

    var searchQueryGoal by mutableStateOf("")

    var allTags by mutableStateOf(listOf<Tag>())
        private set

    var filteredTags by mutableStateOf(listOf<String>())
        private set

    var goals by mutableStateOf(listOf<Goal>())
    public set

    var loading by mutableStateOf(false)

    fun fetchTags() {
        viewModelScope.launch {
            try {
                val tags = apiService.getAllTags()
                allTags = tags
                filteredTags = tags.map { it.name }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateSearchQuery(query: String) {
        searchQueryTag = query
        filteredTags = allTags.map { it.name }.filter {
            it.contains(query, ignoreCase = true)
        }
    }

    fun selectParentTag(tagName: String) {
        parentTag = tagName
        searchQueryTag = tagName
        selectedParentId = allTags.find { it.name.equals(tagName, ignoreCase = true) }?.id!!
    }

    fun fetchGoals() {
        loading = true
        viewModelScope.launch {
            try {
                var userId = tokenManager.getId();
                val allGoals = apiService.getGoals(userId);
                goals = allGoals
            } catch (e: Exception) {
                e.printStackTrace()
            }finally{
                loading = false
            }
        }
    }

    fun createGoal(onSuccess: () -> Unit){
        viewModelScope.launch {
            try {
                var userId = tokenManager.getId();
                var createGoalRequest = CreateGoalRequest(userId = userId, tagId = selectedParentId, targetWorkDuration = targetMinutes.toInt());
                apiService.createGoal(createGoalRequest);
                fetchGoals()
                parentTag = ""
                selectedParentId = 0
                targetMinutes = ""
                searchQueryTag = ""
                onSuccess()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}