package com.nisrmarket.foodingredientrecognizer.data.mapper

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.nisrmarket.foodingredientrecognizer.data.remote.dto.GeminiGenerateContentResponse
import com.nisrmarket.foodingredientrecognizer.domain.model.FoodPrediction

private val labelKeys = listOf("ingredient", "name", "label", "item", "food")
private val confidenceKeys = listOf("confidence", "score", "probability", "value")

fun GeminiGenerateContentResponse.extractTextPayload(): String {
    return candidates
        .orEmpty()
        .flatMap { it.content?.parts.orEmpty() }
        .mapNotNull { it.text }
        .joinToString("\n")
        .trim()
}

fun String.toFoodPredictionsFromGemini(): List<FoodPrediction> {
    if (isBlank()) return emptyList()

    val parsedElement = runCatching { JsonParser().parse(this) }.getOrNull()
    val fromJson = parsedElement?.let { parseJsonPredictions(it) }.orEmpty()

    if (fromJson.isNotEmpty()) {
        return fromJson
    }

    return split(',', '\n')
        .map { it.trim().trimStart('-', '*', '"').trimEnd('"') }
        .filter { it.isNotBlank() }
        .distinctBy { it.lowercase() }
        .take(10)
        .mapIndexed { index, label ->
            FoodPrediction(
                id = "ingredient_$index",
                label = label,
                confidence = 0.6f,
            )
        }
}

private fun parseJsonPredictions(element: JsonElement): List<FoodPrediction> {
    val items = when {
        element.isJsonArray -> element.asJsonArray.toList()
        element.isJsonObject -> readTopLevelArray(element.asJsonObject)
        else -> emptyList()
    }

    return items.mapIndexedNotNull { index, item ->
        when {
            item.isJsonPrimitive && item.asJsonPrimitive.isString -> {
                val label = item.asString.trim()
                if (label.isBlank()) null else FoodPrediction("ingredient_$index", label, 0.7f)
            }
            item.isJsonObject -> item.asJsonObject.toPrediction(index)
            else -> null
        }
    }
        .groupBy { it.label.lowercase() }
        .map { (_, same) -> same.maxBy { it.confidence } }
        .sortedByDescending { it.confidence }
        .take(10)
}

private fun readTopLevelArray(obj: JsonObject): List<JsonElement> {
    val keys = listOf("ingredients", "predictions", "results", "items")
    keys.forEach { key ->
        val value = obj.get(key)
        if (value != null && value.isJsonArray) {
            return value.asJsonArray.toList()
        }
    }
    return emptyList()
}

private fun JsonObject.toPrediction(index: Int): FoodPrediction? {
    val label = labelKeys.firstNotNullOfOrNull { key -> readString(key) }?.trim().orEmpty()
    if (label.isBlank()) return null

    val confidence = confidenceKeys.firstNotNullOfOrNull { key -> readFloat(key) } ?: 0.7f
    val id = readString("id") ?: "ingredient_$index"

    return FoodPrediction(
        id = id,
        label = label,
        confidence = confidence.coerceIn(0f, 1f),
    )
}

private fun JsonObject.readString(key: String): String? {
    val value = get(key) ?: return null
    return if (value.isJsonPrimitive && value.asJsonPrimitive.isString) value.asString else null
}

private fun JsonObject.readFloat(key: String): Float? {
    val value = get(key) ?: return null
    return if (value.isJsonPrimitive && value.asJsonPrimitive.isNumber) value.asFloat else null
}
