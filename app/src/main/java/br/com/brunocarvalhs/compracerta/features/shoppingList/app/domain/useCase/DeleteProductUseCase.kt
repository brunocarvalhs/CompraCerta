package br.com.brunocarvalhs.compracerta.features.shoppingList.app.domain.useCase

import br.com.brunocarvalhs.compracerta.features.shoppingList.app.domain.model.Product
import br.com.brunocarvalhs.compracerta.features.shoppingList.app.domain.repositories.ProductRepository

class DeleteProductUseCase(
    private val productRepository: ProductRepository,
) {
    suspend operator fun invoke(product: Product): Result<Unit> = kotlin.runCatching {
        productRepository.deleteProduct(product)
    }
}