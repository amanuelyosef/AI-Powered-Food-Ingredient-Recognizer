package com.nisrmarket.foodingredientrecognizer.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.nisrmarket.foodingredientrecognizer.presentation.ui.theme.FoodIngredientRecognizerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FoodIngredientRecognizerTheme {
                val applicationContext = applicationContext
                NavigationGraph(applicationContext)
            }
        }
    }
}