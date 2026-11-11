package com.example.focusflow.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.focusflow.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Manages authentication
class AuthViewModel : ViewModel() {

    // Creates authentication repository
    private val repository = AuthRepository()

    // Tracks loading state
    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    // Stores authentication error messages
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    // Handles new user registration
    fun register(
        email: String,
        password: String,
        onSuccess: () -> Unit
    ) {

        // Validates email and password
        if (email.isBlank() || password.isBlank()) {
            _errorMessage.value = "Please enter your email and password."
            return
        }

        // Checks minimum password length
        if (password.length < 6) {
            _errorMessage.value =
                "Password must contain at least 6 characters."
            return
        }

        // Performs registration in background
        viewModelScope.launch {
            _loading.value = true
            _errorMessage.value = null

            val result = repository.register(email, password)

            _loading.value = false

            // Handles registration result
            if (result.isSuccess) {
                onSuccess()
            } else {
                _errorMessage.value =
                    result.exceptionOrNull()?.message
                        ?: "Registration failed."
            }
        }
    }

    // Handles existing user login
    fun login(
        email: String,
        password: String,
        onSuccess: () -> Unit
    ) {

        // Validates login details
        if (email.isBlank() || password.isBlank()) {
            _errorMessage.value = "Please enter your email and password."
            return
        }

        // Performs login in background
        viewModelScope.launch {
            _loading.value = true
            _errorMessage.value = null

            val result = repository.login(email, password)

            _loading.value = false

            // Handles login result
            if (result.isSuccess) {
                onSuccess()
            } else {
                _errorMessage.value =
                    result.exceptionOrNull()?.message
                        ?: "Login failed."
            }
        }
    }

    // Logs out the current user
    fun logout() {
        repository.logout()
    }

    // authentication error message
    fun clearError() {
        _errorMessage.value = null
    }

    // Checks current user login status
    fun isUserLoggedIn(): Boolean {
        return repository.isUserLoggedIn()
    }
}