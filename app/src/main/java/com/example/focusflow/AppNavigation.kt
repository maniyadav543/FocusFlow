package com.example.focusflow

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.example.focusflow.screens.*
import com.example.focusflow.viewmodel.AuthViewModel
import com.example.focusflow.viewmodel.TaskViewModel
import com.example.focusflow.viewmodel.TaskViewModelFactory
import com.example.focusflow.repository.TaskRepository
import com.example.focusflow.data.TaskDatabase
import com.google.firebase.auth.FirebaseAuth

// Controls app screen navigation
@Composable
fun AppNavigation() {

    // navigation controller
    val navController = rememberNavController()

    //  authentication ViewModel
    val authViewModel: AuthViewModel = viewModel()

    // app context
    val context = LocalContext.current

    // Creates local database
    val database = remember {
        TaskDatabase.getDatabase(context)
    }

    // Creates task repository
    val taskRepository = remember {
        TaskRepository(database.taskDao())
    }

    // Sets app navigation routes
    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {

        // Splash screen navigation
        composable("splash") {
            SplashScreen(
                onSplashFinished = {

                    // Checks login status
                    if (authViewModel.isUserLoggedIn()) {

                        // Opens home screen
                        navController.navigate("home") {
                            popUpTo("splash") {
                                inclusive = true
                            }
                        }
                    } else {

                        // Opens login screen
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

                    // Opens home after login
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

                onOpenTasks = {
                    navController.navigate("tasks")
                },

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

        // Task manager screen navigation
        composable("tasks") {

            // Gets current user ID
            val userId = FirebaseAuth
                .getInstance()
                .currentUser
                ?.uid.orEmpty()

            // Creates task ViewModel
            val taskViewModel: TaskViewModel = viewModel(
                factory = TaskViewModelFactory(
                    taskRepository,
                    userId
                )
            )

            TaskManagerScreen(
                taskViewModel = taskViewModel,

                // Returns to previous screen
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}