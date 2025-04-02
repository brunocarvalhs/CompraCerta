package br.com.brunocarvalhs.compracerta.features.scanData.app.domain.useCase

import android.graphics.Bitmap
import br.com.brunocarvalhs.compracerta.features.scanData.app.data.services.OCRServiceImpl
import br.com.brunocarvalhs.compracerta.features.scanData.app.domain.services.OCRService

internal class ExtractDataUseCase(
    private val ocrService: OCRService = OCRServiceImpl()
) {
    suspend operator fun invoke(image: Bitmap): Result<List<String>> = runCatching {
        val tags = ocrService.processImage(image)
        return Result.success(tags)
    }
}