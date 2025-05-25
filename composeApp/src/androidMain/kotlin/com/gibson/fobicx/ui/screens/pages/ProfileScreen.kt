package com.gibson.fobicx.ui.screens.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileScreen() {
    val isDarkTheme = isSystemInDarkTheme()
    FobicxTheme(isDarkTheme = isDarkTheme) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {

                // Close button
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.TopEnd
                ) {
                    IconButton(onClick = { /* Handle close */ }) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }

                // Profile Header
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.profile_pic), // Replace with your image
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text("Gibson Ezeh", style = MaterialTheme.typography.titleMedium)
                        Text("gibsonezeh6@gmail.com", style = MaterialTheme.typography.bodySmall)
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Free Plan Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Free", style = MaterialTheme.typography.titleMedium)
                            Button(onClick = { /* Handle Upgrade */ }) {
                                Text("Upgrade")
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Credits", style = MaterialTheme.typography.labelMedium)
                        Text("0 • Daily credits refresh at 01:00 every day", style = MaterialTheme.typography.bodySmall)
                    }
                }

                // Section: FOBICX
                SectionTitle("FOBICX")
                MenuItem(icon = Icons.Default.Share, title = "Share with a friend")
                MenuItem(icon = Icons.Default.Book, title = "Knowledge")
                MenuItem(icon = Icons.Default.Language, title = "Language", trailingText = "English")

                // Section: General
                SectionTitle("General")
                MenuItem(icon = Icons.Default.AccountCircle, title = "Account")
                MenuItem(icon = Icons.Default.Brightness4, title = "Appearance", trailingText = "Follow system")

                // Section: Information
                SectionTitle("Information")
                MenuItem(icon = Icons.Default.FavoriteBorder, title = "Rate this app")
                MenuItem(icon = Icons.Default.Email, title = "Contact us")
            }
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.labelMedium,
        color = Color.Gray,
        modifier = Modifier
            .padding(start = 16.dp, top = 24.dp, bottom = 8.dp)
    )
}

@Composable
fun MenuItem(
    icon: ImageVector,
    title: String,
    trailingText: String? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Handle click */ }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = title, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyMedium
        )
        trailingText?.let {
            Text(it, style = MaterialTheme.typography.bodySmall)
        }
        Icon(Icons.Default.ChevronRight, contentDescription = "Go", modifier = Modifier.size(20.dp))
    }
}
