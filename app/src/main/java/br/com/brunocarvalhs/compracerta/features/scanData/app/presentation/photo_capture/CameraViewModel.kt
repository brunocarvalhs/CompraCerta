package br.com.brunocarvalhs.compracerta.features.scanData.app.presentation.photo_capture

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import br.com.brunocarvalhs.compracerta.features.scanData.app.domain.useCase.ExtractDataUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal class CameraViewModel(
    private val extractDataUseCase: ExtractDataUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CameraState())
    val state = _state.asStateFlow()

    fun storePhotoInGallery(bitmap: Bitmap) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            extractDataUseCase(bitmap).onSuccess { result ->
                _state.value = _state.value.copy(isLoading = false)
                updateCapturedPhotoState(bitmap, result)
            }.onFailure {
                _state.value = _state.value.copy(isLoading = false)
            }
        }
    }

    private fun updateCapturedPhotoState(updatedPhoto: Bitmap?, result: List<String>) {
        _state.value.capturedImage?.recycle()
        _state.value = _state.value.copy(capturedImage = updatedPhoto, extractedText = result)
    }

    override fun onCleared() {
        _state.value.capturedImage?.recycle()
        super.onCleared()
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val extractDataUseCase = ExtractDataUseCase()
                CameraViewModel(extractDataUseCase)
            }
        }
    }
}
