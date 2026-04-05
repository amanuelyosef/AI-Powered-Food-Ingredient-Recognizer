package com.nisrmarket.foodingredientrecognizer.data.remote

import com.nisrmarket.foodingredientrecognizer.data.remote.dto.GeminiGenerateContentRequest
import com.nisrmarket.foodingredientrecognizer.data.remote.dto.GeminiGenerateContentResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface GeminiApiService {

    @POST("v1beta/models/{model}:generateContent")
    suspend fun generateContent(
        @Path("model") model: String,
        @Query("key") apiKey: String,
        @Body request: GeminiGenerateContentRequest,
    ): GeminiGenerateContentResponse
}

