package br.com.brunocarvalhs.compracerta.features.scanData.app.presentation.photo_capture

import android.graphics.Bitmap

data class CameraState(
    val capturedImage: Bitmap? = null,
    val isLoading: Boolean = false,
    val extractedText: List<String> = emptyList()
)
