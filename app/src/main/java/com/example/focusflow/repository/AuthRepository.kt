package com.example.focusflow.repository

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

// Firebase authentication
class AuthRepository {

    // Firebase authentication instance
    private val auth = FirebaseAuth.getInstance()

    // Checks user is logged in
    fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    // Registers a new user using email and password
    suspend fun register(
        email: String,
        password: String
    ): Result<Unit> {

        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
            Result.success(Unit)
        } catch (e: Exception) {
            // Returns registration error
            Result.failure(e)
        }
    }

    // Logs in an existing user
    suspend fun login(
        email: String,
        password: String
    ): Result<Unit> {

        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            Result.success(Unit)
        } catch (e: Exception) {
            // Returns login error
            Result.failure(e)
        }
    }

    // Logs function
    fun logout() {
        auth.signOut()
    }
}