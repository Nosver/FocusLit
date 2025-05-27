package com.focus.lit.ui.view

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.focus.lit.R
import com.focus.lit.ui.components.GenericButton
import com.focus.lit.ui.components.GenericDropdownMenuContent
import com.focus.lit.ui.viewmodel.AddTagViewModel

@Composable
fun AddTagScreen(navController: NavController, viewModel: AddTagViewModel = hiltViewModel()) {
    val context = LocalContext.current

    LaunchedEffect(viewModel.errorMessage) {
        viewModel.errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.errorMessage = null
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.tag_icon),
            contentDescription = "Tag Icon",
            modifier = Modifier
                .height(100.dp)
                .padding(bottom = 24.dp),
            contentScale = ContentScale.Fit
        )

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

        GenericDropdownMenuContent(options = viewModel.filteredTags, query = viewModel.searchQuery) {
            viewModel.selectParentTag(it)
        }

        Spacer(modifier = Modifier.height(30.dp))

        GenericButton(
            text = "Create Tag",
            modifier = Modifier.fillMaxWidth(),
            enabled = viewModel.newTag.isNotEmpty() && viewModel.parentTag.isNotEmpty(),
            onClick = {
                viewModel.createTag {
                    Toast.makeText(context, "Tag created successfully", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }
}
