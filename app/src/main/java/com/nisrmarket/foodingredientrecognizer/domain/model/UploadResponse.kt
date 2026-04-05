package com.nisrmarket.foodingredientrecognizer.domain.model

data class UploadResponse(
    val success: Boolean,
    val predictions: List<FoodPrediction> = emptyList(),
    val errorMessage: String? = null,
)
