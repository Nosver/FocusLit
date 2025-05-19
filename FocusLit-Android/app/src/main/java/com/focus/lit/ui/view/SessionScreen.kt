package com.focus.lit.ui.view

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.request.ImageRequest
import com.focus.lit.R
import com.focus.lit.data.model.Tag
import com.focus.lit.ui.components.GenericDropdownMenuContent
import com.focus.lit.ui.components.GenericIconButton
import com.focus.lit.ui.viewmodel.RegisterViewModel
import com.focus.lit.ui.viewmodel.SessionViewModel

@Composable
fun StartSessionScreen(navController: NavHostController, viewModel: SessionViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components {
            add(GifDecoder.Factory())
        }
        .build()

    val studyMinutes by viewModel.studyMinutes.collectAsState()
    val breakMinutes by viewModel.breakMinutes.collectAsState()
    val selectedTag by viewModel.selectedTag.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val allTopics = viewModel.allTopics.collectAsState()
    val sessionId= viewModel.sessionId.collectAsState()

    val filteredTopics = allTopics.value
        .filter { it.name.startsWith(searchQuery, ignoreCase = true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 6.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            GenericIconButton(icon = Icons.Default.Add, onClick = { navController.navigate("add_tag") })
        }

        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(R.drawable.giphy)
                .build(),
            contentDescription = "Study Animation",
            imageLoader = imageLoader,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )

        Text("Start a Study Session", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(
            value = studyMinutes,
            onValueChange = { value ->
                if (value.isEmpty() || value.toIntOrNull() ?: 0 > 0) {
                    viewModel.onStudyMinutesChange(value)
                }
            },
            label = { Text("Study Duration (minutes)") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = breakMinutes,
            onValueChange = { value ->
                if (value.isEmpty() || value.toIntOrNull() ?: 0 > 0) {
                    viewModel.onBreakMinutesChange(value)
                }
            },
            label = { Text("Break Duration (minutes)") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = searchQuery,
            onValueChange = viewModel::onSearchQueryChange,
            label = { Text("Search a Tag for your topic") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        TagDropdownMenuContent(filteredTopics, searchQuery) { selectedTag ->
            viewModel.onSelectedTagChange(selectedTag)
            viewModel.onSearchQueryChange(selectedTag.name)
        }

        Button(
            onClick = {
                viewModel.createSession(
                    onSuccess = {
                        val currentSessionId = viewModel.sessionId.value
                        navController.navigate("timer?study=$studyMinutes&break=$breakMinutes&topic=${selectedTag.name}&id=${selectedTag.id}&sessionId=$currentSessionId")
                    },
                    onError = {
                        err -> Toast.makeText(context,"Unable to create session: ${err}  ", Toast.LENGTH_SHORT).show()
                    }
                )
            },
            enabled = studyMinutes.isNotEmpty() && breakMinutes.isNotEmpty() && selectedTag.name.isNotEmpty(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Start Session")
        }
    }
}



@Composable
fun TagDropdownMenuContent(
    options: List<Tag>,
    query: String,
    onSelect: (Tag) -> Unit
) {
    if (query.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 150.dp)
                .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(8.dp))
                .border(1.dp, MaterialTheme.colorScheme.outline, shape = RoundedCornerShape(8.dp))
        ) {
            items(options) { tag ->
                DropdownMenuItem(
                    text = { Text(tag.name) },
                    onClick = { onSelect(tag) }
                )
            }
        }
    }
}



