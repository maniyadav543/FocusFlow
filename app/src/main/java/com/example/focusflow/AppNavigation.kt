package com.example.focusflow

import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.example.focusflow.screens.*
import com.example.focusflow.viewmodel.AuthViewModel

// Handles navigation between application screens
@Composable
fun AppNavigation() {

    // navigation controller
    val navController = rememberNavController()

    // Initializes authentication ViewModel
    val authViewModel: AuthViewModel = viewModel()

    // app navigation destinations
    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {

        // Splash screen navigation
        composable("splash") {
            SplashScreen(
                onSplashFinished = {
                    // Checks user login status
                    if (authViewModel.isUserLoggedIn()) {

                        // Navigates to home screen
                        navController.navigate("home") {
                            popUpTo("splash") {
                                inclusive = true
                            }
                        }
                    } else {

                        // Navigates to login screen
                        navController.navigate("login") {
                            popUpTo("splash") {
                                inclusive = true
                            }
                        }
                    }
                }
            )
        }

        // Login screen navigation
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    // Opens home after successful login
                    navController.navigate("home") {
                        popUpTo("login") {
                            inclusive = true
                        }
                    }
                },

                // Opens registration screen
                onRegisterClick = {
                    navController.navigate("register")
                }
            )
        }

        // Registration screen navigation
        composable("register") {
            RegisterScreen(
                onRegistrationSuccess = {
                    // Opens home after registration
                    navController.navigate("home") {
                        popUpTo("register") {
                            inclusive = true
                        }
                    }
                },

                // Returns to login screen
                onLoginClick = {
                    navController.popBackStack()
                }
            )
        }

        // Home screen navigation
        composable("home") {
            HomeScreen(
                authViewModel = authViewModel,

                // Handles user logout
                onLogout = {
                    navController.navigate("login") {
                        popUpTo("home") {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}