package com.example.focusflow.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.focusflow.viewmodel.AuthViewModel

@Composable
fun HomeScreen(
    authViewModel: AuthViewModel,
    onLogout: () -> Unit
) {

    // theme colours
    val navyBlue = Color(0xFF071B49)
    val royalBlue = Color(0xFF1769E8)
    val purple = Color(0xFF7625D8)
    val secondaryText = Color(0xFF64748B)

    // gradient background
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFF1F6FF),
                        Color.White,
                        Color(0xFFF5F0FF)
                    )
                )
            )
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // Welcome message
        Text(
            text = "Welcome to FocusFlow!",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.ExtraBold,
            color = navyBlue
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Description
        Text(
            text = "Your study planning dashboard will appear here.",
            style = MaterialTheme.typography.bodyMedium,
            color = secondaryText
        )

        // Spacing
        Spacer(modifier = Modifier.height(32.dp))

        // Logout button
        Button(
            onClick = {
                authViewModel.logout()
                onLogout()
            },
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = royalBlue,
                contentColor = Color.White
            ),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 4.dp,
                pressedElevation = 1.dp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            // Logout button text
            Text(
                text = "Logout",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}