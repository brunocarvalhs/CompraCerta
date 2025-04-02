package br.com.brunocarvalhs.compracerta.features.scanData.app.domain.services

import android.graphics.Bitmap

internal interface OCRService {
    suspend fun processImage(bitmap: Bitmap): List<String>
}