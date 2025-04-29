package com.focus.lit.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.focus.lit.R
import com.focus.lit.ui.viewmodel.ProfileViewModel
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextField
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.focus.lit.ui.components.GenericIconButton
import com.focus.lit.ui.components.ImagePagerCarousel


@Composable
fun ProfileScreen(navController: NavController, viewModel: ProfileViewModel = hiltViewModel()) {
    val navBackStackEntry = remember { navController.currentBackStackEntry }

    LaunchedEffect(navBackStackEntry) {
        viewModel.fetchUserInfo()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Absolute.SpaceBetween) {
                GenericIconButton(icon = Icons.Default.Settings, onClick = { navController.navigate("settings") })
                if(viewModel.changeComponentState){
                    GenericIconButton(icon = Icons.Default.Clear, onClick = {viewModel.changeComponentState = !viewModel.changeComponentState})
                }
                GenericIconButton(icon = Icons.Default.Create, onClick = {viewModel.adjustProfile()})

            }
            Spacer(modifier = Modifier.height(30.dp))
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.Gray, CircleShape)
            )
            BasicTextField(
                value = viewModel.changedUsername,
                onValueChange = {viewModel.changedUsername = it},
                modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
                textStyle = TextStyle(
                fontSize = 18.sp,
                color = Color.Black,
                textAlign = TextAlign.Center),
                enabled = viewModel.changeComponentState
                )
            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth(),
                thickness = 1.dp,
                color = Color.LightGray
            )
        }
        Column(
            modifier = Modifier
                .weight(2f)
                .padding(25.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = "Email", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            BasicTextField(
                value = viewModel.changedEmail,
                onValueChange = {viewModel.changedEmail = it},
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(
                    fontSize = 15.sp,
                    color = Color.Blue),
                enabled = viewModel.changeComponentState
            )
            Spacer(modifier = Modifier.height(40.dp))
            Text(text = "Achievements", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(10.dp))
            ImagePagerCarousel(viewModel.achievementImages)
            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}