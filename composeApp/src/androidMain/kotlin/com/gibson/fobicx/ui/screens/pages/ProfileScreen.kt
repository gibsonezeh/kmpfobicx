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
fun ProfileScreen(onLogout: () -> Unit = {}) {
    val itemsFobicx = listOf(
        "Share with a friend" to Icons.Default.Share,
        "Knowledge" to Icons.Default.MenuBook,
        "Language" to Icons.Default.Language
    )

    val itemsGeneral = listOf(
        "Account" to Icons.Default.AccountCircle,
        "Appearance" to Icons.Default.Brightness6
    )

    val itemsInfo = listOf(
        "Rate this app" to Icons.Default.FavoriteBorder,
        "Contact us" to Icons.Default.Email
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        // Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(Color.DarkGray)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text("Gibson Ezeh", style = MaterialTheme.typography.titleMedium)
                Text("gibsonezeh6@gmail.com", style = MaterialTheme.typography.bodySmall)
            }
        }

        // Free Tier Card
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Free", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Credits", style = MaterialTheme.typography.bodyMedium)
                    Text("Daily credits refresh at 01:00 every day", style = MaterialTheme.typography.bodySmall)
                }
                Button(onClick = { /* Upgrade action */ }) {
                    Text("Upgrade")
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // FOBICX Section
        Text("FOBICX", style = MaterialTheme.typography.labelMedium)
        itemsFobicx.forEach { (label, icon) ->
            ProfileItem(label, icon)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // General Section
        Text("General", style = MaterialTheme.typography.labelMedium)
        itemsGeneral.forEach { (label, icon) ->
            ProfileItem(label, icon, trailingText = if (label == "Appearance") "Follow system" else null)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Information Section
        Text("Information", style = MaterialTheme.typography.labelMedium)
        itemsInfo.forEach { (label, icon) ->
            ProfileItem(label, icon)
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onLogout,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Logout")
        }
    }
}

@Composable
fun ProfileItem(title: String, icon: ImageVector, trailingText: String? = null) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null)
        Spacer(modifier = Modifier.width(16.dp))
        Text(title, modifier = Modifier.weight(1f))
        if (trailingText != null) {
            Text(trailingText, style = MaterialTheme.typography.bodySmall)
        } else {
            Icon(Icons.Default.KeyboardArrowRight, contentDescription = null)
        }
    }
}
