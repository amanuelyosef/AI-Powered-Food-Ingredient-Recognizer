package com.nisrmarket.foodingredientrecognizer.presentation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nisrmarket.foodingredientrecognizer.presentation.components.ScreenRoute
import com.nisrmarket.foodingredientrecognizer.presentation.home_screen.HomeScreen

@Composable
fun NavigationGraph(
    applicationContext: Context,
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ScreenRoute.HomeScreen.route
    ) {
        composable(route = ScreenRoute.HomeScreen.route) {
            HomeScreen()
        }
    }


}