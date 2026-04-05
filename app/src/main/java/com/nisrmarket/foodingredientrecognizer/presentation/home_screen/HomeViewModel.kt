package com.nisrmarket.foodingredientrecognizer.presentation.home_screen

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nisrmarket.foodingredientrecognizer.domain.repository.FoodImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: FoodImageRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun onImageSelected(uri: Uri?) {
        _uiState.update {
            it.copy(
                selectedImageUri = uri,
                statusMessage = null,
                predictions = emptyList(),
                errorMessage = null,
            )
        }
    }

    fun uploadSelectedImage() {
        val selectedUri = _uiState.value.selectedImageUri ?: return

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isUploading = true,
                    statusMessage = null,
                    predictions = emptyList(),
                    errorMessage = null,
                )
            }

            val response = repository.uploadImage(
                uri = selectedUri,
                title = selectedUri.lastPathSegment,
            )

            println("error message: ${response.errorMessage}")
            println("response: ${response}")

            _uiState.update {
                it.copy(
                    isUploading = false,
                    predictions = response.predictions,
                    statusMessage = if (response.success) {
                        "Recognition complete. Found ${response.predictions.size} predictions."
                    } else {
                        null
                    },
                    errorMessage = response.errorMessage,
                )
            }
        }
    }
}
