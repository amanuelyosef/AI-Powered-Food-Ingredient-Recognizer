package com.nisrmarket.foodingredientrecognizer.presentation.components

sealed class ScreenRoute(val route: String) {

    object HomeScreen : ScreenRoute("home_screen")

}