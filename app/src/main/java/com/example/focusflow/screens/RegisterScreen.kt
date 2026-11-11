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
fun RegisterScreen(
    onRegistrationSuccess: () -> Unit,
    onLoginClick: () -> Unit,
    authViewModel: AuthViewModel = viewModel()
) {

    // App colours
    val navyBlue = Color(0xFF071B49)
    val royalBlue = Color(0xFF1769E8)
    val purple = Color(0xFF7625D8)
    val secondaryText = Color(0xFF64748B)

    // Store the entered details
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    // Get authentication status
    val loading by authViewModel.loading.collectAsState()
    val errorMessage by authViewModel.errorMessage.collectAsState()

    // gradient background
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

        // Registration form
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

            // Registration heading
            Text(
                text = "Create Account",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold,
                color = navyBlue
            )

            Spacer(modifier = Modifier.height(8.dp))

            // welcome message
            Text(
                text = "Start your learning journey with FocusFlow.",
                style = MaterialTheme.typography.bodyMedium,
                color = secondaryText
            )

            Spacer(modifier = Modifier.height(30.dp))

            // Email label
            Text(
                text = "Email Address",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = navyBlue
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Enter email address
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

            Spacer(modifier = Modifier.height(20.dp))

            // Password label
            Text(
                text = "Password",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = navyBlue
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Enter password
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
                    focusedBorderColor = royalBlue,
                    unfocusedBorderColor = Color(0xFFD5DEED),
                    focusedTextColor = navyBlue,
                    unfocusedTextColor = navyBlue,
                    cursorColor = royalBlue,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Confirm password label
            Text(
                text = "Confirm Password",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = navyBlue
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Re-enter password
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                placeholder = {
                    Text(
                        text = "Re-enter your password",
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

            // authentication errors
            errorMessage?.let {

                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))
            }

            // Create new account
            Button(
                onClick = {

                    // Check both passwords match
                    if (password != confirmPassword) {
                        return@Button
                    }

                    // Send registration details
                    authViewModel.register(
                        email = email,
                        password = password,
                        onSuccess = onRegistrationSuccess
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

                // circular loading
                if (loading) {

                    CircularProgressIndicator(
                        modifier = Modifier.size(21.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )

                } else {

                    Text(
                        text = "Create Account",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Login navigation
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                // Existing account message
                Text(
                    text = "Already have an account?",
                    style = MaterialTheme.typography.bodyMedium,
                    color = secondaryText
                )

                // Navigate to login screen
                TextButton(
                    onClick = onLoginClick,
                    contentPadding = PaddingValues(
                        horizontal = 6.dp
                    )
                ) {
                    // Login button text
                    Text(
                        text = "Login",
                        fontWeight = FontWeight.Bold,
                        color = purple
                    )
                }
            }
        }
    }
}