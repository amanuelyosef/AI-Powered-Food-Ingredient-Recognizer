package com.nisrmarket.foodingredientrecognizer.data.remote.dto

import com.google.gson.annotations.SerializedName

data class GeminiGenerateContentRequest(
    val contents: List<GeminiContent>,
)

data class GeminiContent(
    val parts: List<GeminiPart>,
)

data class GeminiPart(
    val text: String? = null,
    @SerializedName("inline_data")
    val inlineData: GeminiInlineData? = null,
)

data class GeminiInlineData(
    @SerializedName("mime_type")
    val mimeType: String,
    val data: String,
)

data class GeminiGenerateContentResponse(
    val candidates: List<GeminiCandidate>?,
)

data class GeminiCandidate(
    val content: GeminiContent?,
)

