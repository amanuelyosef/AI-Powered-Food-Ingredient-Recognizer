package com.nisrmarket.foodingredientrecognizer.domain.model

data class FoodPrediction(
    val id: String,
    val label: String,
    val confidence: Float,
)

