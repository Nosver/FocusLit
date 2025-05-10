package com.focus.lit.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.focus.lit.data.local.TokenManager
import com.focus.lit.data.model.CreateSessionRequest
import com.focus.lit.data.model.Tag
import com.focus.lit.data.model.UserAnalyticsResponse
import com.focus.lit.data.remote.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.http.Query
import javax.inject.Inject

@HiltViewModel
class SessionViewModel @Inject constructor(
    private val tokenManager: TokenManager,
    private val apiService: ApiService
):ViewModel() {



    private val _studyMinutes = MutableStateFlow("")
    val studyMinutes:StateFlow<String> =_studyMinutes

    private val _breakMinutes = MutableStateFlow("")
    val breakMinutes:StateFlow<String> =_breakMinutes;

    private val _selectedTopic = MutableStateFlow("")
    val selectedTopic:StateFlow<String> = _selectedTopic

    private val _selectedTag = MutableStateFlow(Tag(-1,""))
    val selectedTag:StateFlow<Tag> = _selectedTag;

    private val _searchQuery = MutableStateFlow("")
    val searchQuery:StateFlow<String> = _searchQuery

    private val _allTopics = MutableStateFlow<List<Tag>>(emptyList())
    val allTopics: StateFlow<List<Tag>> = _allTopics

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _sessionId = MutableStateFlow<Int>(-1)
    val sessionId: StateFlow<Int> = _sessionId

    init{
        fetchTags()

    }

    private fun fetchTags() {
        viewModelScope.launch {
            try {
                val tags = apiService.getAllTags()
                _allTopics.value = tags
                //_allTopics.value = listOf("Math", "Science", "History") // hardcoded fallback

            } catch (e: Exception) {
                e.printStackTrace()
                _errorMessage.value = "Failed to load tags: ${e.message}"
            }
        }
    }

    fun onStudyMinutesChange(value: String) {
        _studyMinutes.value = value
    }

    fun onBreakMinutesChange(value: String) {
        _breakMinutes.value = value
    }

    fun onSelectedTopicChange(value: String) {
        _selectedTopic.value = value
    }

    fun onSearchQueryChange(value: String) {
        _searchQuery.value = value
    }

    fun onSelectedTagChange(value: Tag){
        _selectedTag.value=value
    }

    fun onErrorMessageChange(message: String?) {
        _errorMessage.value = message
    }

    fun sessionIdChange(value: Int) {
        _sessionId.value = value
    }

     fun createSession(onSuccess: () ->Unit, onError: (String) -> Unit ){
        viewModelScope.launch {
            try{
                val studyDuration = studyMinutes.value.toInt()
                val breakDuration = breakMinutes.value.toInt()
                
                if (studyDuration <= 0 || breakDuration <= 0) {
                    onError("Study duration and break duration must be greater than 0")
                    return@launch
                }

                val response = apiService.createSession(
                    CreateSessionRequest(tokenManager.getId() ?: -1,
                        selectedTag.value.id, studyDuration, breakDuration
                    )
                )
                _sessionId.value = response.id
                // Wait for the state to be updated
                delay(100)
                onSuccess()
            }catch (e : HttpException){
                val errorBody = e.response()?.errorBody()?.string()
                val errorMessage = try {
                    val json = JSONObject(errorBody ?: "")
                    json.getString("message")
                } catch (jsonException: Exception) {
                    "Unknown server error"
                }
                onError(errorMessage)
            }catch (e: Exception) {
                onError(e.message ?: "Unknown error")
            }
        }
    }





}
