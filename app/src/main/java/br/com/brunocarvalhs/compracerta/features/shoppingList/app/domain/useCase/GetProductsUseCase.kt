package br.com.brunocarvalhs.compracerta.features.shoppingList.app.domain.useCase

import br.com.brunocarvalhs.compracerta.features.shoppingList.app.domain.model.Product
import br.com.brunocarvalhs.compracerta.features.shoppingList.app.domain.repositories.ProductRepository

class GetProductsUseCase(
    private val productRepository: ProductRepository,
) {
    suspend operator fun invoke(groupId: Long): Result<List<Product>> {
        return try {
            val products = productRepository.getProducts(groupId)
            Result.success(products)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}