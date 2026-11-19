package com.example.focusflow.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
    onOpenTasks: () -> Unit,
    onLogout: () -> Unit
) {

    val navyBlue = Color(0xFF142B52)
    val royalBlue = Color(0xFF356AE6)
    val lightBlue = Color(0xFFF1F5FF)
    val textGray = Color(0xFF64748B)

    // Main layout
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        lightBlue,
                        Color.White,
                        Color(0xFFF8FAFC)
                    )
                )
            )
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(85.dp))

        // Welcome message
        Text(
            text = "Welcome to FocusFlow",
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = textGray
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Main heading
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Stay Focused.",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.ExtraBold,
                color = navyBlue
            )

            Text(
                text = "Achieve More.",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.ExtraBold,
                color = royalBlue
            )
        }

        Spacer(modifier = Modifier.height(25.dp))

        // Dashboard card
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 3.dp
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(22.dp),
                horizontalAlignment = Alignment.Start
            ) {

                Text(
                    text = "Your Productivity Starts Here",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = navyBlue
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Organise your tasks, stay focused, and achieve your daily goals.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = textGray,
                    lineHeight = MaterialTheme.typography.bodyMedium.lineHeight
                )

                Spacer(modifier = Modifier.height(22.dp))

                // Task navigation
                Button(
                    onClick = onOpenTasks,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = royalBlue
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 1.dp
                    )
                ) {
                    Text(
                        text = "Manage My Tasks",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleSmall,
                        color = Color.White
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(22.dp))

        // Logout button
        OutlinedButton(
            onClick = {
                authViewModel.logout()
                onLogout()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = textGray
            )
        ) {
            Text(
                text = "Logout",
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
}