package com.gibson.fobicx.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.graphics.Color

@Composable
fun BottomNavBar(
    onItemClick: (String) -> Unit,
    maxWidth: Dp = 500.dp // Ensures it stays centered and constrained on tablets
) {
    val items = listOf("Home", "Market", "Post", "Stock", "Me")
    val icons = listOf(Icons.Default.Home, Icons.Default.ShoppingCart, Icons.Default.Add, Icons.Default.List, Icons.Default.Person)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .widthIn(max = maxWidth)
        ) {
            // Background Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .clip(RoundedCornerShape(30.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                items.forEachIndexed { index, item ->
                    if (item == "Post") {
                        Spacer(modifier = Modifier.width(48.dp)) // Leave space for center button
                    } else {
                        IconButton(onClick = { onItemClick(item) }) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    imageVector = icons[index],
                                    contentDescription = item
                                )
                                Text(
                                    text = item,
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                        }
                    }
                }
            }

            // Center Floating Post Button
            FloatingActionButton(
                onClick = { onItemClick("Post") },
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(64.dp)
                    .align(Alignment.Center)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Post")
            }
        }
    }
}
