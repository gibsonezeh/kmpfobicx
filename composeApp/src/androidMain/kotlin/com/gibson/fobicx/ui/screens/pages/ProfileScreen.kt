package com.gibson.fobicx.ui.screens.pages

import androidx.compose.runtime.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = viewModel()
) {
    val user by viewModel.userProfile.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        // Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Image(
                painter = rememberImagePainter(user.avatarUrl ?: "https://via.placeholder.com/64"),
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(64.dp)
                    .clip(MaterialTheme.shapes.medium)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = user.name, color = Color.White, fontSize = MaterialTheme.typography.h6.fontSize)
                Text(text = user.email, color = Color.Gray)
            }
        }

        // Plan Card
        Card(
            backgroundColor = Color.DarkGray,
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Free", color = Color.White, style = MaterialTheme.typography.body1)
                    Text("Credits", color = Color.Gray)
                    Text("0 ★", color = Color.White)
                    Text("Daily credits refresh at 01:00", color = Color.Gray, fontSize = 12.sp)
                }
                Button(onClick = { /* Upgrade logic */ }) {
                    Text("Upgrade")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Menu sections
        SettingsSection("Menus") {
            SettingsItem("Share with a friend") {}
            SettingsItem("Knowledge") {}
            SettingsItem("Language", rightText = "English") {}
        }

        SettingsSection("General") {
            SettingsItem("Account") {
                navController.navigate("account_details")
            }
            SettingsItem("Appearance", rightText = "Follow system") {}
            SettingsItem("Clear cache", rightText = "2 MB") {}
        }

        SettingsSection("Information") {
            SettingsItem("Rate this app") {}
            SettingsItem("Contact us") {}
        }
    }
}
