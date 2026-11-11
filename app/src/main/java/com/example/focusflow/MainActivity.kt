package com.example.focusflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.focusflow.AppNavigation
import com.example.focusflow.ui.theme.FocusFlowTheme

// Main entry point of application
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Sets the Compose UI content
        setContent {
            // Applies the application theme
            FocusFlowTheme {
                // Starts app navigation
                AppNavigation()
            }
        }
    }
}