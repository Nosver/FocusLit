package com.focus.lit.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.request.ImageRequest
import com.focus.lit.R
import com.focus.lit.ui.components.GenericDropdownMenuContent
import com.focus.lit.ui.components.GenericIconButton


@Composable
fun StartSessionScreen(navController: NavHostController) {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components {
            add(GifDecoder.Factory())
        }
        .build()

    var studyMinutes by remember { mutableStateOf("") }
    var breakMinutes by remember { mutableStateOf("") }
    var selectedTopic by remember { mutableStateOf("") }
    var searchQuery by remember { mutableStateOf("") }
    val allTopics = listOf("#Mathematics", "#Physics", "#History", "#Biology", "#ComputerScience", "#English", "#Chemistry", "#Economics")
    val filteredTopics = allTopics.filter {
        it.substring(1).startsWith(searchQuery, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 6.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End){
            GenericIconButton(icon = Icons.Default.Add, onClick = {navController.navigate("add_tag")})
        }
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(R.drawable.giphy) // Use your GIF filename here without extension
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
            onValueChange = { if (it.all(Char::isDigit)) studyMinutes = it },
            label = { Text("Study Duration (minutes)") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = breakMinutes,
            onValueChange = { if (it.all(Char::isDigit)) breakMinutes = it },
            label = { Text("Break Duration (minutes)") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search a Tag for you topic ") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        GenericDropdownMenuContent(filteredTopics, searchQuery) {
            selectedTopic = it
            searchQuery = it
        }


        Button(
            onClick = {
                // Navigate or start session logic here
                navController.navigate("timer?study=$studyMinutes&break=$breakMinutes&topic=$selectedTopic")
            },
            enabled = studyMinutes.isNotEmpty() && breakMinutes.isNotEmpty() && selectedTopic.isNotEmpty(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Start Session")
        }
    }
}

