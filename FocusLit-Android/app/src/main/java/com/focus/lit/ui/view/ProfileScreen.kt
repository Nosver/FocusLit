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
import androidx.compose.material.icons.Icons
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
import androidx.navigation.NavController
import com.focus.lit.ui.components.GenericIconButton
import com.focus.lit.ui.components.ImagePagerCarousel


@Composable
fun ProfileScreen(navController: NavController, viewModel: ProfileViewModel = viewModel()) {

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

               GenericIconButton(icon = Icons.Default.Create )

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
            Text(
                text = viewModel.userName,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(5.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
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
            Text(text = viewModel.email, color = Color.Blue, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(40.dp))
            Text(text = "Achievements", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(10.dp))
            ImagePagerCarousel(viewModel.achievementImages)
            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}