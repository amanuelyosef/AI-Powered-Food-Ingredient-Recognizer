package com.nisrmarket.foodingredientrecognizer.presentation.home_screen

import android.net.Uri
import com.nisrmarket.foodingredientrecognizer.domain.model.FoodPrediction

data class HomeUiState(
    val selectedImageUri: Uri? = null,
    val isUploading: Boolean = false,
    val statusMessage: String? = null,
    val predictions: List<FoodPrediction> = emptyList(),
    val errorMessage: String? = null,
)
