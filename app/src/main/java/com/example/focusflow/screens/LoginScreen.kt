package com.example.focusflow.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.focusflow.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onRegisterClick: () -> Unit,
    authViewModel: AuthViewModel = viewModel()
) {

    // colors
    val navyBlue = Color(0xFF071B49)
    val royalBlue = Color(0xFF1769E8)
    val purple = Color(0xFF7625D8)
    val secondaryText = Color(0xFF64748B)

    // Input states
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Authentication states
    val loading by authViewModel.loading.collectAsState()
    val errorMessage by authViewModel.errorMessage.collectAsState()

    Box(
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
            ),
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 420.dp)
                .verticalScroll(rememberScrollState())
                .padding(
                    horizontal = 28.dp,
                    vertical = 24.dp
                ),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {

            // Welcome heading
            Text(
                text = "Welcome Back!",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold,
                color = navyBlue
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Supporting text
            Text(
                text = "Sign in to continue your learning journey.",
                style = MaterialTheme.typography.bodyMedium,
                color = secondaryText
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Email label
            Text(
                text = "Email Address",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = navyBlue
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Email field
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = {
                    Text(
                        text = "Enter your email",
                        color = secondaryText.copy(alpha = 0.7f)
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = royalBlue,
                    unfocusedBorderColor = Color(0xFFD5DEED),
                    focusedTextColor = navyBlue,
                    unfocusedTextColor = navyBlue,
                    cursorColor = royalBlue,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )

            // spacing
            Spacer(modifier = Modifier.height(22.dp))

            // Password label
            Text(
                text = "Password",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = navyBlue
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Password field
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = {
                    Text(
                        text = "Enter your password",
                        color = secondaryText.copy(alpha = 0.7f)
                    )
                },
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = purple,
                    unfocusedBorderColor = Color(0xFFD5DEED),
                    focusedTextColor = navyBlue,
                    unfocusedTextColor = navyBlue,
                    cursorColor = purple,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Authentication error
            errorMessage?.let {

                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))
            }

            // Sign In button
            Button(
                onClick = {
                    authViewModel.login(
                        email = email,
                        password = password,
                        onSuccess = onLoginSuccess
                    )
                },
                enabled = !loading,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = royalBlue,
                    contentColor = Color.White,
                    disabledContainerColor = royalBlue.copy(alpha = 0.6f)
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 4.dp,
                    pressedElevation = 1.dp
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {

                if (loading) {

                    CircularProgressIndicator(
                        modifier = Modifier.size(21.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )

                } else {

                    Text(
                        text = "Sign In",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Registration section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "New to FocusFlow?",
                    style = MaterialTheme.typography.bodyMedium,
                    color = secondaryText
                )

                TextButton(
                    onClick = onRegisterClick,
                    contentPadding = PaddingValues(
                        horizontal = 6.dp
                    )
                ) {

                    Text(
                        text = "Create Account",
                        fontWeight = FontWeight.Bold,
                        color = purple
                    )
                }
            }
        }
    }
}