package com.nisrmarket.foodingredientrecognizer.domain.repository

import android.net.Uri
import com.nisrmarket.foodingredientrecognizer.domain.model.UploadResponse

interface FoodImageRepository {
    suspend fun uploadImage(uri: Uri, title: String?): UploadResponse

    //other functions


}