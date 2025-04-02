package br.com.brunocarvalhs.compracerta.features.scanData.app.data.services

import android.graphics.Bitmap
import br.com.brunocarvalhs.compracerta.features.scanData.app.domain.services.OCRService
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.tasks.await
import java.util.regex.Pattern

class OCRServiceImpl : OCRService {

    override suspend fun processImage(bitmap: Bitmap): List<String> {
        val inputImage = InputImage.fromBitmap(bitmap, 0)

        val recognizer = TextRecognition.getClient(
            TextRecognizerOptions.DEFAULT_OPTIONS
        )
        val result = recognizer.process(inputImage).await()
        return extractPrices(result.text)
    }

    private fun extractPrices(text: String): List<String> {
        val pattern = Pattern.compile("(?:R\\$\\s?|)(\\d{1,3}(?:[.,]\\d{3})*[.,]\\d{2})")
        val matcher = pattern.matcher(text)
        val prices = mutableListOf<String>()
        while (matcher.find()) {
            matcher.group(1)?.let { prices.add(it.replace(".", ",")) }
        }
        return prices
    }
}
