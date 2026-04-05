package com.nisrmarket.foodingredientrecognizer.data.repository

import android.content.Context
import android.net.Uri
import android.util.Base64
import com.nisrmarket.foodingredientrecognizer.R
import com.nisrmarket.foodingredientrecognizer.data.mapper.extractTextPayload
import com.nisrmarket.foodingredientrecognizer.data.mapper.toFoodPredictionsFromGemini
import com.nisrmarket.foodingredientrecognizer.data.remote.GeminiApiService
import com.nisrmarket.foodingredientrecognizer.data.remote.dto.GeminiContent
import com.nisrmarket.foodingredientrecognizer.data.remote.dto.GeminiGenerateContentRequest
import com.nisrmarket.foodingredientrecognizer.data.remote.dto.GeminiInlineData
import com.nisrmarket.foodingredientrecognizer.data.remote.dto.GeminiPart
import com.nisrmarket.foodingredientrecognizer.domain.model.UploadResponse
import com.nisrmarket.foodingredientrecognizer.domain.repository.FoodImageRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import javax.inject.Inject
import retrofit2.HttpException

class FoodImageRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val geminiApiService: GeminiApiService,
) : FoodImageRepository {

    override suspend fun uploadImage(
        uri: Uri,
        title: String?,
    ): UploadResponse {
        return runCatching {
            val imageBytes = requireNotNull(
                context.contentResolver.openInputStream(uri)?.use { input -> input.readBytes() },
            ) {
                "Could not read the selected image."
            }

            val request = GeminiGenerateContentRequest(
                contents = listOf(
                    GeminiContent(
                        parts = listOf(
                            GeminiPart(text = context.getString(R.string.GEMINI_INGREDIENT_PROMPT)),
                            GeminiPart(
                                inlineData = GeminiInlineData(
                                    mimeType = context.getString(R.string.GEMINI_IMAGE_MIME_TYPE),
                                    data = Base64.encodeToString(imageBytes, Base64.NO_WRAP),
                                ),
                            ),
                        ),
                    ),
                ),
            )

            val apiKey = context.getString(R.string.GEMINI_API_KEY)
            val configuredModel = context.getString(R.string.GEMINI_MODEL)
            val fallbackModels = context.getString(R.string.GEMINI_FALLBACK_MODELS)
                .split(',')
                .map { it.trim() }
                .filter { it.isNotBlank() }

            val modelsToTry = buildList {
                add(configuredModel)
                addAll(fallbackModels)
            }
                .map { normalizeModelName(it) }
                .distinct()

            val response = generateWithFallbackModels(
                request = request,
                apiKey = apiKey,
                models = modelsToTry,
            )

            val rawText = response.extractTextPayload()
            val predictions = rawText.toFoodPredictionsFromGemini()

            UploadResponse(
                success = predictions.isNotEmpty(),
                predictions = predictions,
                errorMessage = if (predictions.isEmpty()) {
                    "Gemini returned no ingredient predictions."
                } else {
                    null
                },
            )
        }.getOrElse { error ->
            UploadResponse(
                success = false,
                errorMessage = error.toGeminiMessage(),
            )
        }
    }

    private suspend fun generateWithFallbackModels(
        request: GeminiGenerateContentRequest,
        apiKey: String,
        models: List<String>,
    ) = run {
        var lastError: HttpException? = null

        for (model in models) {
            try {
                return@run geminiApiService.generateContent(
                    model = model,
                    apiKey = apiKey,
                    request = request,
                )
            } catch (e: HttpException) {
                if (e.code() == 404) {
                    lastError = e
                    continue
                }
                throw e
            }
        }

        throw (lastError ?: IllegalStateException("No Gemini model configured."))
    }

    private fun normalizeModelName(model: String): String {
        return model.removePrefix("models/").trim()
    }

    private fun Throwable.toGeminiMessage(): String {
        return when (this) {
            is HttpException -> {
                val errorBody = response()?.errorBody()?.string()?.trim().orEmpty()
                buildString {
                    append("Gemini HTTP ")
                    append(code())
                    if (errorBody.isNotBlank()) {
                        append(": ")
                        append(errorBody)
                    }
                }
            }
            is IOException -> message ?: "Network error while contacting Gemini."
            else -> message ?: "Image recognition failed."
        }
    }
}