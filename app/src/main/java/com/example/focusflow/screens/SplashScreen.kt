package com.example.focusflow.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.focusflow.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onSplashFinished: () -> Unit
) {
    // Waits before completing the splash screen
    LaunchedEffect(Unit) {
        delay(2000)
        onSplashFinished()
    }

    // Main splash screen layout
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // Displays FocusFlow logo
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "FocusFlow Logo",
                modifier = Modifier.size(280.dp),
                contentScale = ContentScale.Fit
            )

            // Adds spacing below the logo
            Spacer(modifier = Modifier.height(24.dp))

            // Displays loading indicator
            CircularProgressIndicator()
        }
    }
}