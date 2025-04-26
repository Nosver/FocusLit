package com.focus.lit.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.focus.lit.ui.components.GenericButton
import com.focus.lit.ui.components.GenericDropdownMenuContent
import com.focus.lit.ui.viewmodel.AddTagViewModel

@Composable
fun AddTagScreen(navController: NavController, viewModel: AddTagViewModel = hiltViewModel()){
    Column(modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp, vertical = 20.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        OutlinedTextField(
            value = viewModel.newTag,
            onValueChange = { viewModel.newTag = it },
            label = { Text("Enter New tag") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = viewModel.searchQuery,
            onValueChange = { viewModel.updateSearchQuery(it) },
            label = { Text("Select Parent Tag") },
            modifier = Modifier.fillMaxWidth()
        )
        GenericDropdownMenuContent(options = viewModel.filteredTags, query = viewModel.searchQuery){
            viewModel.parentTag = it
            viewModel.searchQuery = it
        }
        Spacer(modifier = Modifier.height(30.dp))
        GenericButton(text = "Create Tag", modifier = Modifier.fillMaxWidth(), enabled = (viewModel.newTag.isNotEmpty() && viewModel.parentTag.isNotEmpty()))
    }
}